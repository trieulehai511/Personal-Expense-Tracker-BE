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
    BigDecimal totalSpent;
    List<CategoryStatResponse> categoryStats;
}
