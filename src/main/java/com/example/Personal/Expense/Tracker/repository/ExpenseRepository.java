package com.example.Personal.Expense.Tracker.repository;


import com.example.Personal.Expense.Tracker.entity.Category;
import com.example.Personal.Expense.Tracker.entity.Expense;
import com.example.Personal.Expense.Tracker.entity.User;
import com.example.Personal.Expense.Tracker.enums.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import java.math.BigDecimal;
import java.time.LocalDate;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import com.example.Personal.Expense.Tracker.dto.response.dashboard.CategoryStatResponse;
@Repository
public interface ExpenseRepository extends JpaRepository<Expense,String>, JpaSpecificationExecutor<Expense> {
    List<Expense> findByUser(User user);

    boolean existsByCategory_Id(String categoryId);
    @Query("SELECT SUM(e.amount) FROM Expense e WHERE e.user = :user AND e.type = :type")
    BigDecimal sumByType(@Param("user") User user, @Param("type") TransactionType type);

    @Query("SELECT SUM(e.amount) FROM Expense e " +
            "WHERE e.user = :user AND e.date BETWEEN :startDate AND :endDate")
    BigDecimal calculateTotalSpent(@Param("user") User user,
                                   @Param("startDate") LocalDate startDate,
                                   @Param("endDate") LocalDate endDate);

    @Query("""

    SELECT SUM(e.amount) FROM Expense e 

    WHERE e.user = :user 

    AND (:category IS NULL OR e.category = :category) 

    AND e.date BETWEEN :startDate AND :endDate

""")

    BigDecimal calculateCategorySpent(

            @Param("user") User user,

            @Param("category") Category category,

            @Param("startDate") LocalDate startDate,

            @Param("endDate") LocalDate endDate

    );

    @Query("SELECT new com.example.Personal.Expense.Tracker.dto.response.dashboard.CategoryStatResponse(e.category.name, SUM(e.amount)) " +
            "FROM Expense e " +
            "WHERE e.user = :user AND e.date BETWEEN :startDate AND :endDate " +
            "GROUP BY e.category.name")
    List<CategoryStatResponse> getCategoryStats(@Param("user") User user,
                                                @Param("startDate") LocalDate startDate,
                                                @Param("endDate") LocalDate endDate);
    @Query("SELECT e FROM Expense e JOIN FETCH e.category JOIN FETCH e.user")
    List<Expense> findAllWithCategoryAndUser();

    @Query("""
    SELECT SUM(e.amount) FROM Expense e 
    WHERE e.user = :user 
    AND e.type = :type 
    AND e.date BETWEEN :startDate AND :endDate
""")
    BigDecimal sumByTypeAndDate(
            @Param("user") User user,
            @Param("type") TransactionType type,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate
    );

}
