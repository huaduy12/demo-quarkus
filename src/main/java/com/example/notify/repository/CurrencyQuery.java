package com.example.notify.repository;

import com.example.notify.model.CurrencyDto;
import com.example.notify.model.CurrencyReportDto;
import com.example.notify.model.PageResponse;
import io.quarkus.hibernate.orm.PersistenceUnit;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.Tuple;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ApplicationScoped
public class CurrencyQuery {

    @Inject
    @PersistenceUnit("notify")
    EntityManager em;

    public PageResponse<CurrencyReportDto> search(CurrencyDto currencyDto,
                                                  int page, int size,
                                                  String sortField, boolean asc) {
        String baseSql = """
            SELECT c.id, c.ccy_code, c.ccy_name, c.num_of_decimal, co.message
            FROM currency c
            LEFT JOIN report_log co ON c.id = co.id
            WHERE 1=1
        """;

        String countSql = """
            SELECT COUNT(*) 
            FROM currency c
            LEFT JOIN report_log co ON c.id = co.id
            WHERE 1=1
        """;

        Map<String, Object> params = new HashMap<>();
        if (currencyDto.getCcyCode() != null && !currencyDto.getCcyCode().isBlank()) {
            baseSql += " AND lower(c.ccy_code) LIKE :ccyCode";
            countSql += " AND lower(c.ccy_code) LIKE :ccyCode";
            params.put("ccyCode", "%" + currencyDto.getCcyCode() + "%");
        }
        if (currencyDto.getCcyName() != null && !currencyDto.getCcyName().isBlank()) {
            baseSql += " AND lower(c.ccy_name) LIKE :ccyName";
            countSql += " AND lower(c.ccy_name) LIKE :ccyName";
            params.put("ccyName", "%" + currencyDto.getCcyName() + "%");
        }

        baseSql += " ORDER BY c." + sortField + (asc ? " ASC" : " DESC");

        Query query = em.createNativeQuery(baseSql, Tuple.class);
        Query countQuery = em.createNativeQuery(countSql);

        params.forEach((k, v) -> {
            query.setParameter(k, v);
            countQuery.setParameter(k, v);
        });

        query.setFirstResult(page * size);
        query.setMaxResults(size);

        List<Tuple> tuples = query.getResultList();

        List<CurrencyReportDto> dtos = tuples.stream()
                .map(t -> new CurrencyReportDto(
                        ((BigDecimal) t.get("id")).longValue(),
                        t.get("ccy_code", String.class),
                        t.get("ccy_name", String.class),
                        ((BigDecimal) t.get("num_of_decimal")).intValue(),
                        t.get("message", String.class)
                ))
                .toList();

        long total = ((BigDecimal) countQuery.getSingleResult()).longValue();

        return PageResponse.of(dtos, page, size, total);
    }
}
