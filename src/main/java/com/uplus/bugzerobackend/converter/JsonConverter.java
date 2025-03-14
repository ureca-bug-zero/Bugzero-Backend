package com.uplus.bugzerobackend.converter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.io.IOException;
import java.util.List;
@Converter(autoApply = true)  // :흰색_확인_표시: 모든 @Convert 필드에 자동 적용 가능
public class JsonConverter implements AttributeConverter<List<Integer>, String> {
    private static final ObjectMapper objectMapper = new ObjectMapper();
    // :흰색_확인_표시: Java 객체(List<Integer>) -> JSON(String) 변환 (DB 저장용)
    @Override
    public String convertToDatabaseColumn(List<Integer> attribute) {
        if (attribute == null) return null;
        try {
            return objectMapper.writeValueAsString(attribute);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("JSON 변환 오류", e);
        }
    }
    // :흰색_확인_표시: JSON(String) -> Java 객체(List<Integer>) 변환 (조회용)
    @Override
    public List<Integer> convertToEntityAttribute(String dbData) {
        if (dbData == null || dbData.isEmpty()) return null;
        try {
            return objectMapper.readValue(dbData, new TypeReference<List<Integer>>() {});
        } catch (IOException e) {
            throw new RuntimeException("JSON 읽기 오류", e);
        }
    }
}