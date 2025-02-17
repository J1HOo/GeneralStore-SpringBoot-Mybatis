package edu.store.kh.GeneralStore.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SocialViewController {

    // 카카오 회원가입 페이지
    @GetMapping("/signup/kakao")
    public String kakaoSignup() {
        return "signup/kakao";
    }
//    // 카카오 로그인 페이지
//    @GetMapping("/login/kakao")
//    public String kakaoLogin() {
//        return "login/kakao";
//    }
//
    // 네이버 회원가입 페이지
    @GetMapping("/signup/naver")
    public String naverSignup() {
        return "signup/naver";
    }
//
//    // 네이버 로그인 페이지
//    @GetMapping("login/naver")
//    public String naverLogin() {
//        return "login/naver";
//    }
}
