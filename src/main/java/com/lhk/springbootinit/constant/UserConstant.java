package com.lhk.springbootinit.constant;

/**
 * 用户常量
 */
public interface UserConstant {

    /**
     * 用户登录态键
     */
    String USER_LOGIN_STATE = "user_login";

    //  region 权限

    /**
     * 默认角色
     */
    String DEFAULT_ROLE = "user";

    /**
     * 管理员角色
     */
    String ADMIN_ROLE = "admin";

    /**
     * 被封号
     */
    String BAN_ROLE = "ban";

    /**
     * 盐值，混淆密码
     */
    String SALT = "SuchJack";

    /**
     * 默认密码
     */
    String USER_DEFAULT_PASSWORD = "12345678";

    // endregion
}
