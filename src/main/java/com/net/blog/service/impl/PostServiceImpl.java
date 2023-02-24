package com.net.blog.service.impl;

import com.net.blog.dto.PostDto;
import com.net.blog.dto.PostResponse;
import com.net.blog.entity.Category;
import com.net.blog.entity.Post;
import com.net.blog.exception.ResourceNotFoundException;
import com.net.blog.repository.CategoryRepository;
import com.net.blog.repository.PostRepository;
import com.net.blog.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;



@Service
public class PostServiceImpl implements PostService {
    private PostRepository postRepository;
    private ModelMapper modelMapper;

    private CategoryRepository categoryRepository;
    public PostServiceImpl(PostRepository postRepository, CategoryRepository categoryRepository,ModelMapper modelMapper) {
        this.postRepository = postRepository;
        this.categoryRepository=categoryRepository;
        this.modelMapper=modelMapper;
    }

    @Override
    public PostDto createPost(PostDto postDto) {

        Category category = categoryRepository.findById(postDto.getCategoryId())
                .orElseThrow(()->new ResourceNotFoundException("Category","id", postDto.getCategoryId()));

        Post post = mapToEntity(postDto);
        Post savedPost=postRepository.save(post);
        post.setCategory(category);
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

        Category category = categoryRepository.findById(postDto.getCategoryId())
                .orElseThrow(()->new ResourceNotFoundException("Category","id", postDto.getCategoryId()));

        post.setContent(postDto.getContent());
        post.setDescription(postDto.getDescription());
        post.setTitle(postDto.getTitle());
        post.setCategory(category);
        Post updatedPost=  postRepository.save(post);

        return mapToDto(updatedPost);
    }

    @Override
    public List<PostDto> getPostByCategory(long categoryId) {

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(()->new ResourceNotFoundException("Category","id",categoryId));

        List<Post> posts= postRepository.findByCategoryId(categoryId);

        return posts.stream().map(post -> modelMapper.map(post,PostDto.class)).collect(Collectors.toList());
    }

    @Override
    public void  delete(long id)
    {
        Post post=postRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Post","id",id));
        postRepository.delete(post);

    }

    private PostDto mapToDto(Post post)
    {
        PostDto postDto=modelMapper.map(post,PostDto.class);
        return postDto;
    }
    private Post mapToEntity(PostDto postDto)
    {
        Post post=modelMapper.map(postDto,Post.class);
        return post;
    }

}
