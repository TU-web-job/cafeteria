/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.cafe.backend.service.serviceImpl;

import java.time.LocalDateTime;

import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cafe.backend.dto.LoginDTO;
import com.cafe.backend.dto.SignupDTO;
import com.cafe.backend.dto.UserDTO;
import com.cafe.backend.entity.PointHistoryEntity;
import com.cafe.backend.entity.StaffDetailEntity;
import com.cafe.backend.entity.UserEntity;
import com.cafe.backend.repository.PointRepository;
import com.cafe.backend.repository.StaffDetailRepository;
import com.cafe.backend.repository.UserRepository;
import com.cafe.backend.service.SignLoginService;

import lombok.RequiredArgsConstructor;

/**
 *
 * @author tu
 */
@Service
@RequiredArgsConstructor
public class SignLoginServiceImpl implements SignLoginService{

    private final UserRepository repo;
    private final PointRepository pointRepo;
    private final StaffDetailRepository staffRepo;

    public static final class Role {
        public static final int STAFF = 0;

        public static final int CUSTOMER = 1;

        public static final int ADMIN = 9;

        private Role(){}
    }

    public static final class Staff {
        public static final int EMPLOYEE = 0;

        public static final int MANAGER = 1;

        public static final int ARIAMANAGER = 9;
    }

    /*顧客用登録 */
    @Override
    @Transactional
    public UserEntity signinCustomer(SignupDTO dto){
        UserEntity entity = signupDetail(dto);
        entity.setRole(Role.CUSTOMER);


        PointHistoryEntity pEntity = new PointHistoryEntity();
        pEntity.setTotalPoint(0);
        pEntity.setLastOrderDate(LocalDateTime.now());
        pEntity.setUser(entity);
        pointRepo.save(pEntity);
        return repo.save(entity);
    }

    /*スタッフ用登録 */
    @Override
    @Transactional
    public UserEntity signupStaff(SignupDTO dto) {
        UserEntity entity = signupDetail(dto);
        entity.setRole(Role.STAFF);
        repo.save(entity);

        StaffDetailEntity sEntity  = new StaffDetailEntity();
        sEntity.setUser(entity);
        sEntity.setStaffLevel(Staff.EMPLOYEE);
        sEntity.setStaffCode(staffCodeCreate(entity.getUserId()));
        sEntity.setLastUpdate(LocalDateTime.now());
        staffRepo.save(sEntity);

        return entity;
    }

    /*顧客用ログイン */
    @Override
    @Transactional
    public UserDTO loginCustomer(LoginDTO dto) throws Exception {
        UserDTO userDTO = loginDetail(dto);
        
        int totalPoint = pointRepo.findTopByUserUserIdOrderByLastOrderDateDesc(userDTO.getUserId())
        .map(PointHistoryEntity::getTotalPoint).orElse(0);
        return userDTO.toBuilder().totalPoint(totalPoint).build();
    }

    /*スタッフ用ログイン */
    @Override
    @Transactional
    public UserDTO loginStaff(LoginDTO dto) throws Exception{
        UserDTO userDTO = loginDetail(dto);

        return userDTO.toBuilder().build();
    }

    //共通登録項目
    private static UserEntity signupDetail(SignupDTO dto) {
        UserEntity entity = new UserEntity();
        entity.setUserName(dto.getUserName());
        entity.setAddress(dto.getAddress());
        entity.setEmail(dto.getEmail());
        entity.setPhone(dto.getPhone());
        entity.setPassword(dto.getPassword());
        return entity;
    }

    /*共通ログイン */
    private UserDTO loginDetail(LoginDTO dto) throws Exception{
        UserEntity entity  = repo.findByEmailAndPassword(dto.getEmail(), dto.getPassword())
                .orElseThrow(() -> new NotFoundException("対象のアカウントが見つかりません。"));

        if(entity == null) {
            throw new NullPointerException("データがありません。");
        }
        return UserDTO.builder()
        .userId(entity.getUserId())
        .userName(entity.getUserName())
        .address(entity.getAddress())
        .email(entity.getEmail())
        .phone(entity.getPhone())
        .role(entity.getRole())
        .loginAt(LocalDateTime.now())
        .build();
    }

    //スタッフコード作成
    private String staffCodeCreate(int userId) {
        String staffCode = String.format("ST%06d", userId);
        return staffCode;
    }
}
