package com.lhk.springbootinit.service;

import javax.annotation.Resource;
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
            long result = userService.userRegister(userAccount, userPassword, checkPassword);
            Assertions.assertEquals(-1, result);
            userAccount = "su";
            result = userService.userRegister(userAccount, userPassword, checkPassword);
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
            try {
                long result = userService.userRegister(userAccount, userPassword, checkPassword);
                System.out.println(result);
            } catch (Exception e) {

            }
        }
    }
}
