/**
 * 
 */
package com.shijin.learn.movingdemo.users.controller;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.shijin.learn.movingdemo.users.api.LoginUser;
import com.shijin.learn.movingdemo.users.api.UsersOpenApi;
import com.shijin.learn.movingdemo.users.mapper.UserMapper;

/**
 * @author shijin
 *
 */
@RestController
public class UsersController implements UsersOpenApi {
  private static final Logger LOGGER = LogManager.getLogger(UsersController.class);

  @Autowired
  private UserMapper userMapper;
  
  @Override
  @RequestMapping(value = "/user", method = RequestMethod.POST)
  public LoginUser addUser(@RequestBody LoginUser user) {
    LOGGER.debug("add a user:{}", user);
    
    userMapper.addUser(user);
    return user;
  }

  @Override
  @RequestMapping(value = "/user/{id}", method = RequestMethod.DELETE)
  public int deleteUser(@PathVariable int id) {
    LOGGER.debug("delete a user:{}", id);
    
    int count = userMapper.deleteUser(id);
    return count;
  }

  @Override
  @RequestMapping(value = "/user/{id}", method = RequestMethod.PUT)
  public LoginUser updateUser(@PathVariable int id, @RequestBody LoginUser user) {
    LOGGER.debug("update a user:{}", user);
    int count = userMapper.updateUser(user);
    if (count == 1) {
      return user;
    } else {
      return null;
    }
  }

  @Override
  @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
  public LoginUser getUser(@PathVariable int id) {
    LOGGER.debug("get a user:{}", id);
    return userMapper.getUser(id);
  }

}
