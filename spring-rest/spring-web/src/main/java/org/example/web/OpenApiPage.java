package org.example.web;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.domain.Pageable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
public class OpenApiPage {
    private PageableDTO pageable;
    private int totalElements;
    private int totalPages;
    private int number;
    private int size;

    private int numberOfElements;
    private boolean first;
    private boolean last;
    private boolean empty;

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    @Builder
    public static class SortDTO {
        public boolean empty;
        public boolean sorted;
        public boolean unsorted;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    @Builder
    public static class PageableDTO {
        public int pageNumber;
        public int pageSize;
        public SortDTO sort;
    }
}
