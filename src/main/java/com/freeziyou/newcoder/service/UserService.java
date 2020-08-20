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

    public User findUserById(int userId) {
        return userMapper.selectById(userId);
    }

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
        user.setHeaderUrl(String.format("http://images.nowcoder.com/head/%dt.png", new Random().nextInt(1000)));
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

    public LoginTicket findLoginTicket(String ticket) {
        return loginTicketMapper.selectByTicket(ticket);
    }

    public int updateHeader(int userId, String headUrl) {
        return userMapper.updateHeader(userId, headUrl);
    }

    /**
     * 重置密码
     *
     * @param userId      用户id
     * @param oldPassword 旧密码
     * @param newPassword 新密码
     * @return 修改结果, map 里存放错误信息
     */
    public Map<String, Object> updateNewPassword(int userId, String oldPassword, String newPassword) {
        Map<String, Object> map = new HashMap<>();
        User user = userMapper.selectById(userId);
        oldPassword = CommunityUtil.md5(oldPassword + user.getSalt());
        newPassword = CommunityUtil.md5(newPassword + user.getSalt());

        // 检查原密码
        if (!oldPassword.equals(user.getPassword())) {
            map.put("passwordMsg", "原密码输入错误!");
            return map;
        } else if (newPassword.equals(user.getPassword())) {
            map.put("passwordMsg", "新密码与原密码相同!");
            return map;
        }
        // 更新密码
        userMapper.updatePassword(userId, newPassword);
        return map;
    }

    /**
     * 发送找回密码的验证码
     *
     * @param email 用户邮箱
     * @return 验证码
     */
    public String sendForgetCaptcha(String email) {
        // 获取用户
        User user = userMapper.selectByEmail(email);
        // 生成验证码
        String captcha = user.getActivationCode().substring(5, 10);
        // 发送邮件
        Context context = new Context();
        context.setVariable("email", user.getEmail());
        context.setVariable("captcha", captcha);
        String content = templateEngine.process("/mail/forget", context);
        mailClientUtil.sendMail(user.getEmail(), "重置账号密码------" + user.getUsername(), content);

        return captcha;
    }

    public User findUserByEmail(String email) {
        return userMapper.selectByEmail(email);
    }

    public Map<String, Object> resetPassword(String email, String password) {
        Map<String, Object> map = new HashMap<>();
        // 获取当前操作用户
        User user = userMapper.selectByEmail(email);
        // 重置密码
        password = CommunityUtil.encryptPassword(password, user.getSalt());
        if (user.getPassword().equals(password)) {
            map.put("passwordMsg", "新密码与原密码相同, 请重新输入!");
            return map;
        }
        userMapper.updatePassword(user.getId(), password);

        return map;
    }

    public User findUserByName(String username) {
        return userMapper.selectByName(username);
    }
}
