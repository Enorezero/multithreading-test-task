package com.example.multithreadingtesttask.controller;

import com.example.multithreadingtesttask.service.WordOccurrenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
public class WordOccurrenceController {

    @Autowired
    WordOccurrenceService service;

    @GetMapping("/")
    public ResponseEntity<List<Integer>> getWordOccurrencesIndexes() throws ExecutionException, InterruptedException {
        return ResponseEntity.status(HttpStatus.OK).body(service.getWordOccurrencesIndexes("Пьер"));
    }

}
