package com.example.Personal.Expense.Tracker.service;

import com.example.Personal.Expense.Tracker.dto.request.expense.ExpenseCreationRequest;
import com.example.Personal.Expense.Tracker.dto.request.expense.ExpenseUpdateRequest;
import com.example.Personal.Expense.Tracker.dto.response.expense.ExpenseResponse;
import com.example.Personal.Expense.Tracker.dto.response.utils.PageResponse;
import com.example.Personal.Expense.Tracker.entity.Budget;
import com.example.Personal.Expense.Tracker.entity.Category;
import com.example.Personal.Expense.Tracker.entity.Expense;
import com.example.Personal.Expense.Tracker.entity.User;
import com.example.Personal.Expense.Tracker.exeption.AppException;
import com.example.Personal.Expense.Tracker.exeption.ErrorCode;
import com.example.Personal.Expense.Tracker.mapper.ExpenseMapper;
import com.example.Personal.Expense.Tracker.mapper.PageMapper;
import com.example.Personal.Expense.Tracker.repository.BudgetRepository;
import com.example.Personal.Expense.Tracker.repository.CategoryRepository;
import com.example.Personal.Expense.Tracker.repository.ExpenseRepository;
import com.example.Personal.Expense.Tracker.repository.specification.ExpenseSpecification;
import com.example.Personal.Expense.Tracker.utils.SecurityUtils;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cglib.core.Local;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ExpenseService {
    ExpenseRepository expenseRepository;
    ExpenseMapper expenseMapper;
    SecurityUtils securityUtils;
    CategoryRepository categoryRepository;
    PageMapper pageMapper;
    BudgetRepository budgetRepository;

    public ExpenseResponse create(ExpenseCreationRequest rq){
        User user =  securityUtils.getCurrentUser();
        Category category = categoryRepository.findById(rq.getCategoryId())
                .orElseThrow(()-> new AppException(ErrorCode.CATEGORY_NOT_EXISTED));
        LocalDate now = LocalDate.now();
        LocalDate startDate = now.withDayOfMonth(1);
        LocalDate endDate = now.withDayOfMonth(now.lengthOfMonth());
        BigDecimal totalSpent = expenseRepository.calculateCategorySpent(user,category, startDate, endDate);
        if (totalSpent == null) totalSpent = BigDecimal.ZERO;

        Optional<Budget> budgetOpt = budgetRepository.findActiveBudget(user, category, now);
        if(budgetOpt.isPresent()){
            Budget budget = budgetOpt.get();
            BigDecimal newTotal = totalSpent.add(rq.getAmount());
            if(newTotal.compareTo(budget.getAmount()) > 0){
                log.warn("Bạn đã chi tiêu vượt hạn mức danh mục {}!!!", category.getName());
            }
        }

        Expense expense =  expenseMapper.toExpense(rq);
        expense.setUser(user);
        expense.setCategory(category);
        return expenseMapper.toExpenseResponse(expenseRepository.save(expense));
    }

    public List<ExpenseResponse> findAll(){
        var expenses = expenseRepository.findAllWithCategoryAndUser();
        return expenses.stream().map(expenseMapper::toExpenseResponse).toList();
    }

    public PageResponse<ExpenseResponse> myListExpenses(LocalDate startDate, LocalDate endDate, String categoryId, Pageable pageable){
        User user = securityUtils.getCurrentUser();
        var spec = ExpenseSpecification.filter(user, startDate, endDate, categoryId);
        Page<Expense> pageData = expenseRepository.findAll(spec, pageable);
        return pageMapper.toPageResponse(pageData, expenseMapper::toExpenseResponse);
    }

    @PostAuthorize("returnObject.user.username == authentication.name or hasRole('ADMIN')")
    public ExpenseResponse findById(String id){
        Expense expense = expenseRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.EXPENSE_NOT_EXISTED));
        return expenseMapper.toExpenseResponse(expense);
    }

    public void delete(String expenseId) {
        Expense expense = expenseRepository.findById(expenseId)
                .orElseThrow(() -> new AppException(ErrorCode.EXPENSE_NOT_EXISTED));
        User user = securityUtils.getCurrentUser();
        if (!expense.getUser().getId().equals(user.getId())) {
            throw new AppException(ErrorCode.UNAUTHORIZED);
        }
        expenseRepository.delete(expense);
    }

    public ExpenseResponse updateExpense(String expenseId, ExpenseUpdateRequest expenseUpdateRequest){
        Expense expense = expenseRepository.findById(expenseId).orElseThrow(() -> new AppException(ErrorCode.EXPENSE_NOT_EXISTED));
        User user = securityUtils.getCurrentUser();
        if(!user.getId().equals(expense.getUser().getId())){
            throw new AppException(ErrorCode.UNAUTHORIZED);
        }
        expenseMapper.updateExpense(expense, expenseUpdateRequest);
        if (expenseUpdateRequest.getCategoryId() != null) {
            Category category = categoryRepository.findById(expenseUpdateRequest.getCategoryId())
                    .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_EXISTED));
            expense.setCategory(category);
        }
        return expenseMapper.toExpenseResponse(expenseRepository.save(expense));
    }
}