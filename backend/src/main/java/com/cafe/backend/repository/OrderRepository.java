/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */

package com.cafe.backend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cafe.backend.entity.OrderEntity;

/**
 *
 * @author tu
 */
public interface OrderRepository extends JpaRepository<OrderEntity, Integer>{
    List<OrderEntity> findByUserUserIdOrderByOrderDateDesc(Integer userId);

    @Query("""
        select o
        from OrderEntity o
        left join fetch o.item i
        left join fetch i.menu m
        where o.orderId = :orderId and o.user.userId = :userId
        """)
    Optional<OrderEntity> findDetailById(@Param("orderId") Integer orderId, @Param("userId") Integer userId);
}
