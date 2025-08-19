package com.example.cim.redis;

import com.example.cim.model.CategoryDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.quarkus.redis.datasource.ReactiveRedisDataSource;
import io.quarkus.redis.datasource.RedisDataSource;
import io.quarkus.redis.datasource.keys.ReactiveKeyCommands;
import io.quarkus.redis.datasource.value.ValueCommands;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@ApplicationScoped
public class CategoryCacheManual {
    private final ValueCommands<String, String> valueCommands;
    private final ReactiveKeyCommands<String> keyCommands;
    private final ObjectMapper objectMapper;

    @Inject
    public CategoryCacheManual(RedisDataSource ds, ReactiveRedisDataSource reactive, ObjectMapper objectMapper) {
        this.valueCommands = ds.value(String.class);
        this.keyCommands = reactive.key();
        this.objectMapper = objectMapper;
    }

    // Lưu danh sách CategoryDto với TTL
    public void setCategoriesCache(String key, List<CategoryDto> categories, int ttlSeconds) {
        try {
            String json = objectMapper.writeValueAsString(categories);
            valueCommands.setex("manual:categories:" + key, ttlSeconds, json);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to serialize categories to JSON", e);
        }
    }

    // Lấy danh sách CategoryDto
    public List<CategoryDto> getCategoriesCache(String key) {
        String json = valueCommands.get("manual:categories:" + key);
        if (json == null) {
            return Collections.emptyList(); // Cache miss
        }
        try {
            return Arrays.asList(objectMapper.readValue(json, CategoryDto[].class));
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to deserialize JSON to List<CategoryDto>", e);
        }
    }

    // Lưu một CategoryDto với TTL
    public void setCategoryCache(String key, CategoryDto category, int ttlSeconds) {
        try {
            String json = objectMapper.writeValueAsString(category);
            valueCommands.setex("manual:category:" + key, ttlSeconds, json);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to serialize category to JSON", e);
        }
    }

    // Lấy một CategoryDto
    public CategoryDto getCategoryCache(String key) {
        String json = valueCommands.get("manual:category:" + key);
        if (json == null) {
            return null; // Cache miss
        }
        try {
            return objectMapper.readValue(json, CategoryDto.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to deserialize JSON to CategoryDto", e);
        }
    }

    // Xóa cache
    public void clearCategoryCache(String key) {
        keyCommands.del("manual:category:" + key, "manual:categories:" + key).await().indefinitely();
    }

    // Xóa tất cả cache với prefix
    public void clearAllCategoryCaches() {
        List<String> keys = keyCommands.keys("manual:category:*").await().indefinitely();
        if (!keys.isEmpty()) {
            keyCommands.del(keys.toArray(new String[0])).await().indefinitely();
        }
        keys = keyCommands.keys("manual:categories:*").await().indefinitely();
        if (!keys.isEmpty()) {
            keyCommands.del(keys.toArray(new String[0])).await().indefinitely();
        }
    }
}
