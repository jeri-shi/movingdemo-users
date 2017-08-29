package com.shijin.learn.movingdemo.users.api;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class PaginationTest {

  @Test
  public void buildTest() {
    long current = 1;
    int countPerPage = 10;
    Pagination page = new Pagination(current, countPerPage);
    
    assertEquals(0, page.getOffset());
    assertEquals(10, page.getCountPerPage());
    assertEquals(" Limit 0, 10", page.getMySql());
  }
  
  @Test
  public void anotherTest() {
    long current = 5;
    int countPerPage = 45;
    Pagination page = new Pagination(current, countPerPage);
    
    assertEquals(180 , page.getOffset());
    assertEquals(countPerPage, page.getCountPerPage());
    assertEquals(" Limit 180, 45", page.getMySql());   
  }
  
  @Test
  public void nativeOneTest() {
    Pagination page = new Pagination(2, 1);
    
    assertEquals(1, page.getOffset());
    assertEquals(1, page.getCountPerPage());
    assertEquals(" Limit 1, 1", page.getMySql());
  }
}
