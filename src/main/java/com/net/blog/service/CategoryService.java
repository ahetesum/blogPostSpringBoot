package com.net.blog.service;

import com.net.blog.dto.CategoryDto;

import java.util.List;

public interface CategoryService {

    List<CategoryDto> getAll();
    CategoryDto addCategory(CategoryDto categoryDto);
    CategoryDto getCategory(long categoryId);

    CategoryDto updateCategory(long categoryId,CategoryDto categoryDto);

    void deleteCategory(long categoryId);


}
