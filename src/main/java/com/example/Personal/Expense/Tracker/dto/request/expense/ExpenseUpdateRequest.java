package com.example.Personal.Expense.Tracker.dto.request.expense;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ExpenseUpdateRequest {
    BigDecimal amount;
    LocalDate date;
    String description;
    String categoryId;
}
