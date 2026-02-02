/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.cafe.backend.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Builder;
import lombok.Data;

/**
 *
 * @author tu
 */
@Data
@Builder(toBuilder=true)
public class UserDTO implements Serializable{
    private static final long serialVersionUID = 1L;

    private Integer userId;

    private String userName;

    private String address;

    private String email;

    private String phone;

    private int totalPoint;

    private int role;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private LocalDateTime loginAt;
}
