package com.lhk.springbootinit.service;

import javax.annotation.Resource;

import com.lhk.springbootinit.model.dto.user.UserRegisterRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * 用户服务测试
 *
 * 
 * 
 */
@SpringBootTest
public class UserServiceTest {

    @Resource
    private UserService userService;

    @Test
    void userRegister() {
        String userAccount = "lhk";
        String userPassword = "";
        String checkPassword = "123456";
        try {
            UserRegisterRequest userRegisterRequest = new UserRegisterRequest();
            userRegisterRequest.setUserAccount(userAccount);
            userRegisterRequest.setUserPassword(userPassword);
            userRegisterRequest.setCheckPassword(checkPassword);
            long result = userService.userRegister(userRegisterRequest);
            Assertions.assertEquals(-1, result);
            userAccount = "su";
            userRegisterRequest.setUserAccount(userAccount);
            result = userService.userRegister(userRegisterRequest);
            Assertions.assertEquals(-1, result);
        } catch (Exception e) {

        }
    }

    @Test
    void userRegister2() {
        // 批量注册用户 100
        for (int i = 0; i < 100; i++) {
            String userAccount = "lhkkkkk" + i;
            String userPassword = "12345678";
            String checkPassword = "12345678";
            UserRegisterRequest userRegisterRequest = new UserRegisterRequest();
            userRegisterRequest.setUserAccount(userAccount);
            userRegisterRequest.setUserPassword(userPassword);
            userRegisterRequest.setCheckPassword(checkPassword);
            try {
                long result = userService.userRegister(userRegisterRequest);
                System.out.println(result);
            } catch (Exception e) {

            }
        }
    }
}
