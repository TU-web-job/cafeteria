/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */

package com.cafe.backend.service;

import java.util.List;

import com.cafe.backend.dto.MenuDTO;

/**
 *
 * @author tu
 */
public interface MenuService {

    public List<MenuDTO> drinkMenu();
    public List<MenuDTO> foodMenu();

}
