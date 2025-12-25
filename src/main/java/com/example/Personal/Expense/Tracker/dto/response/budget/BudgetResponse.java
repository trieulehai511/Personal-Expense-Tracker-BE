package com.example.Personal.Expense.Tracker.dto.response.budget;

import lombok.*;
import lombok.experimental.FieldDefaults;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BudgetResponse {
    String id;
    BigDecimal amount;
    String categoryName; // Trả về tên danh mục cho dễ hiển thị
    LocalDate startDate;
    LocalDate endDate;
}