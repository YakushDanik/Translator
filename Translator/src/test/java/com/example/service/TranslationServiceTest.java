package com.example.service;

import com.example.model.LectoTranslationRequest;
import com.example.model.LectoTranslationResponse;
import com.example.model.TranslationRequest;
import com.example.repository.TranslationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.concurrent.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class TranslationServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private TranslationRepository translationRepository;

    @InjectMocks
    private TranslationService translationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testTranslate() throws ExecutionException, InterruptedException {
        LectoTranslationRequest lectoRequest = new LectoTranslationRequest();
        lectoRequest.setJson("Hello world");
        lectoRequest.setFrom("en");
        lectoRequest.setTo(List.of("fr"));

        LectoTranslationResponse mockResponse = new LectoTranslationResponse();
        mockResponse.setTranslatedTexts(List.of("Bonjour", "monde"));

        when(restTemplate.postForEntity(anyString(), any(HttpEntity.class), eq(LectoTranslationResponse.class)))
                .thenReturn(new ResponseEntity<>(mockResponse, HttpStatus.OK));

        String result = translationService.translate(lectoRequest);

        verify(translationRepository, times(1)).save(any(TranslationRequest.class));
        assertEquals("Bonjour monde", result);
    }
}
