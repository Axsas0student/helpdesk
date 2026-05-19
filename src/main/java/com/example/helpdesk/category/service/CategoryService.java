package com.example.helpdesk.category.service;

import com.example.helpdesk.category.dto.CategoryRequest;
import com.example.helpdesk.category.dto.CategoryResponse;
import com.example.helpdesk.category.mapper.CategoryMapper;
import com.example.helpdesk.category.model.Category;
import com.example.helpdesk.category.repository.CategoryRepository;
import com.example.helpdesk.exception.CategoryAlreadyExistsException;
import com.example.helpdesk.exception.CategoryNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<CategoryResponse> getAllCategories() {
        return categoryRepository.findAll()
                .stream()
                .map(CategoryMapper::toResponse)
                .toList();
    }

    public CategoryResponse getCategoryById(Long id) {
        Category category = findCategoryById(id);
        return CategoryMapper.toResponse(category);
    }

    public CategoryResponse createCategory(CategoryRequest request) {
        if (categoryRepository.existsByName(request.getName())) {
            throw new CategoryAlreadyExistsException(request.getName());
        }

        Category category = CategoryMapper.toEntity(request);
        Category savedCategory = categoryRepository.save(category);

        return CategoryMapper.toResponse(savedCategory);
    }

    public CategoryResponse updateCategory(Long id, CategoryRequest request) {
        Category existingCategory = findCategoryById(id);

        if (!existingCategory.getName().equals(request.getName())
                && categoryRepository.existsByName(request.getName())) {
            throw new CategoryAlreadyExistsException(request.getName());
        }

        existingCategory.setName(request.getName());
        existingCategory.setDescription(request.getDescription());

        Category savedCategory = categoryRepository.save(existingCategory);
        return CategoryMapper.toResponse(savedCategory);
    }

    public void deleteCategory(Long id) {
        Category existingCategory = findCategoryById(id);
        categoryRepository.delete(existingCategory);
    }

    public Category findCategoryById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException(id));
    }
}