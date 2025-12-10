package com.example.Personal.Expense.Tracker.repository;

import com.example.Personal.Expense.Tracker.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category,String> {
}
