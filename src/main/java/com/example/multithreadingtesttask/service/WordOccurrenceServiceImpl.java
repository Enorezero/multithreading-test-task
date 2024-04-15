package com.example.multithreadingtesttask.service;

import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.*;

@Service
public class WordOccurrenceServiceImpl implements WordOccurrenceService {

    @Override
    public List<Integer> getWordOccurrencesIndexes(String word) throws ExecutionException, InterruptedException {

        String text = getText("src/main/resources/static/война-и-мир.txt");

        List<Future<List<Integer>>> results = new ArrayList<>();

        int numberOfThreads = Runtime.getRuntime().availableProcessors();

        ExecutorService executor = Executors.newFixedThreadPool(numberOfThreads);

        int part = text.length() / numberOfThreads;

        for (int i = 0; i < numberOfThreads; i++) {

            int start = i * part;
            int end;

            if(i != numberOfThreads -1){
                end = start + part;
            }else{
                end = text.length();
            }

            results.add(executor.submit(new WordOccurrenceTask(text.substring(start, end), word)));
        }

        List<Integer> indexis = new ArrayList<>();
        for (Future<List<Integer>> result : results) {
            indexis.addAll(result.get());
        }

        executor.shutdown();

        indexis.addAll(checkBoundariesOfThePart(text, word, numberOfThreads));

        Collections.sort(indexis);

        return indexis;
    }

    private static class WordOccurrenceTask implements Callable<List<Integer>> {
        private final String text;

        private final String word;

        public WordOccurrenceTask(String text, String word) {
            this.text = text;
            this.word = word;
        }

        @Override
        public List<Integer> call() {
            List<Integer> indexis = new ArrayList<>();

            int index = 0;

            while ((index = text.indexOf(word, index)) != -1) {
                indexis.add(index);
                index += word.length();
            }

            return indexis;
        }
    }

    private List<Integer> checkBoundariesOfThePart(String text, String word, int numberOfThreads){
        List<Integer> result = new ArrayList<>();

        int part = text.length()/numberOfThreads;

        for (int i = 1; i < numberOfThreads; i++){

            int index = 0;

            if(text.substring(part*i - word.length() - 1, part*i + word.length() - 1).indexOf(word, index) != -1){
                result.add(index);
            }
        }

        return result;

    }

    private String getText(String fileName){
        StringBuilder builder = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {

            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line).append("\n");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return builder.toString();
    }
}
