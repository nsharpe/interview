package org.amoeba.example.app.media.comment.controller.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.amoeba.example.comment.repository.CommentPostgres;

import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaginatedCommentsResponse {

    private List<CommentGet> comments;
    private int page;
    private int size;
    private long totalElements;

    public static PaginatedCommentsResponse of(List<CommentPostgres> commentList, int page, int size, long totalElements) {
        return PaginatedCommentsResponse.builder()
                .comments(commentList.stream().map(CommentGet::of).toList())
                .page(page)
                .size(size)
                .totalElements(totalElements)
                .build();
    }
}