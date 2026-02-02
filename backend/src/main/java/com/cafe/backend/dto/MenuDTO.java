/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.cafe.backend.dto;

import java.io.Serializable;

import lombok.Builder;
import lombok.Data;

/**
 *
 * @author tu
 */
@Data
@Builder(toBuilder=true)
public class MenuDTO implements Serializable{
    private static final long serialVersionUID = 1L;

    private int menuId;

    private String menuName;

    private int price;

    private int point;

    private String menuImg;

    private String mimeType;
}
