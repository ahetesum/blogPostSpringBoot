package com.net.blog.controller;

import com.net.blog.dto.PostDto;
import com.net.blog.dto.PostResponse;
import com.net.blog.service.PostService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.net.blog.utils.PostConstants.*;

@RestController
@RequestMapping("/api/post")
public class PostController {

    private PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping("create")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PostDto> createPost(@Valid @RequestBody PostDto postDto){
        return  new ResponseEntity<>(postService.createPost(postDto), HttpStatus.CREATED);
    }

    @GetMapping("getall")
    public ResponseEntity<PostResponse> getAllPosts(
            @RequestParam(value = "pageNo",defaultValue = DEFAULT_PAGE_NO,required = false) int pageNo,
            @RequestParam(value = "pageSize",defaultValue = DEFAULT_PAGE_SIZE,required = false) int pageSize,
            @RequestParam(value = "sortBy",defaultValue = DEFAULT_SORT_BY,required = false) String sortBy,
            @RequestParam(value = "sortDir",defaultValue =DEFAULT_SORT_DIRECTION,required = false) String sortDir

    ){
        return  new ResponseEntity<>(postService.getAllPost(pageNo,pageSize,sortBy,sortDir),HttpStatus.OK);
    }
    @GetMapping("get/{id}")
    public ResponseEntity<PostDto> getById(@PathVariable("id") long id){
        return  new ResponseEntity<>(postService.getById(id),HttpStatus.OK);
    }

    @PutMapping("update/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PostDto> update(@PathVariable("id") long id,@Valid @RequestBody PostDto postDto){
        return  new ResponseEntity<>(postService.update(id,postDto),HttpStatus.OK);
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<PostDto>> getByCategory(@PathVariable("categoryId") long categoryId){
        return  new ResponseEntity<>(postService.getPostByCategory(categoryId),HttpStatus.OK);
    }

    @DeleteMapping("delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> delete(@PathVariable("id") long id){
        postService.delete(id);
        return  new ResponseEntity<>("Deleted Successfully",HttpStatus.OK);
    }

}
