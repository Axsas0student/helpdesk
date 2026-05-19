package com.example.helpdesk.category.mapper;

import com.example.helpdesk.category.dto.CategoryRequest;
import com.example.helpdesk.category.dto.CategoryResponse;
import com.example.helpdesk.category.model.Category;

public class CategoryMapper {

    public static Category toEntity(CategoryRequest request) {
        Category category = new Category();
        category.setName(request.getName());
        category.setDescription(request.getDescription());
        return category;
    }

    public static CategoryResponse toResponse(Category category) {
        return new CategoryResponse(
                category.getId(),
                category.getName(),
                category.getDescription()
        );
    }
}