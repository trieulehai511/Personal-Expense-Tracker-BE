package com.example.Personal.Expense.Tracker.service;


import com.example.Personal.Expense.Tracker.dto.request.category.CategoryRequest;
import com.example.Personal.Expense.Tracker.dto.response.category.CategoryResponse;
import com.example.Personal.Expense.Tracker.entity.Category;
import com.example.Personal.Expense.Tracker.exeption.AppException;
import com.example.Personal.Expense.Tracker.exeption.ErrorCode;
import com.example.Personal.Expense.Tracker.mapper.CategoryMapper;
import com.example.Personal.Expense.Tracker.repository.CategoryRepository;
import com.example.Personal.Expense.Tracker.repository.ExpenseRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class CategoryService {
    CategoryRepository categoryRepository;
    CategoryMapper categoryMapper;
    ExpenseRepository expenseRepository;

    public CategoryResponse create(CategoryRequest rq){
        var category = categoryMapper.toCategory(rq);
        return categoryMapper.toCategoryResponse(categoryRepository.save(category));
    }
    public List<CategoryResponse> getAll(){
        var categories = categoryRepository.findAll();
        return categories.stream().map(categoryMapper::toCategoryResponse).toList();
    }

    public CategoryResponse update(String id, CategoryRequest rq){
        Category category = categoryRepository.findById(id).orElseThrow(
                ()-> new AppException(ErrorCode.CATEGORY_NOT_EXISTED)
        );
        category.setName(rq.getName());
        category.setDescription(rq.getDescription());
        return categoryMapper.toCategoryResponse(categoryRepository.save(category));
    }

    public void delete(String id){
        if(!categoryRepository.existsById(id)){
            throw new AppException(ErrorCode.CATEGORY_NOT_EXISTED);
        }
        if (expenseRepository.existsByCategory_Id(id)) {
            throw new AppException(ErrorCode.CATEGORY_CANNOT_DELETE);
        }

        categoryRepository.deleteById(id);
    }

}
