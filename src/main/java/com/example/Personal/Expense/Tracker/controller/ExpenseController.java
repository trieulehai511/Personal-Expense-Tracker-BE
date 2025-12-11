package com.example.Personal.Expense.Tracker.controller;


import com.example.Personal.Expense.Tracker.dto.request.expense.ExpenseCreationRequest;
import com.example.Personal.Expense.Tracker.dto.request.expense.ExpenseUpdateRequest;
import com.example.Personal.Expense.Tracker.dto.response.expense.ExpenseResponse;
import com.example.Personal.Expense.Tracker.dto.response.utils.APIResponse;
import com.example.Personal.Expense.Tracker.dto.response.utils.PageResponse;
import com.example.Personal.Expense.Tracker.entity.Expense;
import com.example.Personal.Expense.Tracker.service.ExpenseService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Pageable;
import java.time.LocalDate;

@RestController
@RequestMapping("/expenses")
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ExpenseController {
    ExpenseService expenseService;

    @PostMapping
    APIResponse<ExpenseResponse> create(@RequestBody @Valid ExpenseCreationRequest rq) {
        return APIResponse.<ExpenseResponse>builder().result(expenseService.create(rq)).build();
    }

    @GetMapping

    APIResponse<PageResponse<ExpenseResponse>> myExpense(
            @RequestParam(required = false) LocalDate startDate,
            @RequestParam(required = false) LocalDate endDate,
            @RequestParam(required = false) String categoryId,
            Pageable pageable
    ) {
        var pageResult = expenseService.myListExpenses(startDate, endDate, categoryId, pageable);
        return APIResponse.<PageResponse<ExpenseResponse>>builder()
                .result(pageResult)
                .build();
    }

    @GetMapping("/{id}")
    APIResponse<ExpenseResponse> getExpenseById(@PathVariable("id") String id){
        return APIResponse.<ExpenseResponse>builder().result(expenseService.findById(id)).build();
    }

    @DeleteMapping("/{id}")
    APIResponse<String> delete(@PathVariable("id") String id) {
        expenseService.delete(id);
        return APIResponse.<String>builder()
                .result("Expense has been deleted")
                .build();
    }
    @PutMapping("/{id}")
    APIResponse<ExpenseResponse> update(@PathVariable("id") String id, @RequestBody ExpenseUpdateRequest rq) {
        return APIResponse.<ExpenseResponse>builder()
                .result(expenseService.updateExpense(id, rq))
                .build();
    }

}
