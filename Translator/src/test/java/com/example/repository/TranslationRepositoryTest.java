package com.example.repository;

import com.example.model.TranslationRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class TranslationRepositoryTest {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private TranslationRepository translationRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSave() {
        TranslationRequest request = new TranslationRequest();
        request.setIpAddress("127.0.0.1");
        request.setInputText("Hello");
        request.setTranslatedText("Bonjour");
        request.setSourceLang("en");
        request.setTargetLang("fr");

        translationRepository.save(request);

        verify(jdbcTemplate, times(1)).update(anyString(), any(Object.class), any(Object.class), any(Object.class), any(Object.class), any(Object.class));
    }

}
