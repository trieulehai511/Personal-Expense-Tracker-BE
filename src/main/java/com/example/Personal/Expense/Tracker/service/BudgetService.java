package com.example.Personal.Expense.Tracker.service;


import com.example.Personal.Expense.Tracker.dto.request.budget.BudgetCreationRequest;
import com.example.Personal.Expense.Tracker.dto.response.budget.BudgetResponse;
import com.example.Personal.Expense.Tracker.dto.response.budget.BudgetStatResponse;
import com.example.Personal.Expense.Tracker.entity.Budget;
import com.example.Personal.Expense.Tracker.entity.Category;
import com.example.Personal.Expense.Tracker.entity.User;
import com.example.Personal.Expense.Tracker.exeption.AppException;
import com.example.Personal.Expense.Tracker.exeption.ErrorCode;
import com.example.Personal.Expense.Tracker.mapper.BudgetMapper;
import com.example.Personal.Expense.Tracker.repository.BudgetRepository;
import com.example.Personal.Expense.Tracker.repository.CategoryRepository;
import com.example.Personal.Expense.Tracker.repository.ExpenseRepository;
import com.example.Personal.Expense.Tracker.utils.SecurityUtils;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BudgetService {
    BudgetRepository budgetRepository;
    CategoryRepository categoryRepository;
    SecurityUtils  securityUtils;
    BudgetMapper budgetMapper;
    ExpenseRepository expenseRepository;

    public BudgetResponse createBudget(BudgetCreationRequest rq) {
        if (rq.getStartDate().isAfter(rq.getEndDate())) {
            throw new AppException(ErrorCode.INVALID_DATE_RANGE);
        }
        User user = securityUtils.getCurrentUser();
        Budget budget = budgetMapper.toBudget(rq);
        budget.setUser(user);

        if(rq.getCategoryId()!=null && !rq.getCategoryId().isEmpty()){
            Category category = categoryRepository.findById(rq.getCategoryId()).orElseThrow(()-> new AppException(ErrorCode.CATEGORY_NOT_EXISTED));
            budget.setCategory(category);
        }
        return budgetMapper.toBudgetResponse(budgetRepository.save(budget));
    }
    public List<BudgetResponse> getMyBudget() {
        User user = securityUtils.getCurrentUser();
        return budgetRepository.findAll().stream().filter(b -> b.getUser().getId().equals(user.getId()))
                .map(budgetMapper::toBudgetResponse)
                .toList();
    }
    public List<BudgetStatResponse> getBudgetStatistics() {
        User user = securityUtils.getCurrentUser();
        List<Budget> budgets = budgetRepository.findAllByUser(user);
        List<BudgetStatResponse> stats = new ArrayList<>();

        for (Budget budget : budgets) {
            BigDecimal totalSpent = expenseRepository.calculateCategorySpent(user,budget.getCategory(),budget.getStartDate(), budget.getEndDate());
            if(totalSpent == null){
                totalSpent = BigDecimal.ZERO;
            }
            BigDecimal limit = budget.getAmount();
            BigDecimal remaining = limit.subtract(totalSpent);
            double percentage = 0;
            if (limit.compareTo(BigDecimal.ZERO) > 0) {
                percentage = totalSpent.divide(limit, 4, RoundingMode.HALF_UP).doubleValue() * 100;
            }
            stats.add(BudgetStatResponse.builder()
                    .id(budget.getId())
                    .categoryName(budget.getCategory() != null ? budget.getCategory().getName() : "Tất cả")
                    .limitAmount(limit)
                    .spentAmount(totalSpent)
                    .remainingAmount(remaining)
                    .progressPercentage(percentage)
                    .isExceeded(remaining.compareTo(BigDecimal.ZERO) < 0) // Nếu còn lại < 0 là lố
                    .startDate(budget.getStartDate())
                    .endDate(budget.getEndDate())
                    .build());

        }
        return stats;
    }
}
