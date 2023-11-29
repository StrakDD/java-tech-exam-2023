package me.fasterthanlight.technicaltask2.repository;

import me.fasterthanlight.technicaltask2.domain.enitity.JpaUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaUserRepository extends JpaRepository<JpaUser, String> {
}
