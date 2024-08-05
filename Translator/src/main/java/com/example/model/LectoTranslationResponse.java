package com.example.model;

import java.util.List;

public class LectoTranslationResponse {
    private List<String> translatedTexts;

    public List<String> getTranslatedTexts() {
        return translatedTexts;
    }

    public void setTranslatedTexts(List<String> translatedTexts) {
        this.translatedTexts = translatedTexts;
    }
}
