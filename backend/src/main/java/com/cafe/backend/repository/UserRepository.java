/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */

package com.cafe.backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cafe.backend.entity.UserEntity;

/**
 *
 * @author tu
 */
public interface UserRepository extends JpaRepository<UserEntity, Integer>{

    Optional<UserEntity> findByEmailAndPassword(String email, String password);
    UserEntity findByUserId(Integer userId);

}
