package com.example.Personal.Expense.Tracker.mapper;


import com.example.Personal.Expense.Tracker.dto.request.budget.BudgetCreationRequest;
import com.example.Personal.Expense.Tracker.dto.response.budget.BudgetResponse;
import com.example.Personal.Expense.Tracker.entity.Budget;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BudgetMapper {
    Budget toBudget(BudgetCreationRequest rq);

    @Mapping(target = "categoryName", source = "category.name")
    BudgetResponse toBudgetResponse(Budget budget);

}
