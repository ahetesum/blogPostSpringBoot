package com.net.blog.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CategoryDto {
    private long id;
    private String name;
    private String description;
}
