package com.example.Personal.Expense.Tracker.dto.response.expense;
import com.example.Personal.Expense.Tracker.dto.response.category.CategoryResponse;
import com.example.Personal.Expense.Tracker.entity.Category;
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
public class ExpenseResponse {

    String id;
    BigDecimal amount;
    LocalDate date;
    String description;
    CategoryResponse category;
}
