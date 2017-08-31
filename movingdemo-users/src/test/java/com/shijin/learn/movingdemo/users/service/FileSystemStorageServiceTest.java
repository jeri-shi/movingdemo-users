package com.shijin.learn.movingdemo.users.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
public class FileSystemStorageServiceTest {

  private static final String rootLocation = "c:\\temp\\upload";
  private static Path destination = null;
  
  @Autowired
  private StorageService storageService;
  
  @BeforeClass
  public static void init() {
    try {
      ClassPathResource original = new ClassPathResource("images.jpg");
      destination = Paths.get(rootLocation).resolve("images").resolve("44").resolve("head.jpeg");
      
      Files.createDirectories(destination);
      Files.copy(Paths.get(original.getURI()), destination, StandardCopyOption.REPLACE_EXISTING);
      
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
  
  @AfterClass
  public static void cleanup() {
    try {
      Files.deleteIfExists(destination);
      Files.deleteIfExists(destination.getParent());
      Files.deleteIfExists(destination.getParent().getParent());
      Files.deleteIfExists(Paths.get(rootLocation));
    } catch (IOException e) {
      e.printStackTrace();
    }
    
  }
  
  @Test 
  public void testGet() {
    long id = 44;
    Resource resource = null;
    
    resource = storageService.get(id);
    
    try {
      assertEquals("Path", Paths.get(rootLocation).resolve("images").resolve("" + id).resolve("head.jpeg"), 
          Paths.get(resource.getURI()));
    } catch (IOException e) {
      e.printStackTrace();
    }
    
    id = -100;
    resource = storageService.get(id);
    assertNull(resource);
  }
  
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
