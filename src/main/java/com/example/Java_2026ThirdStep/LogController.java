package com.example.Java_2026ThirdStep;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.List;

@RestController
@RequestMapping("/api/logs")
public class LogController {

    private final LogService logService;

    public LogController(LogService logService) {
        this.logService = logService;
    }

    @GetMapping
    public List<Log> getLogs() {
        return logService.findAll();
    }

    @GetMapping("/{userId}")
    public List<Log> getLogsByUser(@PathVariable String userId) {
        return logService.findByUserId(userId);
    }

    @PostMapping
    public ResponseEntity<Log> addLog(@RequestBody Log log) {
        Log saved = logService.save(log);
        return ResponseEntity.status(201).body(saved);
    }
}