package com.example.multithreadingtesttask.service;

import java.util.List;
import java.util.concurrent.ExecutionException;

public interface WordOccurrenceService {

    List<Integer> getWordOccurrencesIndexes(String word) throws ExecutionException, InterruptedException;
}
