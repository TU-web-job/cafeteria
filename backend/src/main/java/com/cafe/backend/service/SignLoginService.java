/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */

package com.cafe.backend.service;

import com.cafe.backend.dto.LoginDTO;
import com.cafe.backend.dto.SignupDTO;
import com.cafe.backend.dto.UserDTO;
import com.cafe.backend.entity.UserEntity;

/**
 *
 * @author tu
 */
public interface SignLoginService {

    public UserEntity signinCustomer(SignupDTO dto);

    public UserEntity signupStaff(SignupDTO dto);

    public UserDTO loginCustomer(LoginDTO dto) throws Exception;

    public UserDTO loginStaff(LoginDTO dto) throws Exception;

}
