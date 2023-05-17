package realtalk.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import realtalk.dto.PostDto;
import realtalk.mapper.PostMapper;
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
        return postMapper.toPostDto(postService.createPost(user, postDto.getText(), postDto.getTag()));
    }

    @PutMapping("/{id}")
    public PostDto editPost(@PathVariable Long id, @RequestBody PostDto postDto){
        return postMapper.toPostDto(postService.updatePost(id, postDto.getText(), postDto.getTag()));
    }

    @DeleteMapping("/{id}")
    public PostDto deletePost(@PathVariable Long id){
        return postMapper.toPostDto(postService.deletePost(id));
    }

    /**
     * @return true - поставил лайк, false - убрал лайк
     */
    @SecurityRequirement(name = "Bearer Authentication")
    @PutMapping("/like/{id}")
    public boolean like(@AuthenticationPrincipal User user, @PathVariable Long id) {
        return postService.likePost(user, postService.findPost(id));
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping
    public List<PostDto> getPostsByUser(@AuthenticationPrincipal User user){
        return postService.getUserPosts(user).stream()
                .map(post -> postMapper.toPostDto(post))
                .toList();
    }
}
