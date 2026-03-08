package com.example.Java_2026ThirdStep;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LogService {

    private final LogRepository logRepository;

    public LogService(LogRepository logRepository) {
        this.logRepository = logRepository;
    }

    public List<Log> findAll() {
        return logRepository.findAll();
    }

    public List<Log> findByUserId(String userId) {
        return logRepository.findAll().stream()
                .filter(log -> log.userId().equals(userId))
                .toList();
    }

    public Log save(Log log) {
        logRepository.save(log);
        return log;
    }
}