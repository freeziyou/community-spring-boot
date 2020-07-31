package com.freeziyou.newcoder;

import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.annotation.MapperScans;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author dylanguo
 */
@SpringBootApplication
@MapperScan("com.freeziyou.newcoder.dao")
public class NewcoderApplication {

    public static void main(String[] args) {
        SpringApplication.run(NewcoderApplication.class, args);
    }

}
