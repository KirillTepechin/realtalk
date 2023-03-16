package realtalk.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import realtalk.dto.PostDto;
import realtalk.mapper.PostMapper;
import realtalk.mapper.UserMapper;
import realtalk.model.Post;
import realtalk.model.User;
import realtalk.service.PostService;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/post")
public class PostController {
    @Autowired
    private PostService postService;
    @Autowired
    private PostMapper postMapper;

    @SecurityRequirement(name = "Bearer Authentication")
    @PostMapping
    public PostDto createPost(@AuthenticationPrincipal User user, @RequestBody PostDto postDto){
        return postMapper.toPostDto(postService.createPost(user, postDto.getText()));
    }

    @PutMapping("/{id}")
    public PostDto editPost(@PathVariable Long id, @RequestBody PostDto postDto){
        return postMapper.toPostDto(postService.updatePost(id, postDto.getText()));
    }

    @DeleteMapping("/{id}")
    public PostDto deletePost(@PathVariable Long id){
        return postMapper.toPostDto(postService.deletePost(id));
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping
    public List<PostDto> getPostsByUser(@AuthenticationPrincipal User user){
        return postService.getUserPosts(user).stream()
                .map(post -> postMapper.toPostDto(post))
                .toList();
    }
}
