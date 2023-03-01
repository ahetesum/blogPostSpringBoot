package com.net.blog.controller;

import com.net.blog.dto.CommentDto;
import com.net.blog.dto.PostDto;
import com.net.blog.entity.Post;
import com.net.blog.service.CommentService;
import com.net.blog.service.PostService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/post")
public class CommentController {
    private CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/{postId}/comment/create")
    public ResponseEntity<CommentDto> createComment(@PathVariable("postId") long postId,
                                                    @Valid @RequestBody CommentDto commentDto){
        return new  ResponseEntity<>(commentService.createComment(postId,commentDto), HttpStatus.CREATED);

    }

    @GetMapping("/{postId}/comment/getAll")
    public ResponseEntity<List<CommentDto>>getAll(@PathVariable("postId") long postId){

        return  ResponseEntity.ok(commentService.getAllComment(postId));
    }

    @GetMapping("/{postId}/comment/get/{commentId}")
    public ResponseEntity<CommentDto>getCommentById(@PathVariable("postId") long postId,
                                                    @PathVariable("commentId")long commentId){
        return  ResponseEntity.ok(commentService.getById(postId,commentId));
    }

    @PutMapping("/{postId}/comment/update/{commentId}")
    public ResponseEntity<CommentDto> update(@PathVariable("postId") long postId,
                                          @PathVariable("commentId") long commentId,
                                          @Valid @RequestBody CommentDto commentDto){
        return  new ResponseEntity<>(commentService.update(postId,commentId,commentDto),HttpStatus.OK);
    }

    @DeleteMapping("/{postId}/comment/delete/{commentId}")
    public ResponseEntity<String> delete(@PathVariable("postId") long postId,
                                         @PathVariable("commentId") long commentId){
        commentService.delete(postId,commentId);
        return  new ResponseEntity<>("Deleted Successfully",HttpStatus.OK);
    }

}
