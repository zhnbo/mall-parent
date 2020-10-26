package com.woniuxy.user.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.woniuxy.commons.dto.UserDTO;
import com.woniuxy.commons.entity.User;
import com.woniuxy.commons.param.UserParam;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zh_o
 * @since 2020-10-21
 */
public interface UserService extends IService<User> {

    /**
     * 根据用户 id 查询详细信息
     * @param uId 用户 id
     */
    UserDTO getUserById(Integer uId) throws Exception;

    /**
     * 新增用户
     * @param userParam 新增用户信息
     */
    void saveUser(UserParam userParam) throws Exception;

    /**
     * 获取用户列表
     * @param pageNum 当前页
     * @param pageSize 每页显示条数
     */
    IPage<User> listUser(Integer pageNum, Integer pageSize) throws Exception;

    /**
     * 用户登录
     * @param tel 电话
     * @param password 密码
     * @return 令牌
     */
    String login(String tel, String password);
}
