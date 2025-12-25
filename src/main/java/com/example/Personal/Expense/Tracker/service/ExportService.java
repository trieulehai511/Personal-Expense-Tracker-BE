package com.example.Personal.Expense.Tracker.service;

import com.example.Personal.Expense.Tracker.entity.Expense;
import com.example.Personal.Expense.Tracker.entity.User;
import com.example.Personal.Expense.Tracker.repository.ExpenseRepository;
import com.example.Personal.Expense.Tracker.utils.SecurityUtils;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ExportService {
    ExpenseRepository expenseRepository;
    SecurityUtils securityUtils;

    public ByteArrayInputStream exportExpensesToExcel() throws IOException {
        User user = securityUtils.getCurrentUser();
        // Tạm thời lấy tất cả, sau này có thể thêm filter ngày tháng
        List<Expense> expenses = expenseRepository.findByUser(user);

        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet("Chi Tiêu");

            // 1. Tạo Header (Dòng tiêu đề)
            Row headerRow = sheet.createRow(0);
            String[] columns = {"Ngày", "Danh Mục", "Số Tiền", "Loại", "Mô Tả"};

            // Style cho Header in đậm
            CellStyle headerStyle = workbook.createCellStyle();
            Font font = workbook.createFont();
            font.setBold(true);
            headerStyle.setFont(font);

            for (int i = 0; i < columns.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(columns[i]);
                cell.setCellStyle(headerStyle);
            }

            // 2. Đổ dữ liệu vào các dòng
            int rowIdx = 1;
            for (Expense expense : expenses) {
                Row row = sheet.createRow(rowIdx++);

                row.createCell(0).setCellValue(expense.getDate().toString());
                row.createCell(1).setCellValue(expense.getCategory().getName());
                row.createCell(2).setCellValue(expense.getAmount().doubleValue());
                row.createCell(3).setCellValue(expense.getType().toString());
                row.createCell(4).setCellValue(expense.getDescription());
            }

            // Tự động chỉnh độ rộng cột cho đẹp
            for(int i = 0; i < columns.length; i++) {
                sheet.autoSizeColumn(i);
            }

            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        }
    }
}