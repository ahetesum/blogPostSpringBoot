package com.net.blog.service.impl;

import com.net.blog.dto.PostDto;
import com.net.blog.dto.PostResponse;
import com.net.blog.entity.Post;
import com.net.blog.exception.ResourceNotFoundException;
import com.net.blog.mapper.PostMapper;
import com.net.blog.repository.PostRepository;
import com.net.blog.service.PostService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.net.blog.mapper.PostMapper.mapToDto;
import static com.net.blog.mapper.PostMapper.postMapEntity;

@Service
public class PostServiceImpl implements PostService {
    private PostRepository postRepository;

    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public PostDto createPost(PostDto postDto) {

        Post post = postMapEntity(postDto);
        Post savedPost=postRepository.save(post);
        PostDto responsePost= mapToDto(savedPost);

        return responsePost;
    }

    @Override
    public PostResponse getAllPost(int pageNo, int pageSize,String sortBy,String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())?Sort.by(sortBy).ascending()
                :Sort.by(sortBy).descending();
        Pageable pageable=  PageRequest.of(pageNo,pageSize, sort);
        Page<Post> pageblePosts=postRepository.findAll(pageable);
        List<Post> posts= pageblePosts.getContent();
        PostResponse postResponse = new PostResponse();
        postResponse.setContent(posts.stream().map(p->mapToDto(p)).collect(Collectors.toList()));
        postResponse.setPageNo(pageblePosts.getNumber());
        postResponse.setPageSize(pageblePosts.getSize());
        postResponse.setTotalElement(pageblePosts.getTotalElements());
        postResponse.setTotalPages(pageblePosts.getTotalPages());
        postResponse.setLast(pageblePosts.isLast());
        return  postResponse;
    }

    @Override
    public PostDto getById(long id) {
        Post post=postRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Post","id",id));
        return mapToDto(post);
    }

    @Override
    public PostDto update(long id, PostDto postDto) {
        Post post=postRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Post","id",id));

        post.setContent(postDto.getContent());
        post.setDescription(postDto.getDescription());
        post.setTitle(postDto.getTitle());
        Post updatedPost=  postRepository.save(post);

        return mapToDto(updatedPost);
    }

    @Override
    public void  delete(long id)
    {
        Post post=postRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Post","id",id));
        postRepository.delete(post);

    }

}
