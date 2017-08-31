package com.shijin.learn.movingdemo.users.service;

import java.nio.file.Path;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface StorageService {

  Path store(long id, MultipartFile file, String filename);
  Resource get(long id);
  
}
