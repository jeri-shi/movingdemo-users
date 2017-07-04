/**
 * 
 */
package com.shijin.learn.movingdemo.users.controller;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.shijin.learn.movingdemo.users.api.LoginUser;
import com.shijin.learn.movingdemo.users.mapper.UserMapper;

/**
 * @author shijin
 *
 */
@RunWith(SpringRunner.class)
@WebMvcTest(UsersController.class)
public class UsersControllerTest {

  @Autowired
  private MockMvc mvc;

  @MockBean
  private UserMapper userMapper;

  @Test
  public void getUser() throws Exception {
    LoginUser ret = new LoginUser();
    ret.setCompany("Testing");
    ret.setUsername("Test2");
    ret.setPassword("111");
    ret.setEnabled(true);
    ret.setId(10);

    given(userMapper.getUser(3)).willReturn(ret);

    mvc.perform(get("/user/3")).andExpect(status().isOk())
        .andExpect(content().string("{\"id\":10,\"company\":\"Testing\",\"username\":\"Test2\",\"password\":\"111\",\"enabled\":true}"));

  }

}
