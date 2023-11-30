package me.fasterthanlight.technicaltask2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.ErrorResponseException;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.function.Function.identity;

@Service
public class RateLimiterService {

    private final Integer apiCountLimit;
    private final RedisTemplate<String, String> redisTemplate;

    public RateLimiterService(@Value("${api.count.limit:5}") Integer apiCountLimit, @Autowired RedisTemplate<String, String> redisTemplate) {
        this.apiCountLimit = apiCountLimit;
        this.redisTemplate = redisTemplate;
    }

    public synchronized void setApiHitCountForUser(String userId) {
        if (redisTemplate.hasKey(userId)) {
            if (Integer.parseInt(redisTemplate.opsForValue().get(userId)) >= apiCountLimit) {
                throw new ErrorResponseException(HttpStatus.TOO_MANY_REQUESTS);
            }
            redisTemplate.opsForValue().increment(userId);
        } else {
            redisTemplate.opsForValue().set(userId, "1");
            redisTemplate.expireAt(userId, LocalDate.now().plusDays(1).atStartOfDay().toInstant(ZoneOffset.UTC));
        }
    }

    public Map<String, String> getAllUsersHitsCount() {
        Set<String> keys = redisTemplate.keys("*");

        return keys.stream().collect(Collectors.toMap(identity(), key -> redisTemplate.opsForValue().get(key)));
    }
}
