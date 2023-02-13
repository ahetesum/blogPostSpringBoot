package com.net.blog.service;

import com.net.blog.dto.CommentDto;
import com.net.blog.dto.PostDto;
import com.net.blog.dto.PostResponse;

import java.util.List;

public interface CommentService {
    CommentDto createComment(long postId,CommentDto commentDto);

    List<CommentDto> getAllComment(long postId);
    CommentDto getById(long postId,long commentId);

    CommentDto update(long postId,long commentId, CommentDto commentDto);

    void delete(long postId,long commentId);
}

