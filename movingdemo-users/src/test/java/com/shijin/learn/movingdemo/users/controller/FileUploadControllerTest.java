package com.shijin.learn.movingdemo.users.controller;

import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.fileUpload;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.shijin.learn.movingdemo.users.service.StorageService;

@RunWith(SpringRunner.class)
@WebMvcTest(FileUploadController.class)
public class FileUploadControllerTest {
  
  @Autowired
  private WebApplicationContext context;
  
  private MockMvc mvc;

  @MockBean
  private StorageService storageService;

  @Before
  public void setup() {
    mvc = MockMvcBuilders.webAppContextSetup(context).apply(SecurityMockMvcConfigurers.springSecurity()).build();
  }

  @Test
  public void shouldSaveUploadedFile() throws Exception {
    long id = 6;
    Path path = Paths.get("c:\\uploaded\\images\\" + id + "\\test.png");
    MockMultipartFile multipartFile = new MockMultipartFile("file", "test.png", "multipart/form-data", "AABBCCDD".getBytes());
    //given
    given(storageService.store(id, multipartFile, null)).willReturn(path);
    
    //when and then
    mvc.perform(fileUpload("/user/{id}/photo", 6).file(multipartFile).with(user("user")))
      .andExpect(status().isOk())
      .andExpect(content().string(path.toString()));
  }
  
  @Test
  public void shouldGetImageFile() throws Exception {
    long id = 45;
    Resource resource = new ByteArrayResource("dsssoafdfd".getBytes());
    given(storageService.get(45)).willReturn(resource);
    
    //when
    mvc.perform(get("/user/{id}/photo", id).with(user("user")))
        .andExpect(status().isOk())
        .andExpect(content().bytes("dsssoafdfd".getBytes()));
      
  }
  
}
