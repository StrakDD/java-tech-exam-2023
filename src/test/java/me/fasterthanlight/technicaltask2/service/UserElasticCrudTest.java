package me.fasterthanlight.technicaltask2.service;

import me.fasterthanlight.technicaltask2.domain.dto.User;
import me.fasterthanlight.technicaltask2.domain.dto.UserInfo;
import me.fasterthanlight.technicaltask2.domain.enitity.ElasticUser;
import me.fasterthanlight.technicaltask2.mapper.UserMapper;
import me.fasterthanlight.technicaltask2.repository.ElasticUserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.ErrorResponseException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserElasticCrudTest {

    private static final String USER_ID = "1";

    @Mock
    private UserInfo userInfo;
    @Mock
    private User user;
    @Mock
    private ElasticUser elasticUser;
    @Mock
    private ElasticUserRepository elasticUserRepository;
    @Mock
    private UserMapper userMapper;
    @InjectMocks
    private UserElasticCrud userElasticCrud;

    @Test
    void testCreateUser() {
        when(userMapper.mapUserInfoToElasticUser(userInfo)).thenReturn(elasticUser);
        when(elasticUserRepository.save(elasticUser)).thenReturn(elasticUser);
        when(userMapper.mapElasticUserToUser(elasticUser)).thenReturn(user);

        User result = userElasticCrud.createUser(userInfo);

        assertEquals(user, result);
    }

    @Test
    void testReadUser() {
        when(elasticUserRepository.findById(USER_ID)).thenReturn(Optional.of(elasticUser));
        when(userMapper.mapElasticUserToUser(elasticUser)).thenReturn(user);

        User result = userElasticCrud.readUser(USER_ID);

        assertEquals(user, result);
    }

    @Test
    void testReadUserThrowException() {
        when(elasticUserRepository.findById(USER_ID)).thenReturn(Optional.empty());

        ErrorResponseException errorResponseException = assertThrows(ErrorResponseException.class,
                () -> userElasticCrud.readUser(USER_ID));

        assertEquals(HttpStatus.NOT_FOUND, errorResponseException.getStatusCode());
    }

    @Test
    void testUpdateUser() {
        when(elasticUserRepository.findById(USER_ID)).thenReturn(Optional.of(elasticUser));
        when(elasticUserRepository.save(elasticUser)).thenReturn(elasticUser);
        when(userMapper.mapElasticUserToUser(elasticUser)).thenReturn(user);

        User result = userElasticCrud.updateUser(USER_ID, userInfo);

        verify(userMapper).updateElasticUser(elasticUser, userInfo);
        assertEquals(user, result);
    }

    @Test
    void testUpdateUserThrowException() {
        when(elasticUserRepository.findById(USER_ID)).thenReturn(Optional.empty());

        ErrorResponseException errorResponseException = assertThrows(ErrorResponseException.class,
                () -> userElasticCrud.updateUser(USER_ID, userInfo));

        assertEquals(HttpStatus.NOT_FOUND, errorResponseException.getStatusCode());
    }

    @Test
    void testDeleteUser() {
        userElasticCrud.deleteUser(USER_ID);

        verify(elasticUserRepository).deleteById(USER_ID);
    }
}
