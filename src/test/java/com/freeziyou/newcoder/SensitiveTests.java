package com.freeziyou.newcoder;

import com.freeziyou.newcoder.util.SensitiveFilter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author Dylan Guo
 * @date 7/31/2020 15:11
 * @description TODO
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = NewcoderApplication.class)
public class SensitiveTests {

    @Autowired
    private SensitiveFilter sensitiveFilter;

    @Test
    public void testSensitiveFilter() {
        String text = "可以黄赌毒, 你是傻逼";
        String text2 = "可以❤黄❤赌❤毒, 你是傻逼";
        text = sensitiveFilter.filter(text2);
        System.out.println(text);
    }
}
