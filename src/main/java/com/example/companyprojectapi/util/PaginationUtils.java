package com.example.companyprojectapi.util;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import com.example.companyprojectapi.dto.PageResponse;

public final class PaginationUtils {

    private PaginationUtils() {
    }

    public static Pageable buildPageable(Integer page, Integer size, String sort, Sort defaultSort) {
        int pageNumber = (page == null || page < 0) ? 0 : page;
        int pageSize = (size == null || size <= 0) ? 20 : size;
        Sort sortValue = defaultSort == null ? Sort.by("id").ascending() : defaultSort;

        if (StringUtils.hasText(sort)) {
            List<Sort.Order> orders = new ArrayList<>();
            String[] segments = sort.split(";");
            for (String segment : segments) {
                String[] tokens = segment.split(",");
                String property = tokens[0].trim();
                if (!StringUtils.hasText(property)) {
                    continue;
                }
                Sort.Direction direction = (tokens.length > 1 && "desc".equalsIgnoreCase(tokens[1].trim()))
                        ? Sort.Direction.DESC
                        : Sort.Direction.ASC;
                orders.add(new Sort.Order(direction, property));
            }
            if (!orders.isEmpty()) {
                sortValue = Sort.by(orders);
            }
        }
        return PageRequest.of(pageNumber, pageSize, sortValue);
    }

    public static <T, R> PageResponse<R> toPageResponse(Page<T> page, Function<T, R> mapper) {
        List<R> content = page.stream().map(mapper).toList();
        return new PageResponse<>(
                content,
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.isLast()
        );
    }
}
