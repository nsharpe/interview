package org.example.web;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Schema(name = "PageResponse")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class PageModel<T> {
    public List<T> content;
    public long totalElements;
    public int totalPages;
    public int size;
    public int number;
}