package com.example.Personal.Expense.Tracker.entity;


import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
@Getter
@Table(name = "expense", indexes = {
        @Index(name = "idx_expense_date", columnList = "date"),
        @Index(name = "idx_expense_category", columnList = "category_id")
})
public class Expense {

    @Id
    @GeneratedValue(strategy= GenerationType.UUID)
    String id;

    BigDecimal amount;
    LocalDate date;
    String description;

    @ManyToOne
    @JoinColumn(name = "category_id")
    Category category;

    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;

}
