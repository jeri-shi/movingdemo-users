package com.shijin.learn.movingdemo.users.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.stream.Stream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestClientException;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class FileUploadControllerIntegrationTest {
  private static final Logger LOGGER =
      LogManager.getLogger(FileUploadControllerIntegrationTest.class);

  @Autowired
  private TestRestTemplate restTemplate;

  private static final String rootLocation = "c:\\temp\\upload\\images";

  @BeforeClass
  public static void setup() {
    try {
      Files.createDirectories(Paths.get(rootLocation));
    } catch (IOException e) {
      LOGGER.warn("could not create folder {}", rootLocation);
      e.printStackTrace();
    }
  }

  @AfterClass
  public static void cleanup() {
    LOGGER.trace("cleanup()...");
    LOGGER.trace("delete folders...");
    try {
      Files.deleteIfExists(Paths.get(rootLocation));
      LOGGER.trace("cleanup() delete {}", Paths.get(rootLocation));
      Files.deleteIfExists(Paths.get(rootLocation).getParent());
      LOGGER.trace("cleanup() delete {}", Paths.get(rootLocation).getParent());
    } catch (IOException e) {
      LOGGER.warn("could not delete folder {}", e.getMessage());
      e.printStackTrace();
    }

  }

  @Test
  public void handleGetImageTest() {
    // given
    ClassPathResource original = new ClassPathResource("images.jpg");
    Path destination = Paths.get(rootLocation).resolve("44").resolve("head.jpeg");
    try {
      Files.createDirectories(destination.getParent());
      Files.copy(Paths.get(original.getURI()), destination, StandardCopyOption.REPLACE_EXISTING);
    } catch (IOException e) {
      assertTrue(e.getMessage(), false);
    }
    long id = 44;

    // when
    byte[] image = restTemplate.withBasicAuth("user", "password").getForObject("/user/{id}/photo",
        byte[].class, id);

    // then
    assertNotNull(image);
    assertEquals("image should be same size", destination.toFile().length(), image.length);

    // cleanup
    try {
      Files.deleteIfExists(destination); // head.jpeg
      LOGGER.trace("delete: {}", destination);
      Files.deleteIfExists(destination.getParent()); // 44
      LOGGER.trace("delete: {}", destination.getParent());
      // Files.deleteIfExists(destination.getParent().getParent()); //images
      // LOGGER.trace("delete: {}", destination.getParent().getParent());
      // Files.deleteIfExists(Paths.get(rootLocation).getParent()); //upload
      // LOGGER.trace("delete: {}", Paths.get(rootLocation).getParent());
    } catch (IOException e) {
      LOGGER.warn("could not delete {}", e.getMessage());
      e.printStackTrace();
    }


  }

  @Test
  public void handleFileUploadTest() {
    // Replace testRestTemplate's error handler to catch 4xx/5xx runtime exception
    ResponseErrorHandler originalHanlder = restTemplate.getRestTemplate().getErrorHandler();
    DefaultResponseErrorHandler handler = new DefaultResponseErrorHandler();
    restTemplate.getRestTemplate().setErrorHandler(handler);

    // prepare request for file upload
    ClassPathResource imageFile = new ClassPathResource("images.jpg");
    LinkedMultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
    map.add("file", imageFile);

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.MULTIPART_FORM_DATA);

    HttpEntity<LinkedMultiValueMap<String, Object>> requestEntity =
        new HttpEntity<LinkedMultiValueMap<String, Object>>(map, headers);

    ResponseEntity<String> response = null;
    long id = 7;
    Path expectedImageLocation = Paths.get(rootLocation, "" + id, "head.jpeg");
    try {
      response = restTemplate.withBasicAuth("user", "password").postForEntity("/user/{id}/photo",
          requestEntity, String.class, id);

      assertEquals(HttpStatus.OK, response.getStatusCode());
      assertEquals(expectedImageLocation.getFileName().toString(), response.getBody());
      assertTrue("images file is created", Files.exists(expectedImageLocation));
    } catch (HttpClientErrorException e) {
      System.out.println(e.getResponseBodyAsString());
    } catch (HttpServerErrorException e) {
      System.out.println(e.getResponseBodyAsString());
    } catch (RestClientException e) {
      System.out.println(e.getMessage());
    }

    restTemplate.getRestTemplate().setErrorHandler(originalHanlder);

    try {
      Files.deleteIfExists(expectedImageLocation); // head.jpeg
      LOGGER.trace("delete {} ", expectedImageLocation);
      Files.deleteIfExists(expectedImageLocation.getParent()); // 7
      LOGGER.trace("delete {} ", expectedImageLocation.getParent());
    } catch (IOException e) {
      LOGGER.warn("could not delete {}", e.getMessage());
      e.printStackTrace();
    }


  }


}
