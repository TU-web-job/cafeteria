/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.cafe.backend.controller;

import java.net.URI;
import java.util.List;
import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.cafe.backend.dto.OrderDTO;
import com.cafe.backend.dto.OrderListDTO;
import com.cafe.backend.dto.UserDTO;
import com.cafe.backend.service.OrderService;

import lombok.RequiredArgsConstructor;

/**
 *
 * @author tu
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
public class OrderController {

    private final OrderService service;

    @GetMapping("/order")
    public ResponseEntity<Map<String, Object>> getOrder(@SessionAttribute(name = "LOGIN_INFO", required=false) UserDTO user){
        var drink = service.drinkMenu();
        var food = service.foodMenu();
        return ResponseEntity.ok(Map.of("ok", true,"user",user, "drink", drink, "food", food));
    }

    @PostMapping("/order")
    public ResponseEntity<Map<String, Object>> insertOrder(@RequestBody OrderDTO dto,
        @SessionAttribute(name = "LOGIN_INFO", required=false) UserDTO user) {
        int orderId = service.getOrder(dto, user.getUserId());
        return ResponseEntity.created(URI.create("/order/orderList/" + orderId))
                .body(Map.of("orderId", orderId));
    }

    @GetMapping("/order/orderList")
    public ResponseEntity<Map<String, Object>> orderList(@SessionAttribute(name = "LOGIN_INFO", required=false) UserDTO user){
        List<OrderListDTO> orderHistory = service.getOrderList(user.getUserId());
        return ResponseEntity.ok(Map.of("ok", true, "orderHistory", orderHistory));

    }

    @GetMapping("/order/orderList/{orderId}")
    public ResponseEntity<Map<String, Object>> orderListDetail(@SessionAttribute(name = "LOGIN_INFO", required=false) UserDTO user, @PathVariable Integer orderId){
        if(user == null) return ResponseEntity.status(401).body(Map.of("ok", false));
        var orderDetail = service.getOrderDetail(orderId, user.getUserId());
        return ResponseEntity.ok(Map.of("ok", true, "orderDetail",orderDetail));
    }
}
