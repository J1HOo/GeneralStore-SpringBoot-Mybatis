package kh.edu.react.provider.controller;

import kh.edu.react.provider.dto.User;
import kh.edu.react.provider.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/user")
@RestController
public class UserAPIController {

    @Autowired
    private UserServiceImpl userService;

    // 유저 로그인
    @RequestMapping("/getUserById")
    public User getUserById(String userId) {
        return userService.getUserById(userId);
    }

    // 로그인한 유저 정보 가져오기
    @RequestMapping("/loginUser")
    public User loginUser(String userId, String userPassword) {
        return userService.loginUser(userId, userPassword);
    }

}
