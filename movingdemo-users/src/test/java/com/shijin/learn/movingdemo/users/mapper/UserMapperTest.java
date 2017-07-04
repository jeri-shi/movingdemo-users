package com.shijin.learn.movingdemo.users.mapper;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.shijin.learn.movingdemo.users.api.LoginUser;

@RunWith(SpringRunner.class)
@SpringBootTest
@MybatisTest
public class UserMapperTest {
  
  @Autowired
  private UserMapper userMapper;
  
  @Test
  public void getUserTest() {
    LoginUser user = userMapper.getUser(1);
    
    assertEquals("Jin", user.getUsername());
  }
}
