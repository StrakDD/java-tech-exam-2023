package me.fasterthanlight.technicaltask2.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import me.fasterthanlight.technicaltask2.domain.dto.User;
import me.fasterthanlight.technicaltask2.domain.dto.UserInfo;
import me.fasterthanlight.technicaltask2.domain.enitity.JpaUser;
import me.fasterthanlight.technicaltask2.mapper.UserMapper;
import me.fasterthanlight.technicaltask2.repository.JpaUserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.ErrorResponseException;

import java.util.List;

@AllArgsConstructor
@Service
public class UserJpaCrud implements UserCrud {

    private JpaUserRepository jpaUserRepository;
    private UserMapper userMapper;

    @Override
    public User createUser(UserInfo userInfo) {
        var jpaUser = userMapper.mapUserInfoToJpaUser(userInfo);
        jpaUser = jpaUserRepository.save(jpaUser);

        return userMapper.mapJpaUserToUser(jpaUser);
    }

    @Override
    public User readUser(String userId) {
        var jpaUser = jpaUserRepository.findById(userId)
                .orElseThrow(() -> new ErrorResponseException(HttpStatus.NOT_FOUND));

        return userMapper.mapJpaUserToUser(jpaUser);
    }

    @Override
    @Transactional
    public User updateUser(String userId, UserInfo userInfo) {
        var jpaUser = jpaUserRepository.findById(userId)
                .orElseThrow(() -> new ErrorResponseException(HttpStatus.NOT_FOUND));

        userMapper.updateJpaUser(jpaUser, userInfo);
        jpaUser = jpaUserRepository.save(jpaUser);

        return userMapper.mapJpaUserToUser(jpaUser);
    }

    @Override
    public void deleteUser(String userId) {
        jpaUserRepository.deleteById(userId);
    }

    @Override
    public User saveUser(User user) {
        var jpaUser = userMapper.mapUserToJpaUser(user);
        jpaUser = jpaUserRepository.save(jpaUser);

        return userMapper.mapJpaUserToUser(jpaUser);
    }

    @Override
    public List<User> getAllById(Iterable<String> ids) {
        List<JpaUser> jpaUsers = jpaUserRepository.findAllById(ids);

        return userMapper.mapJpaUsersToUsers(jpaUsers);
    }
}
