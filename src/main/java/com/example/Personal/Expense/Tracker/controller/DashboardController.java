package com.example.Personal.Expense.Tracker.controller;


import com.example.Personal.Expense.Tracker.dto.response.dashboard.DashboardResponse;
import com.example.Personal.Expense.Tracker.dto.response.utils.APIResponse;
import com.example.Personal.Expense.Tracker.service.DashBoardService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

@Controller
@RequestMapping("/dashboard")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DashboardController {

    DashBoardService  dashBoardService;

    @GetMapping
    APIResponse<DashboardResponse> getDashboard(@RequestParam(required = false) LocalDate startDate,
                                                @RequestParam(required = false) LocalDate endDate) {
        return APIResponse.<DashboardResponse>builder().result(dashBoardService.getAnalytics(startDate,endDate)).build();
    }
}
