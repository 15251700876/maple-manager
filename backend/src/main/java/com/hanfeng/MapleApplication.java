package com.hanfeng;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Hello world!
 *
 * @author HanFeng
 */
@SpringBootApplication
@MapperScan(basePackages="com.hanfeng.**.dao")
public class MapleApplication {
    public static void main(String[] args) {
        SpringApplication.run(MapleApplication.class, args);
    }
}
