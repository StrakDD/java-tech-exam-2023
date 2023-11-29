package me.fasterthanlight.technicaltask2.domain.enitity;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "users")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class JpaUser {
    @Id
    @UuidGenerator(style = UuidGenerator.Style.RANDOM)
    @Column(name = "id", nullable = false)
    private String id;

    @Size(max = 255)
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Size(max = 255)
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "last_login_time_utc")
    private LocalDateTime lastLoginTimeUtc;
}
