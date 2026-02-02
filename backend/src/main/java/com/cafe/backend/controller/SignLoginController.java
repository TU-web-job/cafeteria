/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.cafe.backend.controller;

import java.util.Map;

import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cafe.backend.dto.LoginDTO;
import com.cafe.backend.dto.SignupDTO;
import com.cafe.backend.dto.UserDTO;
import com.cafe.backend.service.SignLoginService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

/**
 *
 * @author tu
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
public class SignLoginController {

    private final SignLoginService service;

    @GetMapping("/user/signin")
    public String signin(){
        return "/api/signin";
    }


    @PostMapping("/user/signin")
    public ResponseEntity<Map<String, Object>> signin(@Valid @RequestBody SignupDTO dto, HttpSession session) {
        var user = service.signinCustomer(dto);
        session.setAttribute("LOGIN_USER", user.getUserName());
        session.setAttribute("LOGIN_ID", user.getUserId());
        session.setAttribute("LOGIN_ADDRESS", user.getAddress());
        session.setAttribute("LOGIN_EMAIL", user.getEmail());
        session.setAttribute("LOGIN_PHONE", user.getPhone());
        session.setAttribute("ROLE", user.getRole());
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("result","created"));
    }

    @PostMapping("/staff/staffSignin")
    public ResponseEntity<Map<String, Object>> staffSignin(@Valid @RequestBody SignupDTO dto, HttpSession session) {
        var user = service.signupStaff(dto);
        session.setAttribute("LOGIN_USER", user.getUserName());
        session.setAttribute("LOGIN_ID", user.getUserId());
        session.setAttribute("LOGIN_ADDRESS", user.getAddress());
        session.setAttribute("LOGIN_EMAIL", user.getEmail());
        session.setAttribute("LOGIN_PHONE", user.getPhone());
        session.setAttribute("ROLE", user.getRole());
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("result","created"));
    }

    @GetMapping("/login")
    public String login(){
        return "/api/login";
    }

    @PostMapping("/user/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody LoginDTO dto, HttpSession session) throws Exception{
        try{
            UserDTO userDTO =  service.loginCustomer(dto);
            session.setAttribute("LOGIN_INFO", userDTO);
            session.setAttribute("LOGIN_ID", userDTO.getUserId());
            session.setAttribute("ROLE", userDTO.getRole());

            return ResponseEntity.ok(Map.of("ok", true,
                "user", userDTO
            ));
        }catch(NotFoundException e) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("ok", false, "message", e.getMessage()));
        }
    }

    @PostMapping("/staff/login")
    public ResponseEntity<Map<String, Object>> stafflogin(@RequestBody LoginDTO dto, HttpSession session) throws Exception{
        try{
            UserDTO userDTO =  service.loginStaff(dto);
            session.setAttribute("LOGIN_INFO", userDTO);
            session.setAttribute("LOGIN_ID", userDTO.getUserId());
            session.setAttribute("ROLE", userDTO.getRole());

            return ResponseEntity.ok(Map.of("ok", true,
                "user", userDTO
            ));
        }catch(NotFoundException e) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("ok", false, "message", e.getMessage()));
        }
    }

    @GetMapping("/user/mypage")
    public ResponseEntity<Map<String, Object>> mypage(HttpSession session) {
        var user = session.getAttribute("LOGIN_INFO");
        if(user == null) {
            return ResponseEntity.status(401).body(Map.of("ok", false));
        }
        return ResponseEntity.ok(Map.of("ok", true, "user", user));
    }

    @GetMapping("/staff/staffpage")
    public ResponseEntity<Map<String, Object>> staffpage(HttpSession session) {
        var user = session.getAttribute("LOGIN_INFO");
        if(user == null) {
            return ResponseEntity.status(401).body(Map.of("ok", false));
        }
        return ResponseEntity.ok(Map.of("ok", true, "user", user));
    }

}
