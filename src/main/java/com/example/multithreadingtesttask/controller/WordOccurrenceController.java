package com.example.multithreadingtesttask.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class Controller {

    @GetMapping("/")
    public ResponseEntity<List<Integer>> getAllIndexesOfOccurences(){
        return ResponseEntity.status(HttpStatus.OK).body();
    }

}
