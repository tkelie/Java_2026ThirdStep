package com.example.Java_2026ThirdStep;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/logs")
public class LogController {

    private final List<Log> logs = new ArrayList<>(List.of(
        new Log("alice", "LOGIN"),
        new Log("alice", "LOGOUT"),
        new Log("bob",   "LOGIN"),
        new Log("charlie", "LOGIN"),
        new Log("charlie", "LOGOUT")
    ));

    @GetMapping
    public List<Log> getLogs() {
        return logs;
    }

    @GetMapping("/{userId}")
    public List<Log> getLogsByUser(@PathVariable String userId) {
        return logs.stream()
                   .filter(log -> log.userId().equals(userId))
                   .toList();
    }

    @PostMapping
    public ResponseEntity<Log> addLog(@RequestBody Log log) {
        logs.add(log);
        return ResponseEntity.status(201).body(log);
    }
}