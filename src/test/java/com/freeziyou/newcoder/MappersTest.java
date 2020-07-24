package com.freeziyou.newcoder;

import com.freeziyou.newcoder.entity.DiscussPost;
import com.freeziyou.newcoder.entity.User;
import com.freeziyou.newcoder.service.DiscussPostService;
import com.freeziyou.newcoder.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

/**
 * @author Dylan Guo
 * @date 7/24/2020 11:47
 * @description TODO
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = NewcoderApplication.class)
public class MappersTest {

    @Autowired
    UserService userService;

    @Autowired
    DiscussPostService discussPostService;

    @Test
    public void test1() {
        User user = userService.selectById(150);
        System.out.println(user);
    }

    @Test
    public void testInsertUser() {
        User user = new User();
        user.setUsername("test");
        user.setPassword("123456");
        user.setSalt("abc");
        user.setEmail("test@qq.com");
        user.setHeaderUrl("http://www.nowcoder.com/101.png");
        user.setCreateTime(new Date());

        int rows = userService.insertUser(user);
        System.out.println(rows);
        System.out.println(user.getId());
    }

    @Test
    public void updateUser() {
        int rows = userService.updateStatus(150, 1);
        System.out.println(rows);

        rows = userService.updateHeader(150, "http://www.nowcoder.com/102.png");
        System.out.println(rows);

        rows = userService.updatePassword(150, "hello");
        System.out.println(rows);
    }

    @Test
    public void testSelectPosts() {
        List<DiscussPost> list = discussPostService.selectDiscussPosts(149, 0, 10);
        for(DiscussPost post : list) {
            System.out.println(post);
        }

//        int rows = discussPostService.selectDiscussPostsRows(149);
//        System.out.println(rows);
    }
}
