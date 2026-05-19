package com.example.helpdesk.category.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CategoryRequest {

    @NotBlank(message = "Nazwa kategorii jest wymagana")
    @Size(min = 3, max = 50, message = "Nazwa kategorii musi mieć od 3 do 50 znaków")
    private String name;

    @Size(max = 500, message = "Opis kategorii może mieć maksymalnie 500 znaków")
    private String description;

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}