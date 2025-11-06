package com.example.Personal.Expense.Tracker.repository;

import com.example.Personal.Expense.Tracker.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionRepository extends JpaRepository<Permission,String> {
}
