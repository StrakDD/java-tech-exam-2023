package me.fasterthanlight.technicaltask2.domain.enitity;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Id;
import lombok.Data;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.LocalDateTime;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Document(indexName = "users")
public class ElasticUser {
    @Id
    private String id;

    @Field(type = FieldType.Text, name = "firstName")
    private String firstName;

    @Field(type = FieldType.Text, name = "lastName")
    private String lastName;

    @Field(type = FieldType.Date_Nanos, name = "lastLoginTimeUtc", format = DateFormat.date_hour_minute_second_millis)
    private LocalDateTime lastLoginTimeUtc;
}
