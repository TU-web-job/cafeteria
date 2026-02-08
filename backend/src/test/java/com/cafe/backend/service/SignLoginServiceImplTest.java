/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */

package com.cafe.backend.service;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.cafe.backend.dto.SignupDTO;
import com.cafe.backend.entity.PointHistoryEntity;
import com.cafe.backend.entity.UserEntity;
import com.cafe.backend.repository.PointRepository;
import com.cafe.backend.repository.StaffDetailRepository;
import com.cafe.backend.repository.UserRepository;
import com.cafe.backend.service.serviceImpl.SignLoginServiceImpl;
 
/**
 *
 * @author tu
 */
@ExtendWith(MockitoExtension.class)
public class SignLoginServiceImplTest {

    @Mock
    UserRepository userRepo;
    @Mock
    PointRepository pointRepo;
    @Mock
    StaffDetailRepository staffRepo;

    SignLoginServiceImpl target;

    @BeforeEach
    void setBefore() {
        target = new SignLoginServiceImpl(userRepo, pointRepo, staffRepo);
    }

    private static SignupDTO dto(String name, String address, String email, String phone, String password) {
        var d = new SignupDTO();
        d.setUserName(name);
        d.setAddress(address);
        d.setEmail(email);
        d.setPhone(phone);
        d.setPassword(password);
        return d;
    }

    public static final class Role {
        public static final int CUSTOMER = 1;
    }

    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    @DisplayName("顧客登録")
    class signinCustomerTest {

        Stream<Arguments> signupError() {
            return Stream.of(
                Arguments.of(
                dto("Test1", "TestAddress1", "test1@test.co.jp", "000-0000-0000", null)),
                Arguments.of(
                dto(null, "TestAddress2", "test2@test.co.jp", "111-1111-1111", "password")),
                Arguments.of(
                dto("Test3", "TestAddress3", null, "222-2222-2222", "password"))
            );
        }

        Stream<Arguments> signupCorrect() {
            return Stream.of(
                Arguments.of(
                dto("Test1", "TestAddress1", "test1@test.co.jp", "000-0000-0000", "password"))
            );
        }

        @ParameterizedTest(name = "[{index}]")
        @MethodSource("signupCorrect")
        @DisplayName("正常終了")
        void signinCustomerTest1(SignupDTO dto) {

            when(pointRepo.save(Mockito.any(PointHistoryEntity.class)))
                .thenAnswer(i -> i.getArgument(0));

            when(userRepo.save(Mockito.any(UserEntity.class)))
                .thenAnswer(i -> {
                    UserEntity e = i.getArgument(0, UserEntity.class);
                    e.setUserId(123);
                    return e;
                });

            var save = target.signinCustomer(dto);

            ArgumentCaptor<PointHistoryEntity> ph = ArgumentCaptor.forClass(PointHistoryEntity.class);
            ArgumentCaptor<UserEntity> ue = ArgumentCaptor.forClass(UserEntity.class);

            verify(pointRepo).save(ph.capture());
            verify(userRepo).save(ue.capture());
            verifyNoMoreInteractions(pointRepo, userRepo);
            assertThat(save.getRole()).isEqualTo(SignLoginServiceImpl.Role.CUSTOMER);
            assertThat(save.getEmail()).isEqualTo(dto.getEmail());

        }

        @ParameterizedTest(name = "[{index}]")
        @MethodSource("signupError")
        @DisplayName("異常終了")
        void signinCustomerError(SignupDTO dto) {
            assertThatThrownBy(() -> target.signinCustomer(dto))
                .isInstanceOf(IllegalArgumentException.class);

            verifyNoInteractions(userRepo, pointRepo);
            
        }
    }

}