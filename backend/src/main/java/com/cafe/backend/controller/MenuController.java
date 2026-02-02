/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.cafe.backend.controller;

import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cafe.backend.service.MenuService;

import lombok.RequiredArgsConstructor;

/**
 *
 * @author tu
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
public class MenuController {

    private final MenuService service;

    @GetMapping("/menu")
    public ResponseEntity<Map<String, Object>> menuList() {
        var drink = service.drinkMenu();
        var food = service.foodMenu();
        return ResponseEntity.ok(Map.of("ok", true, "drink", drink, "food", food));
    }

}
