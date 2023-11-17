package com.portal.consumer.core.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PageObject<T> {
    private List<T> data;
    private long totalPages;
    private int currentPage;
}
