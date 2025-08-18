package com.example.notify.mapper;

import com.example.notify.entity.CurrencyEntity;
import com.example.notify.model.CurrencyDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "cdi")
public interface CurrencyMapper {
    CurrencyEntity toEntity(CurrencyDto dto);
    CurrencyDto toDto(CurrencyEntity entity);
    List<CurrencyDto> toDtoList(List<CurrencyEntity> entities);
}
