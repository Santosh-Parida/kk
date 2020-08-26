package com.project.ems.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.ems.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUserName(String userName);
}
