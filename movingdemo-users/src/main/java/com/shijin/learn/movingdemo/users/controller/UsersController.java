/**
 * 
 */
package com.shijin.learn.movingdemo.users.controller;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.shijin.learn.movingdemo.users.api.LoginUser;
import com.shijin.learn.movingdemo.users.api.UsersService;

/**
 * @author shijin
 *
 */
@RestController
public class UsersController implements UsersService {
  private static final Logger LOGGER = LogManager.getLogger(UsersController.class);

  @Override
  @RequestMapping(value = "/user", method = RequestMethod.POST)
  public LoginUser addUser(@RequestBody LoginUser user) {
    LOGGER.debug("add a user:{}", user);
    return null;
  }

  @Override
  @RequestMapping(value = "/user/{id}", method = RequestMethod.DELETE)
  public void deleteUser(@PathVariable Integer id) {
    LOGGER.debug("delete a user:{}", id);
  }

  @Override
  @RequestMapping(value = "/user/{id}")
  public LoginUser updateUser(@PathVariable Integer id, @RequestParam LoginUser user) {
    LOGGER.debug("update a user:{}", user);
    return null;
  }

  @Override
  @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
  public LoginUser getUser(@PathVariable Integer id) {
    LOGGER.debug("get a user:{}", id);
    return null;
  }


}
