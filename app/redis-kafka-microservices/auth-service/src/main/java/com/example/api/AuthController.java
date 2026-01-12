package com.example.auth.api;

import com.example.auth.security.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

  private final JwtUtil jwtUtil;

  public AuthController(JwtUtil jwtUtil) {
    this.jwtUtil = jwtUtil;
  }

  @PostMapping("/login")
  public ResponseEntity<?> login(@RequestBody Map<String, String> req) {
    String username = req.get("username");
    String password = req.get("password");

    if ("alice".equals(username) && "password".equals(password)) {
      return ResponseEntity.ok(Map.of("accessToken",
          jwtUtil.generateToken("alice", List.of("ROLE_USER"))));
    }
    if ("admin".equals(username) && "password".equals(password)) {
      return ResponseEntity.ok(Map.of("accessToken",
          jwtUtil.generateToken("admin", List.of("ROLE_ADMIN"))));
    }

    return ResponseEntity.status(401).body(Map.of("error", "Invalid credentials"));
  }
}
