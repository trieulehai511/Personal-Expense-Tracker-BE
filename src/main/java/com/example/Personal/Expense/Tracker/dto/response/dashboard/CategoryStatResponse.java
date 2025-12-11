package com.example.Personal.Expense.Tracker.dto.response.dashboard;


import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CategoryStatResponse {
    String categoryName;
    BigDecimal amount;
}
