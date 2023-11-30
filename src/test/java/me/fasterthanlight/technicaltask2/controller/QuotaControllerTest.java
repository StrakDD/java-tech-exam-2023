package me.fasterthanlight.technicaltask2.controller;

import me.fasterthanlight.technicaltask2.domain.dto.UserQuota;
import me.fasterthanlight.technicaltask2.service.RateLimiterService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class QuotaControllerTest {

    private static final String USER_ID = "userId";

    @Mock
    private RateLimiterService rateLimiterService;
    @InjectMocks
    private QuotaController quotaController;

    @Test
    void testConsumeQuota() {
        quotaController.consumeQuota(USER_ID);

        verify(rateLimiterService).setApiHitCountForUser(USER_ID);
    }

    @Test
    void testGetUsersQuota() {
        when(rateLimiterService.getAllUsersHitsCount()).thenReturn(Map.of(USER_ID + 1, "1", USER_ID + 2, "3"));

        List<UserQuota> usersQuota = quotaController.getUsersQuota();

        assertEquals(2, usersQuota.size());
        assertTrue(usersQuota.containsAll(List.of(getUserQuota(USER_ID + 1, 1), getUserQuota(USER_ID + 2, 3))));
    }

    private static UserQuota getUserQuota(String userId, int hintCount) {
        return UserQuota.builder()
                .id(userId)
                .hitCount(hintCount)
                .build();
    }
}
