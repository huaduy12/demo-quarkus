package com.example.mapper;

import com.example.entity.CurrencyEntity;
import com.example.model.CurrencyDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "cdi")
public interface CurrencyMapper {
    CurrencyEntity toEntity(CurrencyDto dto);
    CurrencyDto toDto(CurrencyEntity entity);
    List<CurrencyDto> toDtoList(List<CurrencyEntity> entities);
}
