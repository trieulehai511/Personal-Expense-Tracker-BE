package com.example.Personal.Expense.Tracker.repository;


import com.example.Personal.Expense.Tracker.entity.Expense;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpenseRepository extends JpaRepository<Expense,String> {
}
