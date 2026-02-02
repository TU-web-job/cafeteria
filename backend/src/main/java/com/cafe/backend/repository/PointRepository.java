/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */

package com.cafe.backend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cafe.backend.entity.PointHistoryEntity;

/**
 *
 * @author tu
 */
public interface PointRepository extends JpaRepository<PointHistoryEntity, Long>{

    List<PointHistoryEntity> findByUser_UserId(Integer userId);

    Optional<PointHistoryEntity> findTopByUserUserIdOrderByLastOrderDateDesc(Integer userId);

    Optional<PointHistoryEntity> findTopByUserUserIdAndOrderOrderIdOrderByLastOrderDateDesc(Integer userId, Integer orderId);


}
