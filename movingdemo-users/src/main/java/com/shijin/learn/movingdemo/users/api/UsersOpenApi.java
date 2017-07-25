package com.shijin.learn.movingdemo.users.api;

import java.util.Collection;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

public interface UsersOpenApi {
  
  @RequestMapping(value = "/user", method = RequestMethod.POST)
  public LoginUser addUser(@RequestBody LoginUser user);

  @RequestMapping(value = "/user/{id}", method = RequestMethod.DELETE)
  public int deleteUser(@RequestParam int id);
  
  @RequestMapping(value = "/user/{id}", method = RequestMethod.PUT)
  public LoginUser updateUser(@RequestParam int id, @RequestParam LoginUser user);

  @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
  public LoginUser getUser(@RequestParam int id) throws Exception;

  @RequestMapping(value = "/userslist", method = RequestMethod.POST)
  public Collection<LoginUser> getUsersList(@RequestBody(required=false) UserListQueryParameters param);
  
}
