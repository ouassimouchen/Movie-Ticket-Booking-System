package com.cinepass.backend.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/")
    public ResponseEntity<String> root() {
        return ResponseEntity.ok("CinePass Java Backend is Running! Access API at /api/...");
    }
}
