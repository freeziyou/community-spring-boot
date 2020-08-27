package com.freeziyou.newcoder;

import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.annotation.MapperScans;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

/**
 * @author dylanguo
 */
@SpringBootApplication
@MapperScan("com.freeziyou.newcoder.dao")
public class NewcoderApplication {

    @PostConstruct
    public void init() {
        // 解决 netty 启动冲突问题
        // see Netty4Utils.setAvailableProcessors()
        System.setProperty("es.set.netty.runtime.available.processors", "false");
    }

    public static void main(String[] args) {
        SpringApplication.run(NewcoderApplication.class, args);
    }

}
