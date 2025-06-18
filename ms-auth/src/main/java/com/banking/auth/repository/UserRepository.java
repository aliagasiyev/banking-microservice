package com.banking.auth.repository;

import com.banking.auth.entity.UserEntity;
import com.banking.auth.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByEmail(String email);


    Optional<UserEntity> findByRole(Role role);


}
