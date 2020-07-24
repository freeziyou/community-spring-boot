package com.freeziyou.newcoder.service;

import com.freeziyou.newcoder.dao.UserMapper;
import com.freeziyou.newcoder.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Dylan Guo
 * @date 7/24/2020 11:23
 * @description TODO
 */
@Service
public class UserService implements UserMapper{
    @Autowired
    UserMapper userMapper;

    @Override
    public User selectById(Integer id) {
        return userMapper.selectById(id);
    }

    @Override
    public User selectByName(String username) {
        return null;
    }

    @Override
    public User selectByEmail(String email) {
        return null;
    }

    @Override
    public Integer insertUser(User user) {
        return userMapper.insertUser(user);
    }

    @Override
    public Integer updateStatus(Integer id, Integer status) {
        return userMapper.updateStatus(id, status);
    }

    @Override
    public Integer updateHeader(Integer id, String headerUrl) {

        return userMapper.updateHeader(id, headerUrl);
    }

    @Override
    public Integer updatePassword(Integer id, String password) {
        return userMapper.updatePassword(id, password);
    }
}
