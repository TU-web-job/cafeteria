/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */

package com.cafe.backend.service;

import java.util.List;

import com.cafe.backend.dto.MenuDTO;
import com.cafe.backend.dto.OrderDTO;
import com.cafe.backend.dto.OrderItemsDTO;
import com.cafe.backend.dto.OrderListDTO;

/**
 *
 * @author tu
 */
public interface OrderService {

    //注文
    public int getOrder(OrderDTO dto, Integer userId);

    //注文リスト
    public List<OrderListDTO> getOrderList(Integer userId);

    //注文詳細
    public OrderItemsDTO getOrderDetail(Integer orderId, Integer userId);

    //ドリンクメニュー
    public List<MenuDTO> drinkMenu();

    //フードメニュー
    public List<MenuDTO> foodMenu();

}
