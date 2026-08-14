package com.blogging.dto;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class PostResponse {
    private Long id;
    private String title;
    private String content;
    private String summary;
    private String status;
    private String authorUsername;
    private List<String> tags;
    private long commentCount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}