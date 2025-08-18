package com.example.cim.service;

import com.example.cim.model.PageResponse;
import com.example.cim.model.CategoryDto;

import java.util.List;

public interface CategoryService {
    String add(CategoryDto categoryDto);

    String update(CategoryDto categoryDto);

    String delete(String ccyCode);

    CategoryDto detail(String ccyCode);

    List<CategoryDto> list();

    PageResponse<CategoryDto> search(Integer pageSize, Integer pageNum, CategoryDto categoryDto);
}
