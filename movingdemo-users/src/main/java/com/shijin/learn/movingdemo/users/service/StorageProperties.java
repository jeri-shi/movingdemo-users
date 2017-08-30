package com.shijin.learn.movingdemo.users.service;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("storage")
public class StorageProperties {
  
  private String rootLocation = "uploaded";

  public String getRootLocation() {
    return rootLocation;
  }

  public void setRootLocation(String rootLocation) {
    this.rootLocation = rootLocation;
  }
  
  

}
