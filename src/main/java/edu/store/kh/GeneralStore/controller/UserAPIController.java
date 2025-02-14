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
            response.put("status", "success");
            response.put("user", loginUser); // 로그인 성공시 유저 정보 제공
            return ResponseEntity.ok(response); // 로그인 성공시 200 제공
        } else {
            response.put("status", "fail"); // 로그인 실패시 fail 제공
            response.put("message", "아이디 또는 비밀번호가 일치하지 않습니다."); // 로그인 실패시 메시지 제공
            return ResponseEntity.badRequest().body(response); // 로그인 실패시 400 제공
        }
    }

    // 로그아웃
    @PostMapping("/logout")
    public ResponseEntity<Map<String, Object>> logout(HttpSession session) {
        session.invalidate(); // 세션 무효화
        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        return ResponseEntity.ok(response); // 로그아웃 성공시 200 제공
    }

    // 로그인 상태 확인 -> DB를 거치지 않고 오직 세션에서만 정보가 존재하는지 확인
    @GetMapping("/checkLogin")
    public ResponseEntity<?> checkLogin(HttpSession session) {
        User loginUser = (User) session.getAttribute("user");
        if (loginUser != null) {
            return ResponseEntity.ok(loginUser);
        } else {
            return ResponseEntity.status(401).body(Map.of("message", "로그인 상태가 아닙니다."));
        }

    }

    // 특정 유저 정보 조회 -> mypage
    @GetMapping("/{userId}")
    public ResponseEntity<?> findUserById(@PathVariable("userId") String userId) {
        User user = userService.findUserById(userId);

        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.status(404).body(Map.of("message", "로그인 상태가 아닙니다."));
        }
    }

}
