package com.net.blog.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CommentDto {
    private long id;
    @NotEmpty
    @Size(min = 2,message = "Comment name must be 2 character ")
    private String name;
    @NotEmpty(message = "Email must not be empty")
    @Email
    private String email;
    @NotEmpty
    @Size(min = 8,message = "Comment body must be 8 character ")
    private String body;
}
