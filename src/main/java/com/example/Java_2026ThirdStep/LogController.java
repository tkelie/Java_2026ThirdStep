package com.example.Java_2026ThirdStep;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/logs")
public class LogController {

    @GetMapping
    public List<Log> getLogs() {
        return List.of(
                new Log("alice", "LOGIN"),
                new Log("alice", "LOGOUT"),
                new Log("bob", "LOGIN"),
                new Log("charlie", "LOGIN"),
                new Log("charlie", "LOGOUT"));
    }

    @GetMapping("/{userId}")
    public List<Log> getLogsByUser(@PathVariable String userId) {
        return List.of(
                new Log("alice", "LOGIN"),
                new Log("alice", "LOGOUT"),
                new Log("bob", "LOGIN"),
                new Log("charlie", "LOGIN"),
                new Log("charlie", "LOGOUT")).stream()
                .filter(log -> log.userId().equals(userId))
                .toList();
    }
}