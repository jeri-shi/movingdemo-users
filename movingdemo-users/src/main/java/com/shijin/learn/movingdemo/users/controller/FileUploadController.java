package com.shijin.learn.movingdemo.users.controller;

import java.nio.file.Path;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.shijin.learn.movingdemo.users.service.StorageService;

@RestController
public class FileUploadController {
  private static final Logger LOGGER = LogManager.getLogger(FileUploadController.class);
  @Autowired
  private StorageService storageService;
  
  @PostMapping("/user/{id}/photo")
  public String handleFileUpload(@PathVariable("id") long id, @RequestPart("file") MultipartFile file,
      @RequestParam(value="filename", required=false) String filename) {
    LOGGER.debug("/user/id/upload... id = {}, multiFile = {}, filename={}", id,  file, filename);
    
    Path imagePath = storageService.store(id, file, filename);
    return imagePath.toString();
  }
  
  @GetMapping("/user/{id}/photo")
  public Resource getPhotoAsResource(@PathVariable("id") long id) {
    LOGGER.debug("/user/id/photo... id = {}", id);
    return storageService.get(id);
  }
  
}
