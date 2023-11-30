package me.fasterthanlight.technicaltask2.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@AllArgsConstructor
@Service
public class UserCrudStrategy {

    private static final int START_MYSQL_HOUR_UTC = 9;
    private static final int END_MYSQL_HOUR_UTC = 17;

    private UserElasticCrud userElasticCrud;
    private UserJpaCrud userJpaCrud;

    public UserCrud getUserCrud() {
        LocalDateTime now = LocalDateTime.now();
        var hour = now.getHour();
        var minutes = now.getMinute();

        return hour >= START_MYSQL_HOUR_UTC && (hour < END_MYSQL_HOUR_UTC || (hour == END_MYSQL_HOUR_UTC && minutes == 0))
                ? userJpaCrud : userElasticCrud;
    }
}
