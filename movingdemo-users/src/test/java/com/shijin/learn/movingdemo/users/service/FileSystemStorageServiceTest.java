package com.shijin.learn.movingdemo.users.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
public class FileSystemStorageServiceTest {

  private static final String rootLocation = "c:\\temp\\upload";
  
  @Autowired
  private StorageService storageService;
  
  @Test
  public void testStore() {
    //Given
    long id = 6;
    String image = "test66.png";
    MockMultipartFile multipartFile = new MockMultipartFile("file", image, "multipart/form-data", "AABBCCDD".getBytes());
    
    //When
    Path ret = storageService.store(id, multipartFile, image);
    
    //Then
    Path expected = Paths.get(rootLocation + "\\images\\" + id + "\\" + "head.plain");
    assertEquals("The file should be stored",  expected, ret);
    assertTrue("The file is created", Files.exists(expected));
    
    try {
      Files.deleteIfExists(ret);
      Files.deleteIfExists(ret.getParent());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
  
}
