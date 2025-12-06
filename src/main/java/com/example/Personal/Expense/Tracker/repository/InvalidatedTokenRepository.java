package com.example.Personal.Expense.Tracker.repository;

import com.example.Personal.Expense.Tracker.entity.InvalidatedToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvalidatedTokenRepository extends JpaRepository<InvalidatedToken, String> {
}
