package com.net.blog.service;

import com.net.blog.dto.PostDto;
import com.net.blog.dto.PostResponse;

import java.util.List;

public interface PostService {
    PostDto createPost(PostDto postDto);
    PostResponse getAllPost(int pageNo, int pageSize,String sortBy,String sortDir);
    PostDto getById(long id);
    PostDto update(long id,PostDto postDto);
    void delete(long id);

}
