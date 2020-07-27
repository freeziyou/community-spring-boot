package com.freeziyou.newcoder;

import com.freeziyou.newcoder.util.MailClientUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

/**
 * @author Dylan Guo
 * @date 7/27/2020 15:40
 * @description TODO
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = NewcoderApplication.class)
public class MailTest {

    @Autowired
    private MailClientUtil mailClientUtil;

    @Autowired
    private TemplateEngine templateEngine;

    @Test
    public void textTextMail() {
        mailClientUtil.sendMail("80776877@qq.com", "Test", "hello");
    }

    @Test
    public void testHtmlMail() {
        Context context = new Context();
        context.setVariable("username", "dd123");

        String content = templateEngine.process("/mail/demo.html", context);
        System.out.println(content);

        mailClientUtil.sendMail("80776877@qq.com", "HTML", content);
    }


}
