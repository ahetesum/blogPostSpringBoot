package com.net.blog.service.impl;

import com.net.blog.dto.CommentDto;
import com.net.blog.dto.PostDto;
import com.net.blog.entity.Comment;
import com.net.blog.entity.Post;
import com.net.blog.exception.BlogApiException;
import com.net.blog.exception.ResourceNotFoundException;
import com.net.blog.repository.CommentRepository;
import com.net.blog.repository.PostRepository;
import com.net.blog.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;



@Service
public class CommentServiceImpl implements CommentService {

    private CommentRepository commentRepository;
    private PostRepository postRepository;

    private ModelMapper modelMapper;
    public CommentServiceImpl(CommentRepository commentRepository,PostRepository postRepository,ModelMapper modelMapper) {
        this.commentRepository = commentRepository;
        this.postRepository=postRepository;
        this.modelMapper=modelMapper;
    }

    @Override
    public CommentDto createComment(long postId,CommentDto commentDto) {

        Comment comment= mapToEntity(commentDto);

        Post post = postRepository.findById(postId).orElseThrow(()->
                            new ResourceNotFoundException( "Post","id",postId));

        comment.setPost(post);
        Comment createdComment=commentRepository.save(comment);
        return mapToDto(createdComment);
    }

    @Override
    public List<CommentDto> getAllComment(long postId) {
        List<Comment> comments=commentRepository.findByPostId(postId);
        return comments.stream().map(c->mapToDto(c)).collect(Collectors.toList());
    }

    @Override
    public CommentDto getById(long postId, long commentId) {

        Comment comment = getComment(postId, commentId);


        return mapToDto(comment);
    }

    @Override
    public CommentDto update(long postId, long commentId, CommentDto commentDto) {
        Comment comment = getComment(postId, commentId);

        comment.setName(commentDto.getName());
        comment.setEmail(commentDto.getEmail());
        comment.setBody(commentDto.getBody());

        Comment updatedComment=  commentRepository.save(comment);

        return mapToDto(updatedComment);
    }

    @Override
    public void delete(long postId, long commentId) {
        Comment comment = getComment(postId, commentId);
        commentRepository.delete(comment);
    }


    private Comment getComment(long postId, long commentId) {
        Post post = postRepository.findById(postId).orElseThrow(()->
                new ResourceNotFoundException( "Post","id", postId));
        Comment comment =commentRepository.findById(commentId).orElseThrow(()->
                new ResourceNotFoundException("Comment","id", commentId));

        if (!comment.getPost().getId().equals(post.getId()))
        {
            throw new BlogApiException(HttpStatus.NOT_FOUND,"Comment does not belongs to Post");
        }
        return comment;
    }

    private CommentDto mapToDto(Comment comment)
    {
        CommentDto commentDto=modelMapper.map(comment,CommentDto.class);
        return commentDto;
    }
    private Comment mapToEntity(CommentDto commentDto)
    {
        Comment comment=modelMapper.map(commentDto,Comment.class);
        return comment;
    }

}
