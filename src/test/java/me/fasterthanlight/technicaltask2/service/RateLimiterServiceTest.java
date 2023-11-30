package me.fasterthanlight.technicaltask2.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.web.ErrorResponseException;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RateLimiterServiceTest {

    private static final String KEY_1 = "1";
    private static final String VALUE_1 = "11";
    private static final String KEY_2 = "2";
    private static final String VALUE_2 = "22";
    private static int COUNT_LIMIT = 5;

    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private RedisTemplate<String, String> redisTemplate;
    private RateLimiterService rateLimiterService;

    @BeforeEach
    void init() {
        rateLimiterService = new RateLimiterService(COUNT_LIMIT, redisTemplate);
    }

    @Test
    void testGetAllUsersHitsCount() {
        when(redisTemplate.keys("*")).thenReturn(Set.of(KEY_1, KEY_2));
        when(redisTemplate.opsForValue().get(KEY_1)).thenReturn(VALUE_1);
        when(redisTemplate.opsForValue().get(KEY_2)).thenReturn(VALUE_2);

        Map<String, String> allUsersHitsCount = rateLimiterService.getAllUsersHitsCount();

        assertEquals(2, allUsersHitsCount.size());
        assertTrue(allUsersHitsCount.keySet().containsAll(Set.of(KEY_1, KEY_2)));
        assertTrue(allUsersHitsCount.values().containsAll(List.of(VALUE_1, VALUE_2)));
    }

    @Test
    void testSetApiHitCountForUserPutNewKey() {
        when(redisTemplate.hasKey(KEY_1)).thenReturn(false);

        rateLimiterService.setApiHitCountForUser(KEY_1);

        verify(redisTemplate).expireAt(eq(KEY_1), (Instant) any());
        verify(redisTemplate.opsForValue()).set(KEY_1, "1");
    }

    @Test
    void testSetApiHitCountForUserThrowException() {
        when(redisTemplate.hasKey(KEY_1)).thenReturn(true);
        when(redisTemplate.opsForValue().get(KEY_1)).thenReturn("6");

        ErrorResponseException errorResponseException = assertThrows(ErrorResponseException.class,
                () -> rateLimiterService.setApiHitCountForUser(KEY_1));

        assertEquals(HttpStatus.TOO_MANY_REQUESTS, errorResponseException.getStatusCode());
    }

    @Test
    void testSetApiHitCountForUserIncrementCount() {
        when(redisTemplate.hasKey(KEY_1)).thenReturn(true);
        when(redisTemplate.opsForValue().get(KEY_1)).thenReturn("3");

        rateLimiterService.setApiHitCountForUser(KEY_1);

        verify(redisTemplate.opsForValue()).increment(KEY_1);
    }
}
