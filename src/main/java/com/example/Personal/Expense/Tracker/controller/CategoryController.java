package com.example.Personal.Expense.Tracker.controller;


import com.example.Personal.Expense.Tracker.dto.request.category.CategoryRequest;
import com.example.Personal.Expense.Tracker.dto.response.category.CategoryResponse;
import com.example.Personal.Expense.Tracker.dto.response.utils.APIResponse;
import com.example.Personal.Expense.Tracker.repository.CategoryRepository;
import com.example.Personal.Expense.Tracker.service.CategoryService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class CategoryController {


    CategoryService categoryService;

    @PostMapping
    public APIResponse<CategoryResponse> create(@RequestBody CategoryRequest rq){
        return APIResponse.<CategoryResponse>builder().result(categoryService.create(rq)).build();
    }
    @GetMapping
    public APIResponse<List<CategoryResponse>> findAll(){
        return APIResponse.<List<CategoryResponse>>builder().result(categoryService.getAll()).build();
    }
}
