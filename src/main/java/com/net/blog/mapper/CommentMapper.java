package com.net.blog.mapper;

import com.net.blog.dto.CommentDto;
import com.net.blog.entity.Comment;

public class CommentMapper {
    public static CommentDto mapToDto(Comment comment)
    {
        CommentDto commentDto=new CommentDto();
        commentDto.setId(comment.getId());
        commentDto.setName(comment.getName());
        commentDto.setEmail(comment.getEmail());
        commentDto.setBody(comment.getBody());
        return  commentDto;
    }

    public static Comment mapToEntity(CommentDto postDto)
    {
        Comment comment = new Comment();
        comment.setId(postDto.getId());
        comment.setName(postDto.getName());
        comment.setEmail(postDto.getEmail());
        comment.setBody(postDto.getBody());
        return  comment;
    }
}
