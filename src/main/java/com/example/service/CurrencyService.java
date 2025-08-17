package com.example.service;

import com.example.model.CurrencyDto;
import com.example.model.CurrencyReportDto;
import com.example.model.PageResponse;

import java.util.List;

public interface CurrencyService {
    String add(CurrencyDto currencyDto);
    String update(CurrencyDto currencyDto);
    String delete(String ccyCode);
    CurrencyDto detail(String ccyCode);
    List<CurrencyDto> list();
    PageResponse<CurrencyDto> search(Integer pageSize, Integer pageNum, CurrencyDto currencyDto);
    PageResponse<CurrencyReportDto> search2(Integer pageSize, Integer pageNum, CurrencyDto currencyDto);
}
