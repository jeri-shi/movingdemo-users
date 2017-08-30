package com.shijin.learn.movingdemo.users.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.tika.config.TikaConfig;
import org.apache.tika.exception.TikaException;
import org.apache.tika.io.TikaInputStream;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.mime.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileSystemStorageService implements StorageService {
  private final static Logger LOGGER = LogManager.getLogger(FileSystemStorageService.class);

  private Path rootLocation;

  @Autowired
  public FileSystemStorageService(StorageProperties properties) {
    rootLocation = Paths.get(properties.getRootLocation());
  }


  @Override
  public Path store(long id, MultipartFile file, String filename) {
    LOGGER.debug("store file... id={}, filename={}", id, filename);

    Path location = rootLocation.resolve("" + id);
    if (Files.notExists(location)) {
      try {
        Files.createDirectories(location);
      } catch (IOException e) {
        LOGGER.error("Can't create upload folders: {}", e.getMessage());
      }

    }
    Path imageLocation = null;
    try {
      String type = getFileType(file.getBytes());
      if (type == null) {
        // TODO throw exception to deny illeagal file type
        return null;
      }
      imageLocation = location.resolve("head." + type);
      Files.copy(file.getInputStream(), imageLocation, StandardCopyOption.REPLACE_EXISTING);
    } catch (IOException e) {
      LOGGER.error("Can't copy upload file: {}", e.getMessage());
    }

    LOGGER.debug("put image into {}", imageLocation);
    return imageLocation;
  }

  /**
   * When filename is test.png then return .png
   * 
   * @param filename
   * @return
   */
  private String getFileType(byte[] file) {
    String fileType = null;
    try {
      TikaConfig tika = new TikaConfig();
      Metadata metadata = new Metadata();
      metadata.set(Metadata.RESOURCE_NAME_KEY, file.toString());
      MediaType mimetype = tika.getDetector().detect(TikaInputStream.get(file), metadata);
      mimetype.getType();
      fileType = mimetype.getSubtype();
    } catch (TikaException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return fileType;
  }

}
