package com.net.blog.controller;

import com.net.blog.dto.PostDto;
import com.net.blog.dto.PostDtoV2;
import com.net.blog.dto.PostResponse;
import com.net.blog.service.PostService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static com.net.blog.utils.PostConstants.*;

@RestController
@RequestMapping
public class PostController {

    private PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping("/api/v1/post/create")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PostDto> createPost(@Valid @RequestBody PostDto postDto){
        return  new ResponseEntity<>(postService.createPost(postDto), HttpStatus.CREATED);
    }

    @GetMapping("/api/v1/post/getall")
    public ResponseEntity<PostResponse> getAllPosts(
            @RequestParam(value = "pageNo",defaultValue = DEFAULT_PAGE_NO,required = false) int pageNo,
            @RequestParam(value = "pageSize",defaultValue = DEFAULT_PAGE_SIZE,required = false) int pageSize,
            @RequestParam(value = "sortBy",defaultValue = DEFAULT_SORT_BY,required = false) String sortBy,
            @RequestParam(value = "sortDir",defaultValue =DEFAULT_SORT_DIRECTION,required = false) String sortDir

    ){
        return  new ResponseEntity<>(postService.getAllPost(pageNo,pageSize,sortBy,sortDir),HttpStatus.OK);
    }
    @GetMapping("/api/v1/post/get/{id}")
    public ResponseEntity<PostDto> getByIdV1(@PathVariable("id") long id){
        return  new ResponseEntity<>(postService.getById(id),HttpStatus.OK);
    }

    @GetMapping("/api/v2/post/get/{id}")
    public ResponseEntity<PostDtoV2> getByIdV2(@PathVariable("id") long id){

        PostDto postDto = postService.getById(id);
        PostDtoV2 postDtoV2=new PostDtoV2();
        postDtoV2.setId(postDto.getId());
        postDtoV2.setTitle(postDto.getTitle());
        postDtoV2.setDescription(postDto.getDescription());
        postDtoV2.setContent(postDto.getContent());
        postDtoV2.setComments(postDto.getComments());
        postDtoV2.setCategoryId(postDto.getCategoryId());

        List<String> tagds=new ArrayList<>();
        tagds.add("Java");
        tagds.add("Spring Boot");
        tagds.add("Maven");
        postDtoV2.setTags(tagds);

        return  new ResponseEntity<>(postDtoV2,HttpStatus.OK);
    }

    @PutMapping("/api/v1/post/update/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PostDto> update(@PathVariable("id") long id,@Valid @RequestBody PostDto postDto){
        return  new ResponseEntity<>(postService.update(id,postDto),HttpStatus.OK);
    }

    @GetMapping("/api/v1/post/category/{categoryId}")
    public ResponseEntity<List<PostDto>> getByCategory(@PathVariable("categoryId") long categoryId){
        return  new ResponseEntity<>(postService.getPostByCategory(categoryId),HttpStatus.OK);
    }

    @DeleteMapping("/api/v1/post/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> delete(@PathVariable("id") long id){
        postService.delete(id);
        return  new ResponseEntity<>("Deleted Successfully",HttpStatus.OK);
    }

}
