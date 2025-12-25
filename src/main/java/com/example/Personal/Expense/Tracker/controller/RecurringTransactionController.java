package com.example.Personal.Expense.Tracker.controller;

import com.example.Personal.Expense.Tracker.dto.request.recurring_trans.RecurringTransactionRequest;
import com.example.Personal.Expense.Tracker.dto.response.utils.APIResponse;
import com.example.Personal.Expense.Tracker.dto.response.recurring_trans.RecurringTransactionResponse;
import com.example.Personal.Expense.Tracker.service.RecurringTransactionService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/recurring-transactions")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RecurringTransactionController {
    RecurringTransactionService recurringService;

    @PostMapping
    public APIResponse<RecurringTransactionResponse> create(@RequestBody RecurringTransactionRequest request) {
        return APIResponse.<RecurringTransactionResponse>builder()
                .result(recurringService.create(request))
                .build();
    }

    @GetMapping
    public APIResponse<List<RecurringTransactionResponse>> getMyRecurringTransactions() {
        return APIResponse.<List<RecurringTransactionResponse>>builder()
                .result(recurringService.getMyRecurringTransactions())
                .build();
    }

    @DeleteMapping("/{id}")
    public APIResponse<String> delete(@PathVariable String id) {
        recurringService.delete(id);
        return APIResponse.<String>builder()
                .result("Deleted successfully")
                .build();
    }
}