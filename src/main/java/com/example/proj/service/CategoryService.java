package com.example.proj.service;

import com.example.proj.entity.Category;
import com.example.proj.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public List<Category> getAll(){
        return categoryRepository.findAll();
    }
}
