package com.example.Personal.Expense.Tracker.repository.specification;

import com.example.Personal.Expense.Tracker.entity.Expense;
import com.example.Personal.Expense.Tracker.entity.User;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ExpenseSpecification {

    public static Specification<Expense> filter(User user, LocalDate startDate, LocalDate endDate, String categoryId) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            // 1. BẮT BUỘC: Chỉ lấy expense của User đang đăng nhập
            predicates.add(criteriaBuilder.equal(root.get("user"), user));

            // 2. Filter theo ngày bắt đầu (nếu có)
            if (startDate != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("date"), startDate));
            }

            // 3. Filter theo ngày kết thúc (nếu có)
            if (endDate != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("date"), endDate));
            }

            // 4. Filter theo danh mục (nếu có)
            if (categoryId != null && !categoryId.isEmpty()) {
                predicates.add(criteriaBuilder.equal(root.get("category").get("id"), categoryId));
            }

            // 5. Sắp xếp: Ngày gần nhất lên đầu (DESC)
            query.orderBy(criteriaBuilder.desc(root.get("date")));

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}