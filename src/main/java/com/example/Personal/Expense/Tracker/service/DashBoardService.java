package com.example.Personal.Expense.Tracker.service;

import com.example.Personal.Expense.Tracker.dto.response.dashboard.DashboardResponse;
import com.example.Personal.Expense.Tracker.entity.User;
import com.example.Personal.Expense.Tracker.enums.TransactionType;
import com.example.Personal.Expense.Tracker.repository.ExpenseRepository;
import com.example.Personal.Expense.Tracker.utils.SecurityUtils;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class DashBoardService {
    ExpenseRepository expenseRepository;
    SecurityUtils securityUtils;

    public DashboardResponse getAnalytics(LocalDate startDate, LocalDate endDate){
        User user = securityUtils.getCurrentUser();

        if(startDate == null || endDate == null){
            LocalDate now = LocalDate.now();
            startDate = now.with(TemporalAdjusters.firstDayOfMonth());
            endDate = now.with(TemporalAdjusters.lastDayOfMonth());
        }
        BigDecimal totalIncome = expenseRepository.sumByTypeAndDate(
                user, TransactionType.INCOME, startDate, endDate
        );
        if(totalIncome == null) totalIncome = BigDecimal.ZERO;

        BigDecimal totalExpense = expenseRepository.sumByTypeAndDate(
                user, TransactionType.EXPENSE, startDate, endDate
        );
        if(totalExpense == null) totalExpense = BigDecimal.ZERO;

        BigDecimal currentBalance = totalIncome.subtract(totalExpense);
        var categoryStats = expenseRepository.getCategoryStats(user, startDate, endDate);

        return DashboardResponse.builder()
                .totalIncome(totalIncome)
                .totalExpense(totalExpense)
                .currentBalance(currentBalance)
                .categoryStats(categoryStats)
                .build();
    }
}
