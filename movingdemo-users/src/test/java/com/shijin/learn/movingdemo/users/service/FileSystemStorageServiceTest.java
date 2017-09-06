package com.shijin.learn.movingdemo.users.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
  private static final Logger LOGGER = LogManager.getLogger(FileSystemStorageServiceTest.class);
  
  private static final String rootLocation = "c:\\temp\\upload";
  
  @Autowired
  private StorageService storageService;
  
  @BeforeClass
  public static void init() {
    Path root = Paths.get(rootLocation).resolve("images");
    try {
      Files.createDirectories(root);
    } catch (IOException e) {
      LOGGER.error("can't create test folder for {}", e.getMessage());
    }
  }
  
  @AfterClass
  public static void cleanup() {
    Path root = Paths.get(rootLocation).resolve("images");
    LOGGER.debug("delete test root: {}", root);
    try {
      Files.deleteIfExists(root);
    } catch (IOException e) {
      LOGGER.error("can't cleanup test folder for {}", e.getMessage());
    }
  }
  
  @Test 
  public void testGet() {
    long id = 44;
    Resource resource = null;
    prepareImageFile(id);
    
    resource = storageService.get(id);
    
    try {
      assertEquals("Path", Paths.get(rootLocation).resolve("images").resolve("" + id).resolve("head.jpeg"), 
          Paths.get(resource.getURI()));
    } catch (IOException e) {
      e.printStackTrace();
    }
    cleanupFolder(id);
    
    //another test
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
    assertEquals("The file should be stored",  expected.getFileName(), ret);
    assertTrue("The file is created", Files.exists(expected));
    
    cleanupFolder(id);
  }
  
  @Test
  public void testStoreForMultipleImages() {
    //given put multiple files in the destination
    long id = 115;
    prepareImageFile(id);
    
    String image = id + ".png";
    MockMultipartFile multipartFile = new MockMultipartFile("file", image, "multipart/form-data", "AABBCCDD".getBytes());
    //When
    Path ret = storageService.store(id, multipartFile, image);
    //Then
    Path expected = Paths.get(rootLocation + "\\images\\" + id + "\\" + "head.plain");
    assertEquals("The file should be stored",  expected.getFileName(), ret);
    assertTrue("The file is created", Files.exists(expected));
    
    Path destination = Paths.get(rootLocation).resolve("images").resolve(String.valueOf(id)).resolve("head.jpeg");
    assertFalse("The original file should be deleted", Files.exists(destination));
    
    cleanupFolder(id);
  }

  private void cleanupFolder(long folderId) {
    Path location = Paths.get(rootLocation).resolve("images").resolve(String.valueOf(folderId));
    LOGGER.debug("delete test folder: {}", location);
    DirectoryStream<Path> stream;
    try {
      stream = Files.newDirectoryStream(location, "*.*");
      stream.forEach((path) -> {
        LOGGER.debug(path);
        try {
          Files.delete(path);
        } catch (IOException e) {
          LOGGER.debug("Delete error: {}", e.getMessage());
        }
      });
      Files.deleteIfExists(location);
      LOGGER.debug("delete done! {}", location);
    } catch (IOException e) {
      LOGGER.debug(" found exiting images error: {}", e.getMessage());
    }

  }
  
  private void prepareImageFile(long folderId) {
    try {
      ClassPathResource original = new ClassPathResource("images.jpg");
      Path destination = Paths.get(rootLocation).resolve("images").resolve(String.valueOf(folderId)).resolve("head.jpeg");
      
      Files.createDirectories(destination.getParent());
      Files.copy(Paths.get(original.getURI()), destination, StandardCopyOption.REPLACE_EXISTING);
      assertTrue("should be a " + folderId + "/head.jpeg in place", Files.exists(destination));
      
      LOGGER.debug("prepare test folder: {}", destination);
    } catch (IOException e) {
      LOGGER.error("prepare folder error: {}", e.getMessage());
    }
  }
  
}
