/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.cafe.backend.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

/**
 *
 * @author tu
 */

@Data
@Entity
@Table(name = "point_history")
public class PointHistoryEntity {

    @Id @Column(name = "point_id") @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int pointId;

    @Column(name = "total_point", nullable=false)
    private int totalPoint;

    @Column(name = "get_point")
    private int getPoint;

    @Column(name = "last_order_date", nullable=false)
    private LocalDateTime lastOrderDate;

    @ManyToOne(optional=false, fetch=FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable=false)
    private UserEntity user;

    @ManyToOne(optional=false, fetch=FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable=false)
    private OrderEntity order;
}
