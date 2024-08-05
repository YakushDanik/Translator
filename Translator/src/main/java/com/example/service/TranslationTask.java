package com.example.service;

import org.springframework.web.client.RestTemplate;

import java.util.concurrent.Callable;

public class TranslationTask implements Callable<String> {
    private final String word;
    private final String sourceLang;
    private final String targetLang;
    private final RestTemplate restTemplate;
    private final String apiKey;
    private final String apiUrl;

    public TranslationTask(String word, String sourceLang, String targetLang, RestTemplate restTemplate, String apiKey, String apiUrl) {
        this.word = word;
        this.sourceLang = sourceLang;
        this.targetLang = targetLang;
        this.restTemplate = restTemplate;
        this.apiKey = apiKey;
        this.apiUrl = apiUrl;
    }

    @Override
    public String call() throws Exception {
        String url = String.format("%s?text=%s&source=%s&target=%s&key=%s", apiUrl, word, sourceLang, targetLang, apiKey);
        return restTemplate.getForObject(url, String.class);
    }
}