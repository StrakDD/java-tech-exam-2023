package me.fasterthanlight.technicaltask2.controller;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import me.fasterthanlight.technicaltask2.domain.dto.User;
import me.fasterthanlight.technicaltask2.domain.dto.UserQuota;
import me.fasterthanlight.technicaltask2.service.RateLimiterService;
import me.fasterthanlight.technicaltask2.service.UserCrud;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/task/quotas")
public class QuotaController {

    private RateLimiterService rateLimiterService;
    private UserCrud userCrud;

    @PostMapping("/{userId}")
    @Transactional
    public void consumeQuota(@PathVariable String userId) {
        User user = userCrud.readUser(userId);
        user.setLastLoginTimeUtc(LocalDateTime.now());

        rateLimiterService.setApiHitCountForUser(userId);
        userCrud.saveUser(user);
    }

    @GetMapping
    public List<UserQuota> getUsersQuota() {
        return rateLimiterService.getAllUsersHitsCount()
                .entrySet().stream().map(entry -> UserQuota.builder()
                        .id(entry.getKey())
                        .hitCount(Integer.parseInt(entry.getValue()))
                        .build()).toList();
    }
}
