package com.shijin.learn.movingdemo.users.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
  @Autowired
  private TestRestTemplate restTemplate;
  
  @Value("${storage.rootLocation}")
  private String rootLocation;

  @Test
  public void handleFileUploadTest() {
    //Replace testRestTemplate's error handler to catch 4xx/5xx runtime exception
    ResponseErrorHandler originalHanlder = restTemplate.getRestTemplate().getErrorHandler();
    DefaultResponseErrorHandler handler = new DefaultResponseErrorHandler();
    restTemplate.getRestTemplate().setErrorHandler(handler);
    
    //prepare request for file upload
    ClassPathResource imageFile = new ClassPathResource("images.jpg");
    LinkedMultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
    map.add("file", imageFile);
    
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.MULTIPART_FORM_DATA);
    
    HttpEntity<LinkedMultiValueMap<String, Object>> requestEntity = 
        new HttpEntity<LinkedMultiValueMap<String,Object>>(map, headers);

    ResponseEntity<String> response = null;
    long id = 7;
    Path expectedImageLocation = Paths.get(rootLocation, ""+id, "head.jpeg");
    try{
      response = restTemplate.withBasicAuth("user", "password")
          .postForEntity("/user/{id}/upload", requestEntity, String.class, id);
      
      assertEquals(HttpStatus.OK, response.getStatusCode());
      assertEquals(expectedImageLocation.toString(), response.getBody());
      assertTrue("images file is created", Files.exists(expectedImageLocation));
    }catch (HttpClientErrorException e) {
      System.out.println(e.getResponseBodyAsString());
    }catch (HttpServerErrorException e) {
      System.out.println(e.getResponseBodyAsString());
    }catch(RestClientException e){
      System.out.println(e.getMessage());
    }    
    
    restTemplate.getRestTemplate().setErrorHandler(originalHanlder);
    
    try {
      Files.deleteIfExists(expectedImageLocation);
      Files.deleteIfExists(expectedImageLocation.getParent());
    } catch (IOException e) {
      e.printStackTrace();
    }
    
    
  }
  
  
}
