package com.shijin.learn.movingdemo.users.mapper;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.shijin.learn.movingdemo.users.api.LoginUser;
import com.shijin.learn.movingdemo.users.api.Pagination;

@RunWith(SpringRunner.class)
@SpringBootTest
@MybatisTest
public class UserMapperTest {
  
  @Autowired
  private UserMapper userMapper;
  
  @Test
  public void getUserTest() {
    LoginUser user = userMapper.getUser(0);
    assertEquals("Wendy", user.getUsername());
    
    user = userMapper.getUser(1000);
    assertNull(user);
  }
  
  @Test
  public void addUserTest() {
    LoginUser user = new LoginUser();
    user.setCompany("Learn");
    user.setUsername("test1");
    user.setPassword("111");
    
    userMapper.addUser(user);
    LoginUser newUser = userMapper.getUser(user.getId());
    
    assertEquals(user.getCompany(), newUser.getCompany());
    assertEquals(user.getUsername(), newUser.getUsername());
    assertEquals(user.getPassword(), newUser.getPassword());
  }
  
  @Test
  public void updateUserTest() {
    
    LoginUser user = userMapper.getUser(0);
    user.setCompany("LearnHard");
    user.setEnabled(false);
    
    int count = userMapper.updateUser(user);
    
    assertEquals(1, count);
    LoginUser updatedUser = userMapper.getUser(user.getId());
    
    assertEquals(user.getCompany(), updatedUser.getCompany());
    assertEquals(user.getUsername(), updatedUser.getUsername());
    assertEquals(user.getPassword(), updatedUser.getPassword());
    assertEquals(user.getEnabled(), updatedUser.getEnabled());
        
  }
  
  @Test
  public void deleteUserTest() {
    LoginUser user = new LoginUser();
    user.setCompany("LearnWork");
    user.setUsername("testWork");
    user.setPassword("111Work");
    userMapper.addUser(user);
    
    int count = userMapper.deleteUser(user.getId());
    assertEquals(1, count);
    
    count = userMapper.deleteUser(user.getId());
    assertEquals(0, count);
  }
  
  @Test
  public void getUsersTest() {
    
    Collection<LoginUser> collection = userMapper.getUsers("Learn");
    assertNotNull(collection);
    assertEquals(2, collection.size());
    
    LoginUser user = collection.iterator().next();
    assertEquals("Jin", user.getUsername());
  }
  
  @Test
  public void getUsersListTest() {
    LoginUser param = new LoginUser();
    param.setCompany("Learn");
    
    Collection<LoginUser> collection = userMapper.getUsersList(param, null);
    
    assertNotNull(collection);
    assertEquals(2, collection.size());
    
    
    param = new LoginUser();
    param.setCompany("AAA");
    
    collection = userMapper.getUsersList(param, null);
    
    assertNotNull(collection);
    assertEquals(0, collection.size());
   
  }
  
  @Test
  public void getUsersListByUsernameTest() {
    LoginUser param = new LoginUser();
    param.setCompany("Learn");
    param.setUsername("in");
    
    Collection<LoginUser> collection = userMapper.getUsersList(param, null);
    
    assertNotNull(collection);
    assertEquals(1, collection.size());

    LoginUser user = collection.iterator().next();
    assertEquals("Jin", user.getUsername());
  }
  
  @Test 
  public void getEnableDisableUsersListTest() {
    LoginUser param = new LoginUser();
    param.setEnabled(false);
    Collection<LoginUser> collection = userMapper.getUsersList(param, null);
    assertNotNull(collection);
    assertEquals(0, collection.size());

    param.setEnabled(true);
    collection = userMapper.getUsersList(param, null);
    assertNotNull(collection);
    assertEquals(2, collection.size());
  }
  
  @Test
  public void getUsersListWithPagination() {
    long current = 2;
    int countPerPage = 1;
    Pagination page = new Pagination(current, countPerPage);
    Collection<LoginUser> collection = userMapper.getUsersList(null, page);
    assertNotNull(collection);
    assertEquals(1, collection.size());
  }
  
  @Test
  public void getUsersListCount() {
    long count = userMapper.getUsersListCount(null, null);
    assertEquals(2, count);
    
    LoginUser user = new LoginUser();
    user.setCompany("Learn");
    
    count = userMapper.getUsersListCount(user, null);
    assertEquals(2, count);
  }
}
