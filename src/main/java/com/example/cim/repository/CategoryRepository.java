package com.example.cim.repository;

import com.example.cim.entity.CategoryEntity;
import com.example.cim.mapper.CategoryMapper;
import com.example.cim.model.CategoryDto;
import com.example.cim.model.PageResponse;
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
public class CategoryRepository implements PanacheRepositoryBase<CategoryEntity, Long> {

    private final CategoryMapper categoryMapper;

    public CategoryEntity findByCode(String code) {
        return find("code", code).firstResult();
    }

    public PageResponse<CategoryDto> search(CategoryDto categoryDto, int page, int size, String sortField, boolean asc) {
        StringBuilder query = new StringBuilder("1=1");
        Map<String, Object> params = new HashMap<>();

        if (categoryDto.getCode() != null && !categoryDto.getCode().isBlank()) {
            query.append(" and code like :code");
            params.put("code", "%" + categoryDto.getCode() + "%");
        }
        if (categoryDto.getName() != null && !categoryDto.getName().isBlank()) {
            query.append(" and name like :name");
            params.put("name", "%" + categoryDto.getName() + "%");
        }
        // sort
        Sort sort = asc ? Sort.by(sortField).ascending() : Sort.by(sortField).descending();

        // query
        PanacheQuery<CategoryEntity> panacheQuery = find(query.toString(), sort, params);
        // paging
        List<CategoryEntity> resultList = panacheQuery.page(Page.of(page, size)).list();

        // tổng số phần tử
        long total = panacheQuery.count();

        return PageResponse.of(categoryMapper.toDtoList(resultList), page, size,total);
    }
}
