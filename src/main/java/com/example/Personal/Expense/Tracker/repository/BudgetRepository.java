package com.example.Personal.Expense.Tracker.repository;

import com.example.Personal.Expense.Tracker.entity.Budget;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.Personal.Expense.Tracker.entity.Category;
import com.example.Personal.Expense.Tracker.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
@Repository
public interface BudgetRepository extends JpaRepository<Budget, String> {
    @Query("SELECT b FROM Budget b WHERE b.user = :user AND b.category = :category AND :date BETWEEN b.startDate AND b.endDate")
    Optional<Budget> findActiveBudget(@Param("user") User user, @Param("category") Category category, @Param("date") LocalDate date);

    List<Budget> findAllByUser(User user);
}
