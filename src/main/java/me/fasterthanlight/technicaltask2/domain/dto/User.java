package me.fasterthanlight.technicaltask2.domain.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class User {

    private String id;
    private String firstName;
    private String lastName;
    private LocalDateTime lastLoginTimeUtc;
}
