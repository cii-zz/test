package com.userlabel.api;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 用户画像标签系统主启动类
 */
@SpringBootApplication(scanBasePackages = "com.userlabel")
@MapperScan("com.userlabel.**.mapper")
public class LabelApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(LabelApiApplication.class, args);
        System.out.println("========================================");
        System.out.println("用户画像标签系统启动成功！");
        System.out.println("访问地址：http://localhost:8080");
        System.out.println("========================================");
    }
}
