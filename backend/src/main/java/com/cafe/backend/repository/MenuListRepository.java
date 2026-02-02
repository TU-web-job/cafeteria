/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */

package com.cafe.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cafe.backend.entity.MenuListEntity;

/**
 *
 * @author tu
 */
public interface MenuListRepository extends JpaRepository<MenuListEntity, Integer>{
    List<MenuListEntity> findAllByMenuFlgOrderByMenuIdAsc(int menuflg);

    List<MenuList> findByMenuFlgOrderByMenuIdAsc(int menuflg);

    interface MenuList {
        int getMenuId();
        String getMenuName();
        int getMenuPoint();
        int getMenuPrice();
    }

}


