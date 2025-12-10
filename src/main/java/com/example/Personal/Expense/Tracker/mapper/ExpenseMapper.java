package com.example.Personal.Expense.Tracker.mapper;

import com.example.Personal.Expense.Tracker.dto.request.expense.ExpenseCreationRequest;
import com.example.Personal.Expense.Tracker.dto.request.expense.ExpenseUpdateRequest;
import com.example.Personal.Expense.Tracker.dto.response.expense.ExpenseResponse;
import com.example.Personal.Expense.Tracker.entity.Expense;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ExpenseMapper {
    @Mapping(target = "category", ignore = true)
    @Mapping(target = "user", ignore = true)
    Expense toExpense(ExpenseCreationRequest request);
    ExpenseResponse toExpenseResponse(Expense expense);

    @Mapping(target = "category", ignore = true)
    @Mapping(target = "user", ignore = true)
    void updateExpense(@MappingTarget Expense expense, ExpenseUpdateRequest request);
}
