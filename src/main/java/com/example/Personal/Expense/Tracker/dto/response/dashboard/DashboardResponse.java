package com.example.Personal.Expense.Tracker.dto.response.dashboard;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DashboardResponse {
    BigDecimal totalIncome;     // Tổng thu
    BigDecimal totalExpense;    // Tổng chi (Sửa tên từ totalSpent -> totalExpense cho đồng bộ)
    BigDecimal currentBalance;
    List<CategoryStatResponse> categoryStats;
}
