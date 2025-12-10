package com.example.Personal.Expense.Tracker.mapper;


import com.example.Personal.Expense.Tracker.dto.request.category.CategoryRequest;
import com.example.Personal.Expense.Tracker.dto.response.category.CategoryResponse;
import com.example.Personal.Expense.Tracker.entity.Category;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    Category toCategory(CategoryRequest rq);

    CategoryResponse toCategoryResponse(Category c);
}
