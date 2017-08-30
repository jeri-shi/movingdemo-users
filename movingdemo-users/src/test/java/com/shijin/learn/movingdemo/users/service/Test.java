package com.shijin.learn.movingdemo.users.service;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.tika.config.TikaConfig;
import org.apache.tika.exception.TikaException;
import org.apache.tika.io.TikaInputStream;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.mime.MediaType;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
public class Test {

  @org.junit.Test
  public void getFileTypeTest() {
    try {
      Path path = Paths.get("C:", "temp", "images", "images.jpg");
      File file = new File(path.toString());
      TikaConfig tika = new TikaConfig();
      Metadata metadata = new Metadata();
      metadata.set(Metadata.RESOURCE_NAME_KEY, file.toString());
      MediaType mimetype = tika.getDetector().detect(TikaInputStream.get(path), metadata);
      mimetype.getType();
      mimetype.getSubtype();
      
      assertEquals("",  "jpeg", mimetype.getSubtype());
      
      
    
    } catch (TikaException | IOException e) {
      e.printStackTrace();
    }
    
    
    
    
    
    
    
  }
}
