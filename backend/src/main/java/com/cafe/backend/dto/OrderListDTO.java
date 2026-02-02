/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.cafe.backend.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

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
@NoArgsConstructor
@AllArgsConstructor
public class OrderListDTO implements  Serializable {
    private static final long serialVersionUID = 1L;

    private int orderId;

    private int userid;

    private LocalDateTime orderDate;

    private int orderPrice;

    private Integer totalPoint;

    private String tax;

}
