package com.example.model;

import java.util.List;

public class LectoTranslationRequest {
    private List<String> to;
    private String from;
    private List<String> protectedKeys;
    private String json;

    public List<String> getTo() {
        return to;
    }

    public void setTo(List<String> to) {
        this.to = to;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public List<String> getProtectedKeys() {
        return protectedKeys;
    }

    public void setProtectedKeys(List<String> protectedKeys) {
        this.protectedKeys = protectedKeys;
    }

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }
}
