/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.cafe.backend.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author tu
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {

    // 8 or 10
    private Integer taxChoice;

    private List<Item> items;

    @Data
    public static class Item {
        private Integer menuId;

        private Integer count;

        private String type;
    }
}
