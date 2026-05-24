package com.example.helpdesk.init;

import com.example.helpdesk.category.model.Category;
import com.example.helpdesk.category.repository.CategoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final CategoryRepository categoryRepository;

    public DataInitializer(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void run(String... args) {
        createDefaultCategoryIfNotExists();
    }

    private void createDefaultCategoryIfNotExists() {
        String defaultCategoryName = "Brak kategorii";

        if (!categoryRepository.existsByName(defaultCategoryName)) {
            Category category = new Category();
            category.setName(defaultCategoryName);
            category.setDescription("Domyślna kategoria dla zgłoszeń, które nie pasują do istniejących kategorii.");

            categoryRepository.save(category);
        }
    }
}