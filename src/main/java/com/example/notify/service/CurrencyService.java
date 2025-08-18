package com.example.notify.service;

import com.example.notify.model.CurrencyDto;
import com.example.notify.model.CurrencyReportDto;
import com.example.notify.model.PageResponse;

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
