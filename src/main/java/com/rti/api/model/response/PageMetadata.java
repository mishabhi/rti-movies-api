package com.rti.api.model.response;


import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PageMetadata {
    private int numberOfElements;
    private long totalElements;
    private int currentPage;
    private int totalPages;
    private int pageSize;
    private List<String> sortOrders;
}
