package me.fasterthanlight.technicaltask2.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.fasterthanlight.technicaltask2.domain.dto.User;
import me.fasterthanlight.technicaltask2.domain.dto.UserInfo;
import me.fasterthanlight.technicaltask2.service.UserCrud;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/api/task/users")
public class UserController {

    private UserCrud userCrud;

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody @Valid UserInfo userInfo) {
        var user = userCrud.createUser(userInfo);

        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @GetMapping("/{userId}")
    public User readUser(@PathVariable String userId) {
        return userCrud.readUser(userId);
    }

    @PutMapping("/{userId}")
    public User updateUser(@PathVariable String userId, @RequestBody @Valid UserInfo userInfo) {
        return userCrud.updateUser(userId, userInfo);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable String userId) {
        userCrud.deleteUser(userId);

        return ResponseEntity.noContent().build();
    }
}
