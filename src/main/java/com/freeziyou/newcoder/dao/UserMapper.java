package com.freeziyou.newcoder.dao;

import com.freeziyou.newcoder.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author Dylan Guo
 * @date 7/24/2020 10:51
 * @description TODO
 */
@Mapper
public interface UserMapper {
    User selectById(Integer id);

    User selectByName(String username);

    User selectByEmail(String email);

    Integer insertUser(User user);

    Integer updateStatus(Integer id, Integer status);

    Integer updateHeader(Integer id, String headerUrl);

    Integer updatePassword(Integer id, String password);
}
