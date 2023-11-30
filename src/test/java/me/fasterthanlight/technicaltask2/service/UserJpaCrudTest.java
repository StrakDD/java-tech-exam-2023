package me.fasterthanlight.technicaltask2.service;

import me.fasterthanlight.technicaltask2.domain.dto.User;
import me.fasterthanlight.technicaltask2.domain.dto.UserInfo;
import me.fasterthanlight.technicaltask2.domain.enitity.JpaUser;
import me.fasterthanlight.technicaltask2.mapper.UserMapper;
import me.fasterthanlight.technicaltask2.repository.JpaUserRepository;
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
class UserJpaCrudTest {

    private static final String USER_ID = "1";

    @Mock
    private UserInfo userInfo;
    @Mock
    private User user;
    @Mock
    private JpaUser jpaUser;
    @Mock
    private JpaUserRepository jpaUserRepository;
    @Mock
    private UserMapper userMapper;
    @InjectMocks
    private UserJpaCrud userJpaCrud;

    @Test
    void testCreateUser() {
        when(userMapper.mapUserInfoToJpaUser(userInfo)).thenReturn(jpaUser);
        when(jpaUserRepository.save(jpaUser)).thenReturn(jpaUser);
        when(userMapper.mapJpaUserToUser(jpaUser)).thenReturn(user);

        User result = userJpaCrud.createUser(userInfo);

        assertEquals(user, result);
    }

    @Test
    void testReadUser() {
        when(jpaUserRepository.findById(USER_ID)).thenReturn(Optional.of(jpaUser));
        when(userMapper.mapJpaUserToUser(jpaUser)).thenReturn(user);

        User result = userJpaCrud.readUser(USER_ID);

        assertEquals(user, result);
    }

    @Test
    void testReadUserThrowException() {
        when(jpaUserRepository.findById(USER_ID)).thenReturn(Optional.empty());

        ErrorResponseException errorResponseException = assertThrows(ErrorResponseException.class,
                () -> userJpaCrud.readUser(USER_ID));

        assertEquals(HttpStatus.NOT_FOUND, errorResponseException.getStatusCode());
    }

    @Test
    void testUpdateUser() {
        when(jpaUserRepository.findById(USER_ID)).thenReturn(Optional.of(jpaUser));
        when(jpaUserRepository.save(jpaUser)).thenReturn(jpaUser);
        when(userMapper.mapJpaUserToUser(jpaUser)).thenReturn(user);

        User result = userJpaCrud.updateUser(USER_ID, userInfo);

        verify(userMapper).updateJpaUser(jpaUser, userInfo);
        assertEquals(user, result);
    }

    @Test
    void testUpdateUserThrowException() {
        when(jpaUserRepository.findById(USER_ID)).thenReturn(Optional.empty());

        ErrorResponseException errorResponseException = assertThrows(ErrorResponseException.class,
                () -> userJpaCrud.updateUser(USER_ID, userInfo));

        assertEquals(HttpStatus.NOT_FOUND, errorResponseException.getStatusCode());
    }

    @Test
    void testDeleteUser() {
        userJpaCrud.deleteUser(USER_ID);

        verify(jpaUserRepository).deleteById(USER_ID);
    }
}
