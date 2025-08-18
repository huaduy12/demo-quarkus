package com.example.cim.mapper;

import com.example.cim.entity.CategoryEntity;
import com.example.cim.model.CategoryDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "cdi")
public interface CategoryMapper {
    CategoryEntity toEntity(CategoryDto dto);
    CategoryDto toDto(CategoryEntity entity);
    List<CategoryDto> toDtoList(List<CategoryEntity> entities);
}
