package me.fasterthanlight.technicaltask2.domain.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserInfo {

    @Size(max = 255)
    @NotEmpty
    private String firstName;

    @Size(max = 255)
    @NotEmpty
    private String lastName;
}
