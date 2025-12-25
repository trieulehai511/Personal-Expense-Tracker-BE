package com.example.Personal.Expense.Tracker.dto.response.recurring_trans;

import com.example.Personal.Expense.Tracker.entity.Category;
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
public class RecurringTransactionResponse {
    String id;
    BigDecimal amount;
    String description;
    Frequency frequency;
    LocalDate nextExecutionDate;
    Category category; // Trả về cả object category để hiển thị tên, icon
}