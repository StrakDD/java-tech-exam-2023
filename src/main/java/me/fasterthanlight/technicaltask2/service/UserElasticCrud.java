package me.fasterthanlight.technicaltask2.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import me.fasterthanlight.technicaltask2.domain.dto.User;
import me.fasterthanlight.technicaltask2.domain.dto.UserInfo;
import me.fasterthanlight.technicaltask2.domain.enitity.ElasticUser;
import me.fasterthanlight.technicaltask2.mapper.UserMapper;
import me.fasterthanlight.technicaltask2.repository.ElasticUserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.ErrorResponseException;

import java.util.List;
import java.util.stream.StreamSupport;

@AllArgsConstructor
@Service
public class UserElasticCrud implements UserCrud {

    private ElasticUserRepository elasticUserRepository;
    private UserMapper userMapper;

    @Override
    public User createUser(UserInfo userInfo) {
        var elasticUser = userMapper.mapUserInfoToElasticUser(userInfo);
        elasticUser = elasticUserRepository.save(elasticUser);

        return userMapper.mapElasticUserToUser(elasticUser);
    }

    @Override
    public User readUser(String userId) {
        var elasticUser = elasticUserRepository.findById(userId)
                .orElseThrow(() -> new ErrorResponseException(HttpStatus.NOT_FOUND));

        return userMapper.mapElasticUserToUser(elasticUser);
    }

    @Override
    @Transactional
    public User updateUser(String userId, UserInfo userInfo) {
        var elasticUser = elasticUserRepository.findById(userId)
                .orElseThrow(() -> new ErrorResponseException(HttpStatus.NOT_FOUND));

        userMapper.updateElasticUser(elasticUser, userInfo);
        elasticUser = elasticUserRepository.save(elasticUser);

        return userMapper.mapElasticUserToUser(elasticUser);
    }

    @Override
    public void deleteUser(String userId) {
        elasticUserRepository.deleteById(userId);
    }

    @Override
    public User saveUser(User user) {
        var elasticUser = userMapper.mapUserToElasticUser(user);
        elasticUser = elasticUserRepository.save(elasticUser);

        return userMapper.mapElasticUserToUser(elasticUser);
    }

    @Override
    public List<User> getAllById(Iterable<String> ids) {
        List<ElasticUser> elasticUsers = StreamSupport.stream(elasticUserRepository.findAllById(ids).spliterator(), false)
                .toList();

        return userMapper.mapElasticUsersToUsers(elasticUsers);
    }
}
