package com.example.Personal.Expense.Tracker.dto.response.budget;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDate;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BudgetStatResponse {
    String id;
    String categoryName;
    BigDecimal limitAmount;
    BigDecimal spentAmount;
    BigDecimal remainingAmount;

    double progressPercentage;
    boolean isExceeded;
    LocalDate startDate;
    LocalDate endDate;
}
