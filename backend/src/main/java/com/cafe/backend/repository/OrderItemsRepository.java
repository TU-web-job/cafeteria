/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */

package com.cafe.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cafe.backend.entity.OrderItemsEntity;

/**
 *
 * @author tu
 */
public interface OrderItemsRepository extends JpaRepository<OrderItemsEntity, Integer>{

    List<OrderItemsEntity> findByOrderOrderId(Integer orderId);
}
