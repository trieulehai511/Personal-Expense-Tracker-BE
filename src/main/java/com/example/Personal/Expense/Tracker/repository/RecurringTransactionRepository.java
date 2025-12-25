package com.example.Personal.Expense.Tracker.repository;

import com.example.Personal.Expense.Tracker.entity.RecurringTransaction;
import com.example.Personal.Expense.Tracker.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface RecurringTransactionRepository extends JpaRepository<RecurringTransaction, String> {


    List<RecurringTransaction> findAllByNextExecutionDateBefore(LocalDate date);
    List<RecurringTransaction> findAllByUser(User user);
    List<RecurringTransaction> findAllByNextExecutionDateLessThanEqual(LocalDate date);
}