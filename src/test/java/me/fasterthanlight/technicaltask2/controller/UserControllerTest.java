package me.fasterthanlight.technicaltask2.controller;

import me.fasterthanlight.technicaltask2.domain.dto.User;
import me.fasterthanlight.technicaltask2.domain.dto.UserInfo;
import me.fasterthanlight.technicaltask2.service.UserCrud;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    private static final String USER_ID = "userId";

    @Mock
    private UserCrud userCrud;
    @InjectMocks
    private UserController userController;

    @Test
    void testCreateUser() {
        var userInfo = mock(UserInfo.class);
        var user = mock(User.class);
        when(userCrud.createUser(userInfo)).thenReturn(user);

        ResponseEntity<User> responseEntity = userController.createUser(userInfo);

        verify(userCrud).createUser(userInfo);
        assertEquals(user, responseEntity.getBody());
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
    }

    @Test
    void testReadUser() {
        userController.readUser(USER_ID);

        verify(userCrud).readUser(USER_ID);
    }

    @Test
    void testUpdateUser() {
        var userInfo = mock(UserInfo.class);

        userController.updateUser(USER_ID, userInfo);

        verify(userCrud).updateUser(USER_ID, userInfo);
    }

    @Test
    void testDeleteUser() {
        ResponseEntity<Void> responseEntity = userController.deleteUser(USER_ID);

        verify(userCrud).deleteUser(USER_ID);
        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
    }
}
