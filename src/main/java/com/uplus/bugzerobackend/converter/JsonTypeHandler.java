package com.uplus.bugzerobackend.converter;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import java.sql.*;
import java.util.List;

public class JsonTypeHandler extends BaseTypeHandler<List<Integer>> {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    // ✅ Java 객체 → JSON 문자열 (DB 저장 시)
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, List<Integer> parameter, JdbcType jdbcType) throws SQLException {
        try {
            ps.setString(i, objectMapper.writeValueAsString(parameter));
        } catch (Exception e) {
            throw new SQLException("JSON 변환 오류", e);
        }
    }

    // ✅ JSON 문자열 → Java 객체 (DB 조회 시 - 컬럼명 기준)
    @Override
    public List<Integer> getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return toList(rs.getString(columnName));
    }

    // ✅ JSON 문자열 → Java 객체 (DB 조회 시 - 컬럼 인덱스 기준)
    @Override
    public List<Integer> getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return toList(rs.getString(columnIndex));
    }

    // ✅ JSON 문자열 → Java 객체 (CallableStatement 사용 시)
    @Override
    public List<Integer> getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return toList(cs.getString(columnIndex));
    }

    private List<Integer> toList(String json) throws SQLException {
        if (json == null || json.isEmpty()) {
            return null;
        }
        try {
            return objectMapper.readValue(json, new TypeReference<List<Integer>>() {});
        } catch (Exception e) {
            throw new SQLException("JSON 읽기 오류", e);
        }
    }
}