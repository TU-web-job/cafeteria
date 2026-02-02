/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.cafe.backend.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

/**
 *
 * @author tu
 */
@Data
@Entity
@Table(name = "orders")
public class OrderEntity {

    @Id @Column(name = "order_id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int orderId;

    @Column(name = "order_date")
    private LocalDateTime orderDate;

    @Column(name = "subtotal")
    private Integer subtotal;

    @Column(name = "tax")
    private Integer tax;

    @Column(name = "total")
    private Integer total;

    @ManyToOne(optional=false, fetch=FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable=false)
    private UserEntity user;

    @OneToMany(mappedBy="order", cascade=CascadeType.ALL, orphanRemoval=true)
    private List<OrderItemsEntity> item = new ArrayList<>();
}
