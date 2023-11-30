package me.fasterthanlight.technicaltask2.service;

import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mockStatic;

@ExtendWith(MockitoExtension.class)
class UserCrudStrategyTest {

    @Mock
    private UserElasticCrud userElasticCrud;
    @Mock
    private UserJpaCrud userJpaCrud;
    @InjectMocks
    private UserCrudStrategy userCrudStrategy;

    @ParameterizedTest
    @ValueSource(ints = {9, 10, 11, 12, 13, 14, 15, 16, 17})
    void testGetUserCrudWithJpa(int hours) {
        var localDate = LocalDateTime.of(2023, 11, 10, hours, 0);

        try (MockedStatic<LocalDateTime> localDateTimeMockedStatic = mockStatic(LocalDateTime.class)) {
            localDateTimeMockedStatic.when(LocalDateTime::now).thenReturn(localDate);

            UserCrud userCrud = userCrudStrategy.getUserCrud();
            assertEquals(UserJpaCrud.class, userCrud.getClass());
        }
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2, 3, 4, 5, 6, 7, 8, 17, 18, 19, 20, 21, 22, 23})
    void testGetUserCrudWithElastic(int hours) {
        var localDate = LocalDateTime.of(2023, 11, 10, hours, 25);

        try (MockedStatic<LocalDateTime> localDateTimeMockedStatic = mockStatic(LocalDateTime.class)) {
            localDateTimeMockedStatic.when(LocalDateTime::now).thenReturn(localDate);

            UserCrud userCrud = userCrudStrategy.getUserCrud();
            assertEquals(UserElasticCrud.class, userCrud.getClass());
        }
    }
}
