/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.cafe.backend.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author tu
 */
@Data
@Builder(toBuilder=true)
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemsDTO implements Serializable{
    private static final long serialVersionUID = 1L;

    private int orderId;

    private LocalDateTime orderDate;

    private Integer subtotal;

    private Integer tax;

    private Integer totalPrice;

    private Integer getPoint;

    private Integer totalPoint;

    private List<ItemList> lists;

    @Data
    @Builder(toBuilder=true)
    public static class ItemList implements Serializable{

        private static final long serialVersionUID = 1L;

        private String orderName;

        private Integer orderPrice;

        private Integer orderPoint;

        private Integer orderCount;

    }

 }
