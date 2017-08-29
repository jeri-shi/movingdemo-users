package com.shijin.learn.movingdemo.users.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice(annotations = RestController.class)
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
  private static final Logger LOGGER = LogManager.getLogger(RestResponseEntityExceptionHandler.class);
  
  @ExceptionHandler(value={DuplicateKeyException.class})
  protected ResponseEntity<Object> handleKeyConflict(RuntimeException ex, WebRequest request) {
    LOGGER.debug("Exception is ", ex);
    String bodyofResponse = "Duplicated companyname with username";
    return handleExceptionInternal(ex, bodyofResponse, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
  }
  
}
