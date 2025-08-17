package com.example.service.impl;

import com.example.entity.CurrencyEntity;
import com.example.enums.PublicError;
import com.example.exception.BusinessException;
import com.example.mapper.CurrencyMapper;
import com.example.model.CurrencyDto;
import com.example.model.CurrencyReportDto;
import com.example.model.PageResponse;
import com.example.repository.CurrencyQuery;
import com.example.repository.CurrencyRepository;
import com.example.service.CurrencyService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@ApplicationScoped
@RequiredArgsConstructor
@Slf4j
public class CurrencyServiceImpl implements CurrencyService {

    private final CurrencyMapper currencyMapper;

    private final CurrencyRepository currencyRepository;

    private final CurrencyQuery currencyQuery;

    @Override
    @Transactional
    public String add(CurrencyDto currencyDto) {
        CurrencyEntity entity = currencyMapper.toEntity(currencyDto);
        currencyRepository.persist(entity);
        return entity.getCcyCode();
    }

    @Override
    @Transactional
    public String update(CurrencyDto currencyDto) {
        CurrencyEntity entity = currencyRepository.findById(currencyDto.getId());
        entity.setCcyCode(currencyDto.getCcyCode());
        entity.setCcyName(currencyDto.getCcyName());
        entity.setNumOfDecimal(currencyDto.getNumOfDecimal());
        return entity.getCcyCode();
    }

    @Override
    @Transactional
    public String delete(String ccyCode) {
        CurrencyEntity currencyEntity = currencyRepository.findByCcyCode(ccyCode);
        if (currencyEntity == null) {
            throw new BusinessException(PublicError.BAD_REQUEST_DATA, Response.Status.NOT_FOUND, "Không tồn tại");
        }
        currencyRepository.delete(currencyEntity);
        return ccyCode;
    }

    @Override
    public CurrencyDto detail(String ccyCode) {
        CurrencyEntity currencyEntity = currencyRepository.findByCcyCode(ccyCode);
        if (currencyEntity == null) {
            throw new BusinessException(PublicError.BAD_REQUEST_DATA, Response.Status.NOT_FOUND, "Không tồn tại");
        }
        return currencyMapper.toDto(currencyEntity);
    }

    @Override
    public List<CurrencyDto> list() {
        List<CurrencyEntity> currencyEntities = currencyRepository.listAll();
        return currencyMapper.toDtoList(currencyEntities);
    }

    @Override
    public PageResponse<CurrencyDto> search(Integer pageSize, Integer pageNum, CurrencyDto currencyDto) {
        return currencyRepository.search(currencyDto, pageNum, pageSize, "id", true);
    }

    @Override
    public PageResponse<CurrencyReportDto> search2(Integer pageSize, Integer pageNum, CurrencyDto currencyDto) {
        return currencyQuery.search(currencyDto, pageNum, pageSize, "id", true);
    }
}
