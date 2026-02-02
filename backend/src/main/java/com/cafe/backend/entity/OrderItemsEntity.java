/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.cafe.backend.entity;

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
@Table(name = "order_items")
public class OrderItemsEntity {

    @Id @Column(name = "order_items_id") @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int orderItemsId;

    @Column(name = "order_name", nullable=false)
    private String orderName;

    @Column(name = "order_price", nullable=false)
    private Integer orderPrice;

    @Column(name = "num", nullable=false)
    private int num;

    @ManyToOne(optional=false, fetch=FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable=false)
    private OrderEntity order;

    @ManyToOne(optional=false, fetch=FetchType.LAZY)
    @JoinColumn(name = "menu_id", nullable=false)
    private MenuListEntity menu;
}
