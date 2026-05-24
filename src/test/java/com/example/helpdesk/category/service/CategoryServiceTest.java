package com.example.helpdesk.category.service;

import com.example.helpdesk.category.dto.CategoryRequest;
import com.example.helpdesk.category.dto.CategoryResponse;
import com.example.helpdesk.category.model.Category;
import com.example.helpdesk.category.repository.CategoryRepository;
import com.example.helpdesk.exception.CategoryAlreadyExistsException;
import com.example.helpdesk.exception.CategoryNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryService categoryService;

    @Test
    void shouldCreateCategory() {
        CategoryRequest request = new CategoryRequest();
        request.setName("Logowanie");
        request.setDescription("Problemy z logowaniem");

        Category savedCategory = new Category();
        savedCategory.setId(1L);
        savedCategory.setName("Logowanie");
        savedCategory.setDescription("Problemy z logowaniem");

        when(categoryRepository.existsByName("Logowanie")).thenReturn(false);
        when(categoryRepository.save(any(Category.class))).thenReturn(savedCategory);

        CategoryResponse response = categoryService.createCategory(request);

        assertEquals(1L, response.getId());
        assertEquals("Logowanie", response.getName());
        assertEquals("Problemy z logowaniem", response.getDescription());

        verify(categoryRepository).existsByName("Logowanie");
        verify(categoryRepository).save(any(Category.class));
    }

    @Test
    void shouldThrowExceptionWhenCategoryNameAlreadyExists() {
        CategoryRequest request = new CategoryRequest();
        request.setName("Logowanie");
        request.setDescription("Problemy z logowaniem");

        when(categoryRepository.existsByName("Logowanie")).thenReturn(true);

        assertThrows(CategoryAlreadyExistsException.class,
                () -> categoryService.createCategory(request));

        verify(categoryRepository).existsByName("Logowanie");
        verify(categoryRepository, never()).save(any(Category.class));
    }

    @Test
    void shouldGetCategoryById() {
        Category category = new Category();
        category.setId(1L);
        category.setName("Sieć");
        category.setDescription("Problemy z siecią");

        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));

        CategoryResponse response = categoryService.getCategoryById(1L);

        assertEquals(1L, response.getId());
        assertEquals("Sieć", response.getName());
        assertEquals("Problemy z siecią", response.getDescription());

        verify(categoryRepository).findById(1L);
    }

    @Test
    void shouldThrowExceptionWhenCategoryNotFound() {
        when(categoryRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(CategoryNotFoundException.class,
                () -> categoryService.getCategoryById(999L));

        verify(categoryRepository).findById(999L);
    }

    @Test
    void shouldGetAllCategories() {
        Category firstCategory = new Category();
        firstCategory.setId(1L);
        firstCategory.setName("Logowanie");
        firstCategory.setDescription("Problemy z logowaniem");

        Category secondCategory = new Category();
        secondCategory.setId(2L);
        secondCategory.setName("Sprzęt");
        secondCategory.setDescription("Problemy ze sprzętem");

        when(categoryRepository.findAll()).thenReturn(List.of(firstCategory, secondCategory));

        List<CategoryResponse> responses = categoryService.getAllCategories();

        assertEquals(2, responses.size());
        assertEquals("Logowanie", responses.get(0).getName());
        assertEquals("Sprzęt", responses.get(1).getName());

        verify(categoryRepository).findAll();
    }

    @Test
    void shouldDeleteCategory() {
        Category category = new Category();
        category.setId(1L);
        category.setName("Logowanie");
        category.setDescription("Problemy z logowaniem");

        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));

        categoryService.deleteCategory(1L);

        verify(categoryRepository).findById(1L);
        verify(categoryRepository).delete(category);
    }
}