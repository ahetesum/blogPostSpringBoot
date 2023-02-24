package com.net.blog.service.impl;

import com.net.blog.dto.CategoryDto;
import com.net.blog.entity.Category;
import com.net.blog.entity.Post;
import com.net.blog.exception.ResourceNotFoundException;
import com.net.blog.repository.CategoryRepository;
import com.net.blog.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    private CategoryRepository categoryRepository;
    private ModelMapper modelMapper;

    public CategoryServiceImpl(CategoryRepository categoryRepository, ModelMapper modelMapper) {
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<CategoryDto> getAll() {
        List<Category> categories=categoryRepository.findAll();
        List<CategoryDto> categoryDtoList= categories.stream().map(c->modelMapper.map(c,CategoryDto.class)).collect(Collectors.toList());
        return categoryDtoList;
    }

    @Override
    public CategoryDto getCategory(long categoryId) {

        Category category=categoryRepository.findById(categoryId)
                .orElseThrow(()-> new ResourceNotFoundException("Category","id",categoryId));
        return modelMapper.map(category,CategoryDto.class);
    }

    @Override
    public CategoryDto addCategory(CategoryDto categoryDto) {

        Category category= modelMapper.map(categoryDto,Category.class);
        Category savedCategory=categoryRepository.save(category);
        return modelMapper.map(savedCategory,CategoryDto.class);
    }

    @Override
    public CategoryDto updateCategory(long categoryId, CategoryDto categoryDto) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(()->new ResourceNotFoundException("Category","id",categoryId));

        category.setName(category.getName());
        category.setDescription(categoryDto.getDescription());
        Category savedCategory=categoryRepository.save(category);
        return modelMapper.map(savedCategory,CategoryDto.class);
    }

    @Override
    public void deleteCategory(long categoryId) {
        Category category=categoryRepository.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category","id",categoryId));
        categoryRepository.delete(category);
    }
}
