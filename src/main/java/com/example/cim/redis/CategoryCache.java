package com.example.cim.redis;

import com.example.cim.entity.CategoryEntity;
import com.example.cim.mapper.CategoryMapper;
import com.example.cim.model.CategoryDto;
import com.example.cim.repository.CategoryRepository;
import com.example.config.enums.PublicError;
import com.example.config.exception.BusinessException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.quarkus.cache.CacheResult;
import io.quarkus.redis.datasource.ReactiveRedisDataSource;
import io.quarkus.redis.datasource.RedisDataSource;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;

import java.util.List;

@ApplicationScoped
public class CategoryCache {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Inject
    public CategoryCache(CategoryRepository categoryRepository, CategoryMapper categoryMapper, RedisDataSource ds, ReactiveRedisDataSource reactive, ObjectMapper objectMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }


    @CacheResult(cacheName = "category-cache")
    public CategoryDto detail(String code) {
        CategoryEntity categoryEntity = categoryRepository.findByCode(code);
        if (categoryEntity == null) {
            throw new BusinessException(PublicError.BAD_REQUEST_DATA, Response.Status.NOT_FOUND, "Không tồn tại");
        }
        return categoryMapper.toDto(categoryEntity);
    }

    @CacheResult(cacheName = "all-category-cache")
    public List<CategoryDto> list() {
        List<CategoryEntity> categoryEntities = categoryRepository.listAll();
        return categoryMapper.toDtoList(categoryEntities);
    }
}
