package com.lhk.springbootinit.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lhk.springbootinit.common.DeleteRequest;
import com.lhk.springbootinit.model.dto.user.*;
import com.lhk.springbootinit.model.entity.User;
import com.lhk.springbootinit.model.vo.LoginUserVO;
import com.lhk.springbootinit.model.vo.UserVO;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import me.chanjar.weixin.common.bean.WxOAuth2UserInfo;

/**
 * 用户服务
 */
public interface UserService extends IService<User> {

    /**
     * 用户注册
     * @param userRegisterRequest 用户注册封装类
     * @return 新用户 id
     */
    long userRegister(UserRegisterRequest userRegisterRequest);

    /**
     * 用户登录
     * @param request 请求
     * @return 脱敏后的用户信息
     */
    LoginUserVO userLogin(UserLoginRequest userLoginRequest, HttpServletRequest request);

    /**
     * 用户登录（微信开放平台）
     * @param wxOAuth2UserInfo 从微信获取的用户信息
     * @param request
     * @return 脱敏后的用户信息
     */
    LoginUserVO userLoginByMpOpen(WxOAuth2UserInfo wxOAuth2UserInfo, HttpServletRequest request);

    /**
     * 获取当前登录用户(未脱敏)
     * @param request 请求
     * @return User
     */
    User getLoginUser(HttpServletRequest request);

    /**
     * 获取当前登录用户（允许未登录）
     * @param request 请求
     * @return User
     */
    User getLoginUserPermitNull(HttpServletRequest request);

    /**
     * 是否为管理员
     * @param request 请求
     * @return boolean
     */
    boolean isAdmin(HttpServletRequest request);

    /**
     * 是否为管理员
     * @param user
     * @return
     */
    boolean isAdmin(User user);

    /**
     * 用户注销
     * @param request 请求
     * @return boolean
     */
    boolean userLogout(HttpServletRequest request);

    /**
     * 获取脱敏的已登录用户信息
     * @return LoginUserVO
     */
    LoginUserVO getLoginUserVO(User user);

    /**
     * 获取脱敏的用户信息
     *
     * @param user
     * @return
     */
    UserVO getUserVO(User user);

    /**
     * 获取脱敏的用户信息
     * @param userList
     * @return
     */
    List<UserVO> getUserVOList(List<User> userList);

    /**
     * 获取查询条件
     * @param userQueryRequest
     * @return
     */
    QueryWrapper<User> getQueryWrapper(UserQueryRequest userQueryRequest);

    /**
     * 管理页添加用户
     * @param userAddRequest 用户添加封装类
     * @return 新用户 id
     */
    long addUserByAdmin(UserAddRequest userAddRequest);

    /**
     * 管理页删除用户
     * @param deleteRequest 删除封装类
     * @return boolean
     */
    boolean deleteUserByAdmin(DeleteRequest deleteRequest);

    /**
     * 管理页更新用户
     * @param userUpdateRequest 用户更新封装类
     * @return boolean
     */
    boolean updateUserByAdmin(UserUpdateRequest userUpdateRequest);

    /**
     * 根据 id 获取用户(仅管理员)
     * @param id id
     * @return User
     */
    User getUserByIdByAdmin(long id);

    /**
     * 根据 id 获取包装类
     * @param id id
     * @return UserVO
     */
    UserVO getUserVOById(long id);

    /**
     * 分页获取用户列表（仅管理员）
     * @param userQueryRequest 用户查询封装类
     * @return Page<User>
     */
    Page<User> listUserByPageByAdmin(UserQueryRequest userQueryRequest);

    /**
     * 分页获取用户封装列表
     * @param userQueryRequest 用户查询封装类
     * @return Page<UserVO>
     */
    Page<UserVO> listUserVOByPage(UserQueryRequest userQueryRequest);

    /**
     * 更新个人信息
     * @param userUpdateMyRequest 用户更新包装类
     * @param request 请求
     * @return Boolean
     */
    Boolean updateMyUser(UserUpdateMyRequest userUpdateMyRequest, HttpServletRequest request);

}
