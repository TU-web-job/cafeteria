/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.cafe.backend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author tu
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignupDTO {

    @NotBlank(message = "必須項目です。")
    private String userName;

    private String address;

    @NotBlank(message = "必須項目です。")
    @Email(message = "正しいメールアドレスを入力してください。")
    private String email;

    @Pattern(regexp = "^(?:\\d{2}-\\d{4}-\\d{4}|\\d{3}-\\d{4}-\\d{4}|\\d{3}-\\d{3}-\\d{4})?$", message = "正しい電話番号を数字のみで入力してください。")
    private String phone;

    @NotBlank(message = "必須項目です。")
    @Pattern(regexp = "^[A-Za-z0-9]{4,16}$", message = "4桁から16桁で入力してください。")
    private String password;
}
