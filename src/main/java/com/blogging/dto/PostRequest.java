package com.blogging.dto;

import lombok.Data;
import java.util.List;

@Data
public class PostRequest {
    private String title;
    private String content;
    private String summary;
    private String status;
    private List<String> tags;
}