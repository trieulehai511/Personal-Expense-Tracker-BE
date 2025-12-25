package com.example.Personal.Expense.Tracker.dto.request.recurring_trans;


import com.example.Personal.Expense.Tracker.enums.Frequency;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RecurringTransactionRequest {
    BigDecimal amount;
    String description;
    Frequency frequency; // DAILY, WEEKLY, MONTHLY...
    String categoryId;
    LocalDate nextExecutionDate; // Ngày bắt đầu chạy
}