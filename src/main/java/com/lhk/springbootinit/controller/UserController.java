package com.lhk.springbootinit.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lhk.springbootinit.annotation.AuthCheck;
import com.lhk.springbootinit.common.BaseResponse;
import com.lhk.springbootinit.common.DeleteRequest;
import com.lhk.springbootinit.common.ErrorCode;
import com.lhk.springbootinit.common.ResultUtils;
import com.lhk.springbootinit.config.WxOpenConfig;
import com.lhk.springbootinit.constant.UserConstant;
import com.lhk.springbootinit.exception.BusinessException;
import com.lhk.springbootinit.model.dto.user.*;
import com.lhk.springbootinit.model.entity.User;
import com.lhk.springbootinit.model.vo.LoginUserVO;
import com.lhk.springbootinit.model.vo.UserVO;
import com.lhk.springbootinit.service.UserService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.bean.WxOAuth2UserInfo;
import me.chanjar.weixin.common.bean.oauth2.WxOAuth2AccessToken;
import me.chanjar.weixin.mp.api.WxMpService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 用户接口
 */
@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Resource
    private UserService userService;

    @Resource
    private WxOpenConfig wxOpenConfig;

    // region 登录相关

    /**
     * 用户注册
     * @param userRegisterRequest 用户注册封装类
     * @return 新用户 id
     */
    @PostMapping("/register")
    public BaseResponse<Long> userRegister(@RequestBody UserRegisterRequest userRegisterRequest) {
        long userId = userService.userRegister(userRegisterRequest);
        return ResultUtils.success(userId);
    }

    /**
     * 用户登录
     * @param userLoginRequest 用户登录封装类
     * @param request 请求
     * @return LoginUserVO
     */
    @PostMapping("/login")
    public BaseResponse<LoginUserVO> userLogin(@RequestBody UserLoginRequest userLoginRequest, HttpServletRequest request) {
        LoginUserVO loginUserVO = userService.userLogin(userLoginRequest, request);
        return ResultUtils.success(loginUserVO);
    }

    /**
     * 用户登录（微信开放平台）
     */
    @GetMapping("/login/wx_open")
    public BaseResponse<LoginUserVO> userLoginByWxOpen(HttpServletRequest request, HttpServletResponse response,
            @RequestParam("code") String code) {
        WxOAuth2AccessToken accessToken;
        try {
            WxMpService wxService = wxOpenConfig.getWxMpService();
            accessToken = wxService.getOAuth2Service().getAccessToken(code);
            WxOAuth2UserInfo userInfo = wxService.getOAuth2Service().getUserInfo(accessToken, code);
            String unionId = userInfo.getUnionId();
            String mpOpenId = userInfo.getOpenid();
            if (StringUtils.isAnyBlank(unionId, mpOpenId)) {
                throw new BusinessException(ErrorCode.SYSTEM_ERROR, "登录失败，系统错误");
            }
            return ResultUtils.success(userService.userLoginByMpOpen(userInfo, request));
        } catch (Exception e) {
            log.error("userLoginByWxOpen error", e);
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "登录失败，系统错误");
        }
    }

    /**
     * 用户注销
     * @param request 请求
     * @return boolean
     */
    @PostMapping("/logout")
    public BaseResponse<Boolean> userLogout(HttpServletRequest request) {
        boolean result = userService.userLogout(request);
        return ResultUtils.success(result);
    }

    /**
     * 获取当前登录用户
     * @param request 请求
     * @return LoginUserVO
     */
    @GetMapping("/get/login")
    public BaseResponse<LoginUserVO> getLoginUserVO(HttpServletRequest request) {
        User user = userService.getLoginUser(request);
        LoginUserVO loginUserVO = userService.getLoginUserVO(user);
        return ResultUtils.success(loginUserVO);
    }

    // endregion

    // region 增删改查

    /**
     * 管理页添加用户
     * @param userAddRequest 用户添加封装类
     * @return 新用户 id
     */
    @PostMapping("/add")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    @ApiOperation(value = "addUser(op)")
    public BaseResponse<Long> addUser(@RequestBody UserAddRequest userAddRequest) {
        long userId = userService.addUserByAdmin(userAddRequest);
        return ResultUtils.success(userId);
    }

    /**
     * 管理页删除用户
     * @param deleteRequest 删除封装类
     * @return boolean
     */
    @PostMapping("/delete")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    @ApiOperation(value = "deleteUser(op)")
    public BaseResponse<Boolean> deleteUser(@RequestBody DeleteRequest deleteRequest) {
        boolean result = userService.deleteUserByAdmin(deleteRequest);
        return ResultUtils.success(result);
    }

    /**
     * 管理页更新用户
     * @param userUpdateRequest 用户更新封装类
     * @return boolean
     */
    @PostMapping("/update")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    @ApiOperation(value = "updateUser(op)")
    public BaseResponse<Boolean> updateUser(@RequestBody UserUpdateRequest userUpdateRequest) {
        Boolean result = userService.updateUserByAdmin(userUpdateRequest);
        return ResultUtils.success(result);
    }

    /**
     * 根据 id 获取用户(仅管理员)
     * @param id id
     * @return User
     */
    @GetMapping("/get")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    @ApiOperation(value = "getUserById(op)")
    public BaseResponse<User> getUserById(long id) {
        User user = userService.getUserByIdByAdmin(id);
        return ResultUtils.success(user);
    }

    /**
     * 根据 id 获取包装类
     * @param id id
     * @return UserVO
     */
    @GetMapping("/get/vo")
    public BaseResponse<UserVO> getUserVOById(long id) {
        UserVO userVO = userService.getUserVOById(id);
        return ResultUtils.success(userVO);
    }

    /**
     * 分页获取用户列表（仅管理员）
     * @param userQueryRequest 用户查询封装类
     * @return Page<User>
     */
    @PostMapping("/list/page")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    @ApiOperation(value = "listUserByPage(op)")
    public BaseResponse<Page<User>> listUserByPage(@RequestBody UserQueryRequest userQueryRequest) {
        Page<User> userPage = userService.listUserByPageByAdmin(userQueryRequest);
        return ResultUtils.success(userPage);
    }

    /**
     * 分页获取用户封装列表
     * @param userQueryRequest 用户查询封装类
     * @return Page<UserVO>
     */
    @PostMapping("/list/page/vo")
    public BaseResponse<Page<UserVO>> listUserVOByPage(@RequestBody UserQueryRequest userQueryRequest) {
        Page<UserVO> userVOPage = userService.listUserVOByPage(userQueryRequest);
        return ResultUtils.success(userVOPage);
    }

    // endregion

    /**
     * 更新个人信息
     * @param userUpdateMyRequest 用户更新包装类
     * @param request 请求
     * @return Boolean
     */
    @PostMapping("/update/my")
    public BaseResponse<Boolean> updateMyUser(@RequestBody UserUpdateMyRequest userUpdateMyRequest, HttpServletRequest request) {
        Boolean result = userService.updateMyUser(userUpdateMyRequest,request);
        return ResultUtils.success(result);
    }
}
