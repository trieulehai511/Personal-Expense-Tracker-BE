package com.example.Personal.Expense.Tracker.mapper;


import com.example.Personal.Expense.Tracker.dto.response.utils.PageResponse;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class PageMapper {
    public <E, D> PageResponse<D> toPageResponse(Page<E> page, Function<E, D> mapToDto) {
        return PageResponse.<D>builder()
                .currentPage(page.getNumber() + 1) // Logic +1 trang ở đây
                .totalPage(page.getTotalPages())
                .pageSize(page.getSize())
                .totalElements(page.getTotalElements())
                .data(page.getContent().stream().map(mapToDto).toList())
                .build();
    }
}
