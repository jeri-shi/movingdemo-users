package com.shijin.learn.movingdemo.users.api;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

public interface UsersService {
  
  @RequestMapping(value = "/user", method = RequestMethod.POST)
  public LoginUser addUser(@RequestBody LoginUser user);

  @RequestMapping(value = "/user/{id}", method = RequestMethod.DELETE)
  public void deleteUser(@RequestParam Integer id);
  
  @RequestMapping(value = "/user/{id}")
  public LoginUser updateUser(@RequestParam Integer id, @RequestParam LoginUser user);

  @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
  public LoginUser getUser(@RequestParam Integer id);

}
