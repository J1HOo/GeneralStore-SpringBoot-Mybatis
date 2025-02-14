package edu.store.kh.GeneralStore.controller;

import edu.store.kh.GeneralStore.dto.User;
import edu.store.kh.GeneralStore.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
public class UserAPIController {

    @Autowired
    private UserService userService;


    public ResponseEntity<Map<String , Object>> loginUser(@RequestBody User user, HttpSession session) {
        User loginUser = userService.loginUser(user.getUserId(), user.getPassword());

        Map<String, Object> response = new HashMap<>();

        if (loginUser != null) {
            session.setAttribute("loginUser", loginUser); // 로그인 성공시 세션에 로그인 정보 저장
            response.put("status", "success"); // 로그인 성공시 success 제공
            response.put("user", loginUser); // 로그인 성공시 유저 정보 제공
            return ResponseEntity.ok(response); // 로그인 성공시 200 제공
        } else {
            response.put("status", "fail"); // 로그인 실패시 fail 제공
            response.put("message", "아이디 또는 비밀번호가 일치하지 않습니다."); // 로그인 실패시 메시지 제공
            return ResponseEntity.badRequest().body(response); // 로그인 실패시 400 제공
        }
    }

    // 로그아웃
    @GetMapping("/logout")
    public void logout() {
        // 로그아웃 기능은 클라이언트에서 처리하므로 서버에서는 아무런 처리를 하지 않는다.
    }

    // 로그인 상태 확인
    @GetMapping("/loginCheck")
    public User loginCheck() {
        // 로그인 상태 확인은 클라이언트에서 처리하므로 서버에서는 아무런 처리를 하지 않는다.
        return null;
    }

    // 특정 유저 정보 조회 마이페이지
    @GetMapping("/mypage/{userId}")
    public User mypage(@PathVariable String userId) {
        return userService.findUserById(userId);
    }
}
