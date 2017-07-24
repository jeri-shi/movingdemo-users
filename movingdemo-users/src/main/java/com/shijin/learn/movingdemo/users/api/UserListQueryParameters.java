/**
 * 
 */
package com.shijin.learn.movingdemo.users.api;

import java.io.Serializable;

/**
 * @author shijin
 *
 */
public class UserListQueryParameters implements Serializable{
  private static final long serialVersionUID = 4035332372928691599L;
  private LoginUser userParam;
  private Pagination pageParam;

  public UserListQueryParameters() {

  }

  public UserListQueryParameters(LoginUser user, Pagination page) {
    this.userParam = user;
    this.pageParam = page;
  }

  public LoginUser getUserParam() {
    return userParam;
  }

  public void setUserParam(LoginUser userParam) {
    this.userParam = userParam;
  }

  public Pagination getPageParam() {
    return pageParam;
  }

  public void setPageParam(Pagination pageParam) {
    this.pageParam = pageParam;
  }



}
