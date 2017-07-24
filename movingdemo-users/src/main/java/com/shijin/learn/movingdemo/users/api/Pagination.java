package com.shijin.learn.movingdemo.users.api;

import java.io.Serializable;

public class Pagination  implements Serializable{
  private static final long serialVersionUID = -5144960643511189599L;
  private long current;
  private int countPerPage;

  public Pagination() {
    
  }
  
  
  public long getCurrent() {
    return current;
  }


  public void setCurrent(long current) {
    this.current = current;
  }


  public void setCountPerPage(int countPerPage) {
    this.countPerPage = countPerPage;
  }


  public Pagination(long current, int countPerPage) {
    this.current = current;
    this.countPerPage = countPerPage;
  }

  public long getOffset() {
    return countPerPage * (current - 1);
  }
  
  public int getCountPerPage() {
    return countPerPage;
  }
  
  public String getMySql() {
    return " Limit " + getOffset() + ", " + countPerPage;
  }
}
