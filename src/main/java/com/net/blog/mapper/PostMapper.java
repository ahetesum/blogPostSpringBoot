package com.net.blog.mapper;

import com.net.blog.dto.PostDto;
import com.net.blog.entity.Post;

public  class PostMapper {
    public static PostDto mapToDto(Post post)
    {
        PostDto postDto=new PostDto();
        postDto.setId(post.getId());
        postDto.setTitle(post.getTitle());
        postDto.setDescription(post.getDescription());
        postDto.setContent(post.getContent());
        return  postDto;
    }

    public static Post postMapEntity(PostDto postDto)
    {
        Post post = new Post();
        post.setId(postDto.getId());
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());
        return  post;
    }
}
