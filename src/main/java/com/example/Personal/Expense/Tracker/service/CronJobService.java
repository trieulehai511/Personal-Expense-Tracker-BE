package com.example.Personal.Expense.Tracker.service;

import com.example.Personal.Expense.Tracker.dto.request.expense.ExpenseCreationRequest;
import com.example.Personal.Expense.Tracker.entity.RecurringTransaction;
import com.example.Personal.Expense.Tracker.enums.Frequency;
import com.example.Personal.Expense.Tracker.enums.TransactionType;
import com.example.Personal.Expense.Tracker.repository.RecurringTransactionRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static com.example.Personal.Expense.Tracker.enums.Frequency.*;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class CronJobService {

    RecurringTransactionRepository recurringRepository;
    ExpenseService expenseService;

    @Scheduled(cron = "0 0 0 * * ?")
    @Transactional
    public void autoScanRecurringTransactions() {
        log.info("⏰ Bắt đầu quét các giao dịch định kỳ trong ngày...");

        LocalDate today = LocalDate.now();
        List<RecurringTransaction> dueTransactions = recurringRepository.findAllByNextExecutionDateLessThanEqual(today);

        log.info("Tìm thấy {} giao dịch cần xử lý.", dueTransactions.size());

        for (RecurringTransaction trans : dueTransactions) {
            try {

                ExpenseCreationRequest rq = new ExpenseCreationRequest();
                rq.setAmount(trans.getAmount());
                rq.setDescription(trans.getDescription() + " (Auto)");
                rq.setDate(today);
                rq.setType(TransactionType.EXPENSE);
                rq.setCategoryId(trans.getCategory().getId());

                expenseService.createSystemExpense(rq, trans.getUser());

                LocalDate nextDate = calculateNextDate(trans.getNextExecutionDate(), trans.getFrequency());
                trans.setNextExecutionDate(nextDate);

                recurringRepository.save(trans);

                log.info("✅ Đã xử lý xong cho User: {}", trans.getUser().getUsername());

            } catch (Exception e) {
                log.error("❌ Lỗi khi xử lý giao dịch ID {}: {}", trans.getId(), e.getMessage());
            }
        }
    }

    private LocalDate calculateNextDate(LocalDate currentDate, Frequency frequency) {
        if (currentDate == null) return LocalDate.now();

        return switch (frequency) {
            case DAILY -> currentDate.plusDays(1);
            case WEEKLY -> currentDate.plusWeeks(1);
            case MONTHLY -> currentDate.plusMonths(1);
            case YEARLY -> currentDate.plusYears(1);
            default -> throw new RuntimeException("Frequency not supported: " + frequency);
        };
    }
}