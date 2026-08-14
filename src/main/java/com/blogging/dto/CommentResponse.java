package com.blogging.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class CommentResponse {
    private Long id;
    private String content;
    private String authorUsername;
    private Long postId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}