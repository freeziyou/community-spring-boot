package com.freeziyou.newcoder;

import com.freeziyou.newcoder.dao.LoginTicketMapper;
import com.freeziyou.newcoder.dao.MessageMapper;
import com.freeziyou.newcoder.dao.UserMapper;
import com.freeziyou.newcoder.entity.DiscussPost;
import com.freeziyou.newcoder.entity.LoginTicket;
import com.freeziyou.newcoder.entity.Message;
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
    UserMapper userMapper;

    @Autowired
    DiscussPostService discussPostService;

    @Autowired
    LoginTicketMapper loginTicketMapper;

    @Autowired
    MessageMapper messageMapper;

    @Test
    public void test1() {
        User user = userMapper.selectById(150);
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

        int rows = userMapper.insertUser(user);
        System.out.println(rows);
        System.out.println(user.getId());
    }

    @Test
    public void updateUser() {
        int rows = userMapper.updateStatus(150, 1);
        System.out.println(rows);

        rows = userMapper.updateHeader(150, "http://www.nowcoder.com/102.png");
        System.out.println(rows);

        rows = userMapper.updatePassword(150, "hello");
        System.out.println(rows);
    }

    @Test
    public void testSelectPosts() {
        List<DiscussPost> list = discussPostService.selectDiscussPosts(149, 0, 10);
        for (DiscussPost post : list) {
            System.out.println(post);
        }
    }

    @Test
    public void testInsertLoginTicket() {
        LoginTicket loginTicket = new LoginTicket();
        loginTicket.setUserId(1000);
        loginTicket.setTicket("qqq");
        loginTicket.setStatus(0);
        loginTicket.setExpired(new Date(System.currentTimeMillis() + 1000 * 60 * 10));

        loginTicketMapper.insertLoginTicket(loginTicket);
    }

    @Test
    public void testUpdateLoginTicket() {
        LoginTicket loginTicket = loginTicketMapper.selectByTicket("qqq");
        System.out.println(loginTicket);
        loginTicketMapper.updateStatus("qqq", 1);
        loginTicket = loginTicketMapper.selectByTicket("qqq");
        System.out.println(loginTicket);
    }

    @Test
    public void testSelectLetter() {
        List<Message> list = messageMapper.selectConversations(111, 0, 20);
        for (Message message : list) {
            System.out.println(message);
        }

        int count = messageMapper.selectConversationCount(111);
        System.out.println(count);

        List<Message> messageLetter = messageMapper.selectLetters("111_112", 0, 10);
        for (Message message : messageLetter) {
            System.out.println(message);
        }

        count = messageMapper.selectLetterCount("111_112");
        System.out.println(count);

        count = messageMapper.selectLetterUnreadCount(131, "111_131");
        System.out.println(count);
    }
}
