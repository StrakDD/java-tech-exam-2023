package me.fasterthanlight.technicaltask2.service;

import me.fasterthanlight.technicaltask2.domain.dto.User;
import me.fasterthanlight.technicaltask2.domain.dto.UserInfo;

import java.util.List;

public interface UserCrud {

    User createUser(UserInfo userInfo);

    User readUser(String userId);

    User updateUser(String userId, UserInfo userInfo);

    void deleteUser(String userId);

    List<User> getAllById(Iterable<String> ids);
}
