/**
 * 
 */
package com.shijin.learn.movingdemo.users.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.shijin.learn.movingdemo.users.api.LoginUser;
import com.shijin.learn.movingdemo.users.api.Pagination;
import com.shijin.learn.movingdemo.users.api.UserListQueryParameters;

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
    LoginUser user =
        restTemplate.withBasicAuth("user", "password").getForObject("/user/1", LoginUser.class);
    assertNotNull("user should not be null", user);
    assertEquals("Learn", user.getCompany());
    assertEquals("Jin", user.getUsername());
  }

  @Test
  public void updateUserTest() {
    LoginUser user =
        restTemplate.withBasicAuth("user", "password").getForObject("/user/1", LoginUser.class);
    user.setEnabled(false);
    restTemplate.withBasicAuth("user", "password").put("/user/1", user, user.getId());

    LoginUser updatedUser =
        restTemplate.withBasicAuth("user", "password").getForObject("/user/1", LoginUser.class);
    assertEquals(user.getCompany(), updatedUser.getCompany());
    assertEquals(user.getEnabled(), updatedUser.getEnabled());
  }

  @Test
  public void addUserTest() {
    LoginUser user = new LoginUser();
    user.setCompany("LearnHard");
    user.setUsername("Hard");
    user.setPassword("222");

    LoginUser newUser = restTemplate.withBasicAuth("user", "password").postForObject("/user", user,
        LoginUser.class);

    assertNotNull(newUser);
    assertEquals(user.getCompany(), newUser.getCompany());
    assertEquals(user.getEnabled(), newUser.getEnabled());
    assertEquals(2, newUser.getId());
    
    restTemplate.withBasicAuth("user", "password").delete("/user/{1}", newUser.getId());
  }

  @Test
  public void deleteUserTest() {
    LoginUser user = new LoginUser();
    user.setCompany("LearnWork");
    user.setUsername("Work");
    user.setPassword("222333");

    LoginUser newUser = restTemplate.withBasicAuth("user", "password").postForObject("/user", user,
        LoginUser.class);

    assertNotNull(newUser);

    restTemplate.withBasicAuth("user", "password").delete("/user/{1}", newUser.getId());

    user = restTemplate.withBasicAuth("user", "password").getForObject("/user/{1}", LoginUser.class,
        newUser.getId());
    assertNull(user);
  }

  @Test
  public void getUsersListWithoutParamTest() {

    ResponseEntity<Collection<LoginUser>> response =
        restTemplate.withBasicAuth("user", "password").exchange("/userslist", HttpMethod.POST, null,
            new ParameterizedTypeReference<Collection<LoginUser>>() {});
    Collection<LoginUser> collection = response.getBody();

    assertNotNull(collection);
    assertEquals(2, collection.size());
  }

  @Test
  public void getUsersListWithUserNameTest() {
    
    LoginUser userParam = new LoginUser();
    userParam.setUsername("Jin");
    Pagination pageParam = null;
    
    UserListQueryParameters queryParameters = new UserListQueryParameters();
    queryParameters.setUserParam(userParam);
    queryParameters.setPageParam(pageParam);
    
    HttpEntity<UserListQueryParameters> request = new HttpEntity<>(queryParameters);

    ResponseEntity<Collection<LoginUser>> response =
        restTemplate.withBasicAuth("user", "password").exchange("/userslist", HttpMethod.POST,
            request, new ParameterizedTypeReference<Collection<LoginUser>>() {});

    Collection<LoginUser> collection = response.getBody();

    assertNotNull(collection);
    assertEquals(1, collection.size());
    assertEquals("Jin", collection.iterator().next().getUsername());
  }
  
  @Test
  public void getUserListWithUserandPageTest() {
    LoginUser userParam = new LoginUser();
    userParam.setCompany("Learn");
    Pagination pageParam = new Pagination(2, 1);
    
    UserListQueryParameters queryParameters = new UserListQueryParameters(userParam, pageParam);
    HttpEntity<UserListQueryParameters> request = new HttpEntity<>(queryParameters);
    
    ResponseEntity<Collection<LoginUser>> response =
        restTemplate.withBasicAuth("user", "password").exchange("/userslist", HttpMethod.POST,
            request, new ParameterizedTypeReference<Collection<LoginUser>>() {});
 
    Collection<LoginUser> collection = response.getBody();

    assertNotNull(collection);
    assertEquals(1, collection.size());
    assertEquals("Wendy", collection.iterator().next().getUsername());
  }
  
  @Test
  public void getUsersListCountTest() {
    UserListQueryParameters queryParameters = new UserListQueryParameters(null, null);
    HttpEntity<UserListQueryParameters> request = new HttpEntity<>(queryParameters);
    
    ResponseEntity<Long> response =
        restTemplate.withBasicAuth("user", "password").exchange("/userslistcount", HttpMethod.POST,
            request, new ParameterizedTypeReference<Long>() {});
    
    Long count = response.getBody();
    
    assertEquals(2l, count.longValue());
  }
}
