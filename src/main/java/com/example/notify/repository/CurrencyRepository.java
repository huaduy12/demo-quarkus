package com.example.notify.repository;

import com.example.notify.entity.CurrencyEntity;
import com.example.notify.mapper.CurrencyMapper;
import com.example.notify.model.CurrencyDto;
import com.example.notify.model.PageResponse;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import io.quarkus.panache.common.Page;
import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ApplicationScoped
@RequiredArgsConstructor
public class CurrencyRepository implements PanacheRepositoryBase<CurrencyEntity, Long> {

    private final CurrencyMapper currencyMapper;

    public CurrencyEntity findByCcyCode(String ccyCode) {
        return find("ccyCode", ccyCode).firstResult();
    }

    public PageResponse<CurrencyDto> search(CurrencyDto currencyDto, int page, int size, String sortField, boolean asc) {
        StringBuilder query = new StringBuilder("1=1");
        Map<String, Object> params = new HashMap<>();

        if (currencyDto.getCcyCode() != null && !currencyDto.getCcyCode().isBlank()) {
            query.append(" and ccyCode like :code");
            params.put("code", "%" + currencyDto.getCcyCode() + "%");
        }
        if (currencyDto.getCcyName() != null && !currencyDto.getCcyName().isBlank()) {
            query.append(" and ccyName like :name");
            params.put("name", "%" + currencyDto.getCcyName() + "%");
        }
        if (currencyDto.getNumOfDecimal() != null) {
            query.append(" and numOfDecimal = :numOfDecimal");
            params.put("numOfDecimal", currencyDto.getNumOfDecimal());
        }

        // sort
        Sort sort = asc ? Sort.by(sortField).ascending() : Sort.by(sortField).descending();

        // query
        PanacheQuery<CurrencyEntity> panacheQuery = find(query.toString(), sort, params);
        // paging
        List<CurrencyEntity> resultList = panacheQuery.page(Page.of(page, size)).list();

        // tổng số phần tử
        long total = panacheQuery.count();

        return PageResponse.of(currencyMapper.toDtoList(resultList), page, size,total);
    }
}
