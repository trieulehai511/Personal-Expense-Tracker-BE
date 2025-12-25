package com.example.Personal.Expense.Tracker.dto.request.expense;

import com.example.Personal.Expense.Tracker.entity.Category;
import com.example.Personal.Expense.Tracker.enums.TransactionType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDate;


@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ExpenseCreationRequest {

    @NotNull(message = "AMOUNT_REQUIRED")
    @Min(value = 0, message = "AMOUNT_INVALID")
    BigDecimal amount;
    LocalDate date;
    String description;
    String categoryId;

    TransactionType type;
}
