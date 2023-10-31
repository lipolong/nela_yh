package com.example.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSenderImpl;

/**
 * @author Lenovo
 * 在您的 Application 类或带@Configuration注释的类中，添加@EnableEventeum注释。
 */
@SpringBootApplication
//@ServletComponentScan(basePackages="com.example.test.listen.*")
public class YiHuaTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(YiHuaTestApplication.class, args);
        System.out.println("O(∩_∩)O 测试程序启动成功！！！ (ง •_•)ง");
    }

    @Bean
    public JavaMailSenderImpl javaMailSenderImpl() {
        return new JavaMailSenderImpl();
    }


}
