/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.cafe.backend.service.serviceImpl;

import java.util.Base64;

import org.springframework.stereotype.Service;

import java.util.List;

import com.cafe.backend.dto.MenuDTO;
import com.cafe.backend.entity.MenuListEntity;
import com.cafe.backend.repository.MenuListRepository;
import com.cafe.backend.service.MenuService;

import lombok.RequiredArgsConstructor;

/**
 *
 * @author tu
 */
@Service
@RequiredArgsConstructor
public class MenuServiceImpl implements MenuService{

    private final MenuListRepository menuRepo;

    private static final int DRINK = 0;
    private static final int FOOD = 1;

    @Override
    public List<MenuDTO> drinkMenu() {
        List<MenuDTO> drinkDTO = menuRepo.findAllByMenuFlgOrderByMenuIdAsc(DRINK)
            .stream().map(this::toDTO).toList();
        return drinkDTO;
    }

    @Override
    public List<MenuDTO> foodMenu(){
        List<MenuDTO> foodDTO = menuRepo.findAllByMenuFlgOrderByMenuIdAsc(FOOD)
            .stream().map(this::toDTO).toList();
        return foodDTO;
    }


    private MenuDTO toDTO(MenuListEntity entity) {
        String menuImg = byteMime(entity.getMenuImg());
        String base64 = entity.getMenuImg() != null ? Base64.getEncoder().encodeToString(entity.getMenuImg()) : null;

        return MenuDTO.builder()
            .menuId(entity.getMenuId())
            .menuName(entity.getMenuName())
            .price(entity.getMenuPrice())
            .point(entity.getMenuPoint())
            .menuImg(menuImg)
            .mimeType(base64)
            .build();
    }

    private String byteMime(byte[] bytes) {
        if(bytes == null || bytes.length < 4) return "application/obtet-stream";

        if((bytes[0] & 0xFF) == 0xFF && (bytes[1] & 0xFF) == 0xD8) return "image/jpeg";

        if((bytes[0] & 0xFF) == 0x89 && bytes[1] == 0x50 && bytes[2] == 0x4E && bytes[3] == 0x47) return "image/jpg";

        if((bytes[0] == 0x47 && bytes[1] == 0x49 &&bytes[2] == 0x46)) return "image/gif";

        if(bytes.length >= 12 &&
            bytes[0]=='R' && bytes[1]=='I' && bytes[2]=='F' && bytes[3]=='F' &&
            bytes[8]=='W' && bytes[9]=='E' && bytes[10]=='B' && bytes[11]=='P') return "image/webp";
        return "imgae/jpeg";
    }

}
