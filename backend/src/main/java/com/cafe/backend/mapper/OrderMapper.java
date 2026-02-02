/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.cafe.backend.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.cafe.backend.dto.OrderDTO;

/**
 *
 * @author tu
 */
@Mapper
public interface OrderMapper {

    public OrderDTO getOrder(OrderDTO dto);

}
