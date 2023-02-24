package com.net.blog.controller;

import com.net.blog.dto.CategoryDto;
import com.net.blog.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public  ResponseEntity<List<CategoryDto>> getAll()
    {
        List<CategoryDto> categoryDtoList= categoryService.getAll();
        return  new ResponseEntity<>(categoryDtoList, HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CategoryDto> create(@RequestBody CategoryDto categoryDto)
    {
        CategoryDto createdCategoryDto= categoryService.addCategory(categoryDto);
        return  new ResponseEntity<>(createdCategoryDto, HttpStatus.CREATED);
    }


    @GetMapping("{id}")
    public  ResponseEntity<CategoryDto> getCategory(@PathVariable("id") long id)
    {
        CategoryDto categoryDto= categoryService.getCategory(id);
        return  new ResponseEntity<>(categoryDto, HttpStatus.OK);
    }

    @PutMapping("{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CategoryDto> create(@PathVariable("id") long id, @RequestBody CategoryDto categoryDto)
    {
        CategoryDto updatedCategoryDto= categoryService.updateCategory(id,categoryDto);
        return  new ResponseEntity<>(updatedCategoryDto, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> delete(@PathVariable("id") long id)
    {
        categoryService.deleteCategory(id);
        return  new ResponseEntity<>("Category Deleted Successfully", HttpStatus.OK);
    }
}
