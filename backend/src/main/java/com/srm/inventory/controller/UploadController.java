package com.srm.inventory.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Map;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/uploads")
public class UploadController {
  @Value("${app.upload-dir}") private String uploadDir;

  @PostMapping("/product-image")
  ResponseEntity<Map<String, String>> upload(@RequestParam("image") MultipartFile image) throws IOException {
    if (image.isEmpty() || image.getContentType() == null || !image.getContentType().startsWith("image/")) {
      return ResponseEntity.badRequest().body(Map.of("message", "Please choose an image file"));
    }
    if (image.getSize() > 5_000_000) {
      return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE).body(Map.of("message", "Image must be 5 MB or smaller"));
    }
    String original = image.getOriginalFilename() == null ? "image.png" : image.getOriginalFilename();
    String extension = original.contains(".") ? original.substring(original.lastIndexOf('.')) : ".png";
    String filename = UUID.randomUUID() + extension.toLowerCase();
    Path directory = Path.of(uploadDir).toAbsolutePath().normalize();
    Files.createDirectories(directory);
    Files.copy(image.getInputStream(), directory.resolve(filename), StandardCopyOption.REPLACE_EXISTING);
    return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("imageUrl", "/uploads/" + filename));
  }
}
