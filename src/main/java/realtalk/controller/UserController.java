package realtalk.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import realtalk.dto.*;
import realtalk.mapper.UserMapper;
import realtalk.model.User;
import realtalk.service.UserService;

import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private UserMapper userMapper;

    @PostMapping("/register")
    public void register(@Valid @RequestBody RegisterDto registerDto) {
        User user = userMapper.fromRegisterDto(registerDto);
        userService.registration(user);
    }

    @PostMapping("/auth")
    public String auth(@RequestBody LoginDto loginDto) {
        User user = userMapper.fromLoginDto(loginDto);
        return userService.authentication(user);
    }
    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("/me")
    public UserProfileInfoDto me(@AuthenticationPrincipal User user) {
        return userMapper.toUserProfileInfoDto(user);
    }
    @SecurityRequirement(name = "Bearer Authentication")
    @PutMapping(value = "/edit-profile", consumes = {MULTIPART_FORM_DATA_VALUE})
    public UserEditDto editProfile(@AuthenticationPrincipal User user, @RequestParam(required = false) String name,
                                   @RequestParam(required = false) String surname, @RequestParam(required = false) String password,
                                   @RequestParam(required = false) String login, @RequestParam(required = false) MultipartFile file){
        return userMapper.toUserEditDto(userService.updateUser(user,login, password, name, surname, file));
    }

    /**
     * @return true - подписался, false - отписался
     */
    @SecurityRequirement(name = "Bearer Authentication")
    @PutMapping("/subscribe/{id}")
    public boolean subscribe(@AuthenticationPrincipal User user, @PathVariable Long id) {
        return userService.subscribe(user, userService.findUser(id));
    }
}
