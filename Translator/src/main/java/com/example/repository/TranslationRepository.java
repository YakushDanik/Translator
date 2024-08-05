package com.example.repository;

import com.example.model.TranslationRequest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Repository
public class TranslationRepository {
    private static final Logger logger = LoggerFactory.getLogger(TranslationRepository.class);

    private final JdbcTemplate jdbcTemplate;

    public TranslationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void save(TranslationRequest request) {
        String sql = "INSERT INTO translation_requests (ip_address, input_text, translated_text, source_lang, target_lang) VALUES (?, ?, ?, ?, ?)";
        try {
            jdbcTemplate.update(sql, request.getIpAddress(), request.getInputText(), request.getTranslatedText(), request.getSourceLang(), request.getTargetLang());
            logger.info("Translation request inserted into database");
        } catch (Exception e) {
            logger.error("Error inserting translation request into database", e);
        }
    }

    public List<TranslationRequest> findAll() {
        String sql = "SELECT * FROM translation_requests";
        return jdbcTemplate.query(sql, new TranslationRequestRowMapper());
    }

    private static class TranslationRequestRowMapper implements RowMapper<TranslationRequest> {
        @Override
        public TranslationRequest mapRow(ResultSet rs, int rowNum) throws SQLException {
            TranslationRequest request = new TranslationRequest();
            request.setId(rs.getLong("id"));
            request.setIpAddress(rs.getString("ip_address"));
            request.setInputText(rs.getString("input_text"));
            request.setTranslatedText(rs.getString("translated_text"));
            request.setSourceLang(rs.getString("source_lang"));
            request.setTargetLang(rs.getString("target_lang"));
            return request;
        }
    }
}
