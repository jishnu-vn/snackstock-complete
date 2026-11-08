package com.srm.inventory.controller;

import com.srm.inventory.dto.LoginRequest;
import jakarta.validation.Valid;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
  @Value("${app.admin-id}") private String adminId;
  @Value("${app.admin-password}") private String adminPassword;

  @PostMapping("/login")
  ResponseEntity<Map<String, String>> login(@Valid @RequestBody LoginRequest request) {
    if (!adminId.equals(request.adminId()) || !adminPassword.equals(request.password())) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "Incorrect admin ID or password"));
    }
    return ResponseEntity.ok(Map.of("role", "ADMIN", "adminId", adminId));
  }
}
