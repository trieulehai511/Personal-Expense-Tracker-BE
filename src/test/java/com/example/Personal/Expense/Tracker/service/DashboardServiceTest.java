package com.example.Personal.Expense.Tracker.service;

import com.example.Personal.Expense.Tracker.dto.response.dashboard.CategoryStatResponse;
import com.example.Personal.Expense.Tracker.dto.response.dashboard.DashboardResponse;
import com.example.Personal.Expense.Tracker.entity.User;
import com.example.Personal.Expense.Tracker.repository.ExpenseRepository;
import com.example.Personal.Expense.Tracker.utils.SecurityUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class DashboardServiceTest {

    @Mock
    ExpenseRepository expenseRepository;

    @Mock
    SecurityUtils securityUtils;

    @InjectMocks
    DashBoardService dashboardService;

    // Dữ liệu giả để test
    User mockUser;
    LocalDate startDate;
    LocalDate endDate;

    @BeforeEach
    void setUp() {
        mockUser = User.builder().id("user-123").username("trieu").build();
        startDate = LocalDate.of(2023, 1, 1);
        endDate = LocalDate.of(2023, 1, 31);
    }

    @Test
    void getAnalytics_ShouldReturnCorrectData_WhenDataExists() {
        // 1. Giả lập hành vi (GIVEN)
        // Khi gọi getCurrentUser -> Trả về user giả
        Mockito.when(securityUtils.getCurrentUser()).thenReturn(mockUser);

        // Khi gọi tính tổng tiền -> Trả về 1 triệu
        Mockito.when(expenseRepository.calculateTotalSpent(Mockito.any(), Mockito.any(), Mockito.any()))
                .thenReturn(new BigDecimal("1000000"));

        // Khi gọi thống kê danh mục -> Trả về list giả
        Mockito.when(expenseRepository.getCategoryStats(Mockito.any(), Mockito.any(), Mockito.any()))
                .thenReturn(List.of(new CategoryStatResponse("Ăn uống", new BigDecimal("500000"))));

        // 2. Gọi hàm cần test (WHEN)
        DashboardResponse result = dashboardService.getAnalytics(startDate, endDate);

        // 3. Kiểm tra kết quả (THEN)
        Assertions.assertNotNull(result);
        Assertions.assertEquals(new BigDecimal("1000000"), result.getTotalSpent()); // Tổng tiền phải khớp
        Assertions.assertEquals(1, result.getCategoryStats().size()); // List phải có 1 phần tử
        Assertions.assertEquals("Ăn uống", result.getCategoryStats().get(0).getCategoryName());
    }
}