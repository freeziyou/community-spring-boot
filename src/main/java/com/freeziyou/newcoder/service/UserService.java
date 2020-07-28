package com.freeziyou.newcoder.service;

import com.freeziyou.newcoder.dao.LoginTicketMapper;
import com.freeziyou.newcoder.dao.UserMapper;
import com.freeziyou.newcoder.entity.LoginTicket;
import com.freeziyou.newcoder.entity.User;
import com.freeziyou.newcoder.util.CommunityConstant;
import com.freeziyou.newcoder.util.CommunityUtil;
import com.freeziyou.newcoder.util.MailClientUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * @author Dylan Guo
 * @date 7/24/2020 11:23
 * @description TODO
 */
@Service
public class UserService implements CommunityConstant {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private MailClientUtil mailClientUtil;

    @Autowired
    private TemplateEngine templateEngine;

    @Autowired
    private LoginTicketMapper loginTicketMapper;

    @Value("${newcoder.path.domain}")
    private String domain;

    @Value("${server.servlet.context-path}")
    private String contextPath;

    public Map<String, Object> register(User user) {
        Map<String, Object> map = new HashMap<>();

        // 检查是否空值
        if (user == null) {
            throw new IllegalArgumentException("参数不能为空");
        }
        if (StringUtils.isBlank(user.getUsername())) {
            map.put("usernameMsg", "用户名不能为空!");
            return map;
        }
        if (StringUtils.isBlank(user.getPassword())) {
            map.put("passwordMsg", "密码不能为空!");
            return map;
        }
        if (StringUtils.isBlank(user.getEmail())) {
            map.put("emailMsg", "邮箱不能为空!");
            return map;
        }

        // 验证账户名
        User checkUser = userMapper.selectByName(user.getUsername());
        if (checkUser != null) {
            map.put("usernameMsg", "此用户名已被使用!");
            return map;
        }

        // 验证邮箱
        checkUser = userMapper.selectByEmail(user.getEmail());
        System.out.println(checkUser);
        if (checkUser != null) {
            map.put("emailMsg", "此邮箱已被注册!");
            return map;
        }

        // 注册用户
        user.setSalt(CommunityUtil.generateUUID().substring(5, 10));
        user.setPassword(CommunityUtil.md5(user.getPassword() + user.getSalt()));
        user.setType(0);
        user.setStatus(0);
        user.setActivationCode(CommunityUtil.generateUUID());
        user.setHeaderUrl(String.format("http://images.newcoder.com/head/%dt.png", new Random().nextInt(1000)));
        user.setCreateTime(new Date());

        userMapper.insertUser(user);

        // 发送激活邮件,
        Context context = new Context();
        context.setVariable("email", user.getEmail());
        // http://localhost:8080/community/activation/101/code
        String url = domain + contextPath + "/activation/" + user.getId() + "/" + user.getActivationCode();
        context.setVariable("url", url);
        String content = templateEngine.process("/mail/activation", context);
        mailClientUtil.sendMail(user.getEmail(), "激活账号", content);

        return map;
    }

    public int activation(Integer userId, String code) {
        User user = userMapper.selectById(userId);
        if (user.getStatus() == 1) {
            return ACTIVATION_REPEAT;
        } else if (user.getActivationCode().equals(code)) {
            userMapper.updateStatus(userId, 1);
            return ACTIVATION_SUCCESS;
        } else {
            return ACTIVATION_FAILURE;
        }
    }

    public Map<String, Object> login(String username, String password, int expiredSeconds) {
        Map<String, Object> map = new HashMap<>();

        // 空值处理
        if (StringUtils.isBlank(username)) {
            map.put("usernameMsg", "账号不能为空!");
            return map;
        }

        if (StringUtils.isBlank(password)) {
            map.put("passwordMsg", "密码不能为空!");
            return map;
        }

        // 验证账号
        User user = userMapper.selectByName(username);
        if (user == null) {
            map.put("usernameMsg", "账号不存在!");
            return map;
        }

        // 验证状态
        if (user.getStatus() == 0) {
            map.put("usernameMsg", "账号未激活!");
            return map;
        }

        // 验证密码
        String encryptPassword = CommunityUtil.md5(password + user.getSalt());
        if (!user.getPassword().equals(encryptPassword)) {
            map.put("passwordMsg", "密码错误!");
            return map;
        }

        // 生成登陆凭证
        LoginTicket loginTicket = new LoginTicket();
        loginTicket.setUserId(user.getId());
        loginTicket.setTicket(CommunityUtil.generateUUID());
        loginTicket.setStatus(0);
        loginTicket.setExpired(new Date(System.currentTimeMillis() + expiredSeconds * 1000 * 12));

        loginTicketMapper.insertLoginTicket(loginTicket);

        map.put("ticket", loginTicket.getTicket());
        return map;
    }

    public void logout(String ticket) {
        loginTicketMapper.updateStatus(ticket, 1);
    }

    public User selectById(int userId) {
        return userMapper.selectById(userId);
    }
}
