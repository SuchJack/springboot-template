package com.lhk.springbootinit;

import cn.hutool.core.util.IdUtil;

import java.time.Year;
import java.util.UUID;

public class testSnowflake {


    public static void main(String[] args) {
        // 用户 ID
        System.out.println("MSH2025" + IdUtil.getSnowflakeNextIdStr().substring(7));
        // 帖子 ID
        System.out.println(Year.now().getValue() + IdUtil.getSnowflakeNextIdStr().substring(4));
        String string = UUID.randomUUID().toString();
        System.out.println(string);
    }
}
