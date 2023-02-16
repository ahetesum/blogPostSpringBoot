package com.net.blog.dto;

import com.net.blog.entity.Comment;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Set;

@Data
public class PostDto {
    private long id;
    @NotEmpty
    @Size(min = 2,message = "Post title must be 2 character ")
    private String title;
    @NotEmpty
    @Size(min = 8,message = "Post description must be 8 character ")
    private String description;
    @NotEmpty
    private String content;
    private Set<CommentDto> comments;
}
