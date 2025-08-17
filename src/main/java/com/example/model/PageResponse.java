package com.example.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class PageResponse<T> {
    private List<T> content;
    private int page;           // trang hiện tại (0-based hoặc 1-based tùy bạn)
    private int size;           // số phần tử mỗi trang
    private long totalElements; // tổng số phần tử
    private int totalPages;     // tổng số trang
    private boolean first;      // có phải trang đầu
    private boolean last;       // có phải trang cuối

    public static <T> PageResponse<T> of(List<T> content, int page, int size, long totalElements) {
        int totalPages = (int) Math.ceil((double) totalElements / size);
        return PageResponse.<T>builder()
                .content(content)
                .page(page)
                .size(size)
                .totalElements(totalElements)
                .totalPages(totalPages)
                .first(page == 0)
                .last(page >= totalPages - 1)
                .build();
    }
}
