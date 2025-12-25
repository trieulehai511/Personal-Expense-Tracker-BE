package com.example.Personal.Expense.Tracker.dto.request.budget;

import lombok.*;
import lombok.experimental.FieldDefaults;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BudgetCreationRequest {
    BigDecimal amount;       // Số tiền giới hạn (VD: 5 triệu)
    String categoryId;       // ID danh mục (Có thể null nếu đặt ngân sách chung)
    LocalDate startDate;     // Ngày bắt đầu
    LocalDate endDate;
}
