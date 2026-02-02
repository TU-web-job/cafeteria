/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.cafe.backend.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

/**
 *
 * @author tu
 */
@Data
@Entity
@Table(name = "users")
public class UserEntity {

    @Id @Column(name = "user_id") @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;

    @Column(name = "user_name", nullable=false)
    private String userName;

    @Column(name = "address")
    private String address;

    @Column(name = "email", nullable=false)
    private String email;

    @Column(name = "phone")
    private String phone;

    @Column(name = "password", nullable=false)
    private String password;

    @Column(name = "role", nullable=false)
    private int role;

    @OneToOne(mappedBy="user", cascade= CascadeType.ALL, fetch=FetchType.LAZY)
    private StaffDetailEntity staffEntity;

    @OneToMany(mappedBy="user", cascade=CascadeType.ALL, fetch=FetchType.LAZY)
    private List<PointHistoryEntity> pointHistoryList;

    @OneToMany(mappedBy="user", cascade=CascadeType.ALL, fetch=FetchType.LAZY)
    private List<OrderEntity> order;
}
