package com.freeziyou.newcoder.util;

import com.freeziyou.newcoder.entity.User;
import org.springframework.stereotype.Component;

/**
 * @author Dylan Guo
 * @date 7/29/2020 11:08
 * @description 持有用户信息, 用于代替 session 对象, 保证线程安全
 */
@Component
public class HostHolder {

    private ThreadLocal<User> users = new ThreadLocal<>();

    public void setUser(User user) {
        users.set(user);
    }

    public User getUser() {
        return users.get();
    }

    public void clear() {
        users.remove();
    }
}
