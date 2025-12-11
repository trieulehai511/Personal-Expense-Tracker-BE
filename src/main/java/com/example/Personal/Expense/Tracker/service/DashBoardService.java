package com.example.Personal.Expense.Tracker.service;

import com.example.Personal.Expense.Tracker.dto.response.dashboard.DashboardResponse;
import com.example.Personal.Expense.Tracker.entity.User;
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
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DashBoardService {
    ExpenseRepository expenseRepository;
    SecurityUtils securityUtils;

    public DashboardResponse getAnalytics(LocalDate startDate, LocalDate endDate){
        User user = securityUtils.getCurrentUser();

        if(startDate == null ||  endDate == null){
            LocalDate now = LocalDate.now();
            startDate = now.with(TemporalAdjusters.firstDayOfMonth());
            endDate = now.with(TemporalAdjusters.lastDayOfMonth());
        }
        BigDecimal totalSpent = expenseRepository.calculateTotalSpent(user, startDate, endDate);
        var categoryStats = expenseRepository.getCategoryStats(user, startDate, endDate);
        if(totalSpent == null){
            totalSpent = BigDecimal.ZERO;
        }
        return DashboardResponse.builder().categoryStats(categoryStats).totalSpent(totalSpent).build();
    }
}
