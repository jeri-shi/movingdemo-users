/**
 * 
 */
package com.shijin.learn.movingdemo.users.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import com.shijin.learn.movingdemo.users.api.LoginUser;

/**
 * @author shijin
 *
 */

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class UsersControllerIntergrationTest {

  @Autowired
  private TestRestTemplate restTemplate;

  @Test
  public void getUserTest() {
    LoginUser user = restTemplate.getForObject("/user/1", LoginUser.class);
    assertNotNull("user should not be null", user);
    assertEquals("Learn", user.getCompany());
    assertEquals("Jin", user.getUsername());
  }

  @Test
  public void updateUserTest() {
    LoginUser user = restTemplate.getForObject("/user/1", LoginUser.class);
    user.setEnabled(false);
    restTemplate.put("/user/0", user, user.getId());

    LoginUser updatedUser = restTemplate.getForObject("/user/1", LoginUser.class);
    assertEquals(user.getCompany(), updatedUser.getCompany());
    assertEquals(user.isEnabled(), updatedUser.isEnabled());
  }

  @Test
  public void addUserTest() {
    LoginUser user = new LoginUser();
    user.setCompany("LearnHard");
    user.setUsername("Hard");
    user.setPassword("222");

    LoginUser newUser = restTemplate.postForObject("/user", user, LoginUser.class);

    assertNotNull(newUser);
    assertEquals(user.getCompany(), newUser.getCompany());
    assertEquals(user.isEnabled(), newUser.isEnabled());
    assertEquals(2, newUser.getId());
  }

  @Test
  public void deleteUserTest() {
    LoginUser user = new LoginUser();
    user.setCompany("LearnWork");
    user.setUsername("Work");
    user.setPassword("222333");

    LoginUser newUser = restTemplate.postForObject("/user", user, LoginUser.class);

    assertNotNull(newUser);
    
    restTemplate.delete("/user/{1}", newUser.getId());
    
    user = restTemplate.getForObject("/user/{1}", LoginUser.class, newUser.getId());
    assertNull(user);
  }
}
