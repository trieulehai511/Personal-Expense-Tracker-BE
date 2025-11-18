package com.example.Personal.Expense.Tracker.repository;

import com.example.Personal.Expense.Tracker.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role,String> {
}
