/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.cafe.backend.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

/**
 *
 * @author tu
 */
@Data
@Entity
@Table(name = "staff_detail")
public class StaffDetailEntity {

    @Id @Column(name = "staff_id") @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int staffId;

    @Column(name = "staff_code", nullable=false, length=8)
    private String staffCode;

    @Column(name = "staff_level", nullable=false)
    private int staffLevel;

    @Column(name = "last_update")
    private LocalDateTime lastUpdate;

    @MapsId
    @OneToOne
    @JoinColumn(name = "user_id", unique=true, nullable=false)
    private UserEntity user;

}
