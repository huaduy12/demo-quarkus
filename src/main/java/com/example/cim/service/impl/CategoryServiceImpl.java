package com.example.cim.service.impl;

import com.example.cim.entity.CategoryEntity;
import com.example.cim.feignclient.TestServiceFeignClient;
import com.example.cim.mapper.CategoryMapper;
import com.example.cim.model.CategoryDto;
import com.example.cim.model.PageResponse;
import com.example.cim.model.UserDto;
import com.example.cim.repository.CategoryRepository;
import com.example.cim.service.CategoryService;
import com.example.config.enums.PublicError;
import com.example.config.exception.BusinessException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.util.List;

@ApplicationScoped
@Slf4j
public class CategoryServiceImpl implements CategoryService {

    @Inject
    private CategoryMapper categoryMapper;
    @Inject
    private CategoryRepository categoryRepository;

    @Inject
    @RestClient
    private TestServiceFeignClient testServiceFeignClient;

    @Override
    @Transactional
    public String add(CategoryDto categoryDto) {
        CategoryEntity entity = categoryMapper.toEntity(categoryDto);
        categoryRepository.persist(entity);
        return entity.getCode();
    }

    @Override
    @Transactional
    public String update(CategoryDto categoryDto) {
        CategoryEntity entity = categoryRepository.findById(categoryDto.getId());
        entity.setCode(categoryDto.getCode());
        entity.setName(categoryDto.getName());
        entity.setType(categoryDto.getType());
        entity.setStatus(categoryDto.getStatus());
        return entity.getCode();
    }

    @Override
    @Transactional
    public String delete(String ccyCode) {
        CategoryEntity categoryEntity = categoryRepository.findByCode(ccyCode);
        if (categoryEntity == null) {
            throw new BusinessException(PublicError.BAD_REQUEST_DATA, Response.Status.NOT_FOUND, "Không tồn tại");
        }
        categoryRepository.delete(categoryEntity);
        return ccyCode;
    }

    @Override
    public CategoryDto detail(String ccyCode) {
        CategoryEntity categoryEntity = categoryRepository.findByCode(ccyCode);
        if (categoryEntity == null) {
            throw new BusinessException(PublicError.BAD_REQUEST_DATA, Response.Status.NOT_FOUND, "Không tồn tại");
        }
        return categoryMapper.toDto(categoryEntity);
    }

    @Override
    public List<CategoryDto> list() {
        List<CategoryEntity> currencyEntities = categoryRepository.listAll();
        UserDto userDto = new UserDto();
        userDto.setName("duy");
        Response response = testServiceFeignClient.searchUser(userDto);
        List<UserDto> users = response.readEntity(
                new GenericType<>() {
                }
        );
        return categoryMapper.toDtoList(currencyEntities);
    }

    @Override
    public PageResponse<CategoryDto> search(Integer pageSize, Integer pageNum, CategoryDto categoryDto) {
        return categoryRepository.search(categoryDto, pageNum, pageSize, "id", true);
    }
}
