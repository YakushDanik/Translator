package com.example.controller;

import com.example.model.LectoTranslationRequest;
import com.example.service.TranslationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import static org.mockito.Mockito.*;

@WebMvcTest(TranslationController.class)
class TranslationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private TranslationService translationService;

    @InjectMocks
    private TranslationController translationController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(translationController).build();
    }

    @Test
    void testTranslate() throws Exception {
        LectoTranslationRequest request = new LectoTranslationRequest();
        request.setJson("Hello world");
        request.setFrom("en");
        request.setTo(List.of("fr"));

        when(translationService.translate(any(LectoTranslationRequest.class))).thenReturn("Bonjour monde");

        mockMvc.perform(post("/translate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isOk());

        verify(translationService, times(1)).translate(any(LectoTranslationRequest.class));
    }
}
