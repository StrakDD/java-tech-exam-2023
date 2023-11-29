package me.fasterthanlight.technicaltask2.mapper;

import me.fasterthanlight.technicaltask2.domain.dto.User;
import me.fasterthanlight.technicaltask2.domain.dto.UserInfo;
import me.fasterthanlight.technicaltask2.domain.enitity.ElasticUser;
import me.fasterthanlight.technicaltask2.domain.enitity.JpaUser;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    JpaUser mapUserInfoToJpaUser(UserInfo userInfo);

    void updateJpaUser(@MappingTarget JpaUser jpaUser, UserInfo userInfo);

    ElasticUser mapUserInfoToElasticUser(UserInfo userInfo);

    void updateElasticUser(@MappingTarget ElasticUser jpaUser, UserInfo userInfo);

    User mapJpaUserToUser(JpaUser jpaUser);

    List<User> mapJpaUsersToUsers(List<JpaUser> jpaUsers);

    User mapElasticUserToUser(ElasticUser elasticUser);

    List<User> mapElasticUsersToUsers(List<ElasticUser> elasticUsers);
}
