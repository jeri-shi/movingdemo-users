package com.shijin.learn.movingdemo.users;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

import com.shijin.learn.movingdemo.users.service.StorageProperties;

/**
 * Hello world!
 *
 */
@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
@EnableResourceServer  //need to comment to run all unit test, uncomment for production
@EnableConfigurationProperties(StorageProperties.class)
public class UserApplication {

  public static void main(String[] args) {
    SpringApplication.run(UserApplication.class, args);
  }
}
