package com.example.Personal.Expense.Tracker.controller;


import com.example.Personal.Expense.Tracker.dto.request.budget.BudgetCreationRequest;
import com.example.Personal.Expense.Tracker.dto.response.budget.BudgetResponse;
import com.example.Personal.Expense.Tracker.dto.response.budget.BudgetStatResponse;
import com.example.Personal.Expense.Tracker.dto.response.utils.APIResponse;
import com.example.Personal.Expense.Tracker.entity.Budget;
import com.example.Personal.Expense.Tracker.service.BudgetService;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/budgets")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BudgetController {
    BudgetService budgetService;

    @PostMapping
    APIResponse<BudgetResponse> createBudget(@RequestBody BudgetCreationRequest rq) {
        return APIResponse.<BudgetResponse>builder().result(budgetService.createBudget(rq)).build();
    }

    @GetMapping
    APIResponse<List<BudgetResponse>> getMyBudgets(){
        return APIResponse.<List<BudgetResponse>>builder().result(budgetService.getMyBudget()).build();
    }
    @GetMapping("/stats")
    APIResponse<List<BudgetStatResponse>> getBudgetStats() {
        return APIResponse.<List<BudgetStatResponse>>builder()
                .result(budgetService.getBudgetStatistics())
                .build();
    }

}
