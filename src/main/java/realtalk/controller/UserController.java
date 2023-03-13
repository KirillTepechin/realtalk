package realtalk.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import realtalk.dto.LoginDto;
import realtalk.dto.RegisterDto;
import realtalk.dto.UserDto;
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

    @GetMapping("/me")
    public UserDto me(@AuthenticationPrincipal User user) {
        return userMapper.toUserDto(user);
    }

    @PutMapping(value = "/edit-profile", consumes = {MULTIPART_FORM_DATA_VALUE})
    public User editProfile(@RequestParam String name,@RequestParam String surname,@RequestParam MultipartFile file){
        User user = new User();
        user.setName(name);
        user.setSurname(surname);
        return userService.updateUser(user, file);
    }

}
