package com.demo.newsapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * Demo application to fetch the news.
 *
 * @Author: Shubham Arya
 * @Version: 0.1
 */
@SpringBootApplication
@EnableFeignClients
public class NewsApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(NewsApiApplication.class, args);
    }

}
