package realtalk.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import realtalk.dto.PostDto;
import realtalk.mapper.PostMapper;
import realtalk.model.User;
import realtalk.service.PostService;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/feed")
public class FeedController {
    @Autowired
    private PostService postService;
    @Autowired
    private PostMapper postMapper;

    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping
    public List<PostDto> getFeed(@AuthenticationPrincipal User user){
        return postService.getFeed(user).stream()
                .map(post -> postMapper.toPostDto(post))
                .toList();
    }
}
