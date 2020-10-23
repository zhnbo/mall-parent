package com.woniuxy.user.controller;


import com.woniuxy.commons.param.UserParam;
import com.woniuxy.commons.result.ResponseResult;
import com.woniuxy.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zh_o
 * @since 2020-10-21
 */
@RestController
@Api(tags = "用户接口")
public class UserController {


    @Autowired
    private UserService userService;

    /**
     * 根据 id 查询用户
     * @param uId 用户 id
     */
    @ApiOperation("查询指定用户信息")
    @GetMapping("/user/{uId}")
    @ApiImplicitParam(name = "uId", value = "用户 ID")
    public Object getUserById(@PathVariable("uId") Integer uId) throws Exception {
        return ResponseResult.success(userService.getUserById(uId));
    }

    /**
     * 添加用户
     * @param userParam 新增用户信息
     */
    @PostMapping("/user/add")
    @ApiOperation("添加用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "tel", value = "用户电话"),
            @ApiImplicitParam(name = "name", value = "用户名"),
            @ApiImplicitParam(name = "age", value = "用户年龄")
    })
    public Object addUser(UserParam userParam) throws Exception {
        userService.saveUser(userParam);
        return ResponseResult.success("添加成功");
    }

    /**
     * 获取用户列表
     * @param pageNum 当前页
     * @param pageSize 每页显示数据数量
     */
    @GetMapping("/user/{pageNum}/{pageSize}")
    @ApiOperation("获取用户列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "当前页"),
            @ApiImplicitParam(name = "pageSize", value = "每页显示数据数量")
    })
    public Object listUser(@PathVariable("pageNum") Integer pageNum, @PathVariable("pageSize") Integer pageSize) throws Exception {
        return ResponseResult.success(userService.listUser(pageNum, pageSize));
    }

}

