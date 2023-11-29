package me.fasterthanlight.technicaltask2.controller;

import lombok.AllArgsConstructor;
import me.fasterthanlight.technicaltask2.domain.dto.UserQuota;
import me.fasterthanlight.technicaltask2.service.RateLimiterService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/task/quotas")
public class QuotaController {

    private RateLimiterService rateLimiterService;

    @PostMapping("/{userId}")
    public void consumeQuota(@PathVariable String userId) {
        rateLimiterService.setApiHitCountForUser(userId);
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
