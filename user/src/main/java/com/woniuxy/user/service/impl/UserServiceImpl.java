package com.woniuxy.user.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRule;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRuleManager;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.woniuxy.commons.dto.UserDTO;
import com.woniuxy.commons.entity.User;
import com.woniuxy.commons.jwt.util.JwtUtils;
import com.woniuxy.commons.param.UserParam;
import com.woniuxy.user.mapper.UserMapper;
import com.woniuxy.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zh_o
 * @since 2020-10-21
 */
@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Resource
    private UserMapper userMapper;

    @Autowired
    private ObjectMapper json;

    public UserServiceImpl() {
        initDegrade();
    }

    /**
     * 根据用户 id 查询详细信息
     *
     * @param uId 用户 id
     */
    @Override
    public UserDTO getUserById(Integer uId) throws Exception {
        if (uId == null) {
            throw new RuntimeException("参数不合法");
        }
        // 查询用户数据
        User user = userMapper.selectById(uId);
        if (user == null) {
            throw new RuntimeException("该用户不存在");
        }
        return BeanUtil.copyProperties(user, UserDTO.class);
    }

    /**
     * 新增用户
     *
     * @param userParam 新增用户信息
     */
    @Override
    public void saveUser(UserParam userParam) throws Exception {
        if (userParam == null) {
            throw new RuntimeException("参数不合法");
        }
        // 新增用户数据
        User user = BeanUtil.copyProperties(userParam, User.class);
        int row = userMapper.insert(user);
        if (row < 1) {
            throw new RuntimeException("新增失败");
        }
    }

    /**
     * 获取用户列表
     *
     * @param pageNum  当前页
     * @param pageSize 每页显示条数
     */
    @Override
    @SentinelResource("users")
    public IPage<User> listUser(Integer pageNum, Integer pageSize) throws Exception {
        if (pageNum == null || pageSize == null) {
            throw new RuntimeException("参数不合法");
        }
        Page<User> userPage = userMapper.selectPage(new Page<>(pageNum, pageSize), null);
        if (userPage == null) {
            throw new RuntimeException("无用户");
        }
        return userPage;
    }

    /**
     * 熔断降级
     */
    private void initDegrade() {
        List<DegradeRule> rules = new ArrayList<>();
        DegradeRule degradeRule = new DegradeRule();
        // 设置降级资源
        degradeRule.setResource("users");
        // 最小请求数
        degradeRule.setMinRequestAmount(1);
        // 慢调用比例
        degradeRule.setCount(0.1);
        // 熔断策略
        degradeRule.setGrade(RuleConstant.DEGRADE_GRADE_RT);
        // 设置熔断时长
        degradeRule.setTimeWindow(5);
        // 慢调用比例
        degradeRule.setSlowRatioThreshold(0.2);
        rules.add(degradeRule);
        // 加载规则
        DegradeRuleManager.loadRules(rules);
    }

    /**
     * 用户登录
     *
     * @param tel 电话
     * @param password 密码
     * @return 令牌
     */
    @Override
    public String login(String tel, String password) {
        // 判断账号是否存在
        User user = userMapper.selectOne(new QueryWrapper<User>().eq(User.TEL, tel));
        if (user == null) {
            throw new RuntimeException("用户不存在");
        }
        // 判断密码是否正确
        if (!user.getPassword().equals(password)) {
            throw new RuntimeException("密码错误");
        }
        // 生成 Token
        HashMap<String, String> map = new HashMap<>();
        map.put(User.TEL, tel);
        map.put(User.ID, user.getId() + "");
        return JwtUtils.getToken(map);
    }

    /**
     * QQ 登录
     *
     * @param code 回调 Code
     * @return 令牌
     */
    @Override
    public String qqLogin(String code) throws JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate();
        log.info("当前 code: {}", code);
        // 获取 AccessToken
        String accessToken = restTemplate.getForObject("https://graph.qq.com/oauth2.0/token?grant_type=authorization_code&client_id=101780702&client_secret=be3cf79b89eafe3f0b8f90462aed8065&code=" + code + "&redirect_uri=http://www.pawntest.com/qqlogin", String.class);
        if (accessToken != null) {
            accessToken = accessToken.substring(accessToken.indexOf("=") + 1, accessToken.indexOf("&"));
        }
        log.info("当前 AccessToken:  {}", accessToken);
        // 获取 OpenId
        String jsonStr = restTemplate.getForObject("https://graph.qq.com/oauth2.0/me?fmt=json&access_token=" + accessToken, String.class);
        Map<String, String> map = json.readValue(jsonStr, Map.class);
        String openId = map.get("openid");
        log.info("当前 OpenId:  {}", openId);
        // 获取用户信息
        String userMsg = restTemplate.getForObject("https://graph.qq.com/user/get_user_info?access_token=" + accessToken + "&oauth_consumer_key=101780702&openid=" + openId, String.class);
        log.info("当前用户信息:  {}", userMsg);
        Map<String, String> userInfo = json.readValue(userMsg, Map.class);
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("nickname", userInfo.get("nickname"));
        String token = JwtUtils.getToken(hashMap);
        return token;
    }
}
