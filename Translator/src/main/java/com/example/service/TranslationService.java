package com.example.service;

import com.example.model.LectoTranslationRequest;
import com.example.model.LectoTranslationResponse;
import com.example.model.TranslationRequest;
import com.example.repository.TranslationRepository;

import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.*;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;

@Service
public class TranslationService {

    private final RestTemplate restTemplate;
    private final TranslationRepository translationRepository;
    private final String API_KEY = "BFMMVYP-XP74TFY-KZD7JGZ-VXEDRT4";
    private final String API_URL = "https://api.lecto.ai/v1/translate/text";
    private final ExecutorService executorService;

    @Autowired
    public TranslationService(RestTemplate restTemplate, TranslationRepository translationRepository) {
        this.restTemplate = restTemplate;
        this.translationRepository = translationRepository;
        this.executorService = Executors.newFixedThreadPool(10);
    }

    public String translate(LectoTranslationRequest lectoRequest) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        headers.set("X-API-Key", API_KEY);

        String[] words = lectoRequest.getJson().split("\\s+");

        List<Future<String>> futures = new ArrayList<>();
        
        for (String word : words) {
            futures.add(executorService.submit(() -> translateWord(word, lectoRequest.getFrom(), lectoRequest.getTo().get(0))));
        }

        List<String> translatedWords = new ArrayList<>();
        
        for (Future<String> future : futures) {
            try {
                translatedWords.add(future.get());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }

        String translatedText = String.join(" ", translatedWords);

        TranslationRequest translationRequest = new TranslationRequest();
        translationRequest.setIpAddress("127.0.0.1");
        translationRequest.setInputText(lectoRequest.getJson());
        translationRequest.setTranslatedText(translatedText);
        translationRequest.setSourceLang(lectoRequest.getFrom());
        translationRequest.setTargetLang(lectoRequest.getTo().get(0));

        translationRepository.save(translationRequest);

        return translatedText;
    }

    private String translateWord(String word, String from, String to) {
        LectoTranslationRequest singleWordRequest = new LectoTranslationRequest();
        singleWordRequest.setJson(word);
        singleWordRequest.setFrom(from);
        singleWordRequest.setTo(List.of(to));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        headers.set("X-API-Key", API_KEY);

        HttpEntity<LectoTranslationRequest> requestEntity = new HttpEntity<>(singleWordRequest, headers);

        ResponseEntity<LectoTranslationResponse> responseEntity = restTemplate.postForEntity(API_URL, requestEntity, LectoTranslationResponse.class);
        LectoTranslationResponse response = responseEntity.getBody();

        if (response != null && !response.getTranslatedTexts().isEmpty()) {
            return response.getTranslatedTexts().get(0);
        } else {
            return word; 
        }
    }
}
