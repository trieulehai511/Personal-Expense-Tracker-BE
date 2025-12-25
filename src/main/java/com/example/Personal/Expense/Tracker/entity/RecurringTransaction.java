package com.example.Personal.Expense.Tracker.entity;

import com.example.Personal.Expense.Tracker.enums.Frequency;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RecurringTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    BigDecimal amount;
    String description;
    @Enumerated(EnumType.STRING)
    Frequency frequency;
    LocalDate nextExecutionDate;

    @ManyToOne
    User user;

    @ManyToOne
    Category category;
}
