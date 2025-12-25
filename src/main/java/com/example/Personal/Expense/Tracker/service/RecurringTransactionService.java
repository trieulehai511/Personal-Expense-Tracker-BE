package com.example.Personal.Expense.Tracker.service;

import com.example.Personal.Expense.Tracker.dto.request.recurring_trans.RecurringTransactionRequest;
import com.example.Personal.Expense.Tracker.dto.response.recurring_trans.RecurringTransactionResponse;
import com.example.Personal.Expense.Tracker.entity.Category;
import com.example.Personal.Expense.Tracker.entity.RecurringTransaction;
import com.example.Personal.Expense.Tracker.entity.User;
import com.example.Personal.Expense.Tracker.exeption.AppException;
import com.example.Personal.Expense.Tracker.exeption.ErrorCode;
import com.example.Personal.Expense.Tracker.repository.CategoryRepository;
import com.example.Personal.Expense.Tracker.repository.RecurringTransactionRepository;
import com.example.Personal.Expense.Tracker.utils.SecurityUtils;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RecurringTransactionService {
    RecurringTransactionRepository recurringRepository;
    CategoryRepository categoryRepository;
    SecurityUtils securityUtils;

    // 1. Tạo quy tắc mới
    public RecurringTransactionResponse create(RecurringTransactionRequest request) {
        User user = securityUtils.getCurrentUser();

        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_EXISTED));

        RecurringTransaction transaction = RecurringTransaction.builder()
                .amount(request.getAmount())
                .description(request.getDescription())
                .frequency(request.getFrequency())
                .nextExecutionDate(request.getNextExecutionDate())
                .user(user)
                .category(category)
                .build();

        return toResponse(recurringRepository.save(transaction));
    }

    // 2. Lấy danh sách của tôi
    public List<RecurringTransactionResponse> getMyRecurringTransactions() {
        User user = securityUtils.getCurrentUser();
        // Cần thêm hàm findByUser trong Repository nhé (bước dưới mình nhắc)
        return recurringRepository.findAllByUser(user).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    // 3. Xóa quy tắc
    public void delete(String id) {
        User user = securityUtils.getCurrentUser();
        RecurringTransaction transaction = recurringRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transaction not found"));

        if (!transaction.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("You do not have permission");
        }

        recurringRepository.delete(transaction);
    }

    private RecurringTransactionResponse toResponse(RecurringTransaction entity) {
        return RecurringTransactionResponse.builder()
                .id(entity.getId())
                .amount(entity.getAmount())
                .description(entity.getDescription())
                .frequency(entity.getFrequency())
                .nextExecutionDate(entity.getNextExecutionDate())
                .category(entity.getCategory())
                .build();
    }
}