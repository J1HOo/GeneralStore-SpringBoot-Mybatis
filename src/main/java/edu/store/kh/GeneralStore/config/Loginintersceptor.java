package edu.store.kh.GeneralStore.config;

import edu.store.kh.GeneralStore.dto.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.servlet.HandlerInterceptor;

public class Loginintersceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession(); // 로그인을 했다면 request 에서 getssion에 로그인한 정보가 담겨있음 -> 가져와서 세션에 담아줌

        User user = (User) session.getAttribute("user"); // 세션에 담긴 user 정보를 가져옴

        if(user == null) { // 로그인을 안했다면
            response.sendRedirect("/login"); // 로그인 페이지로 이동
            return false;
        }
        return true;
    }
}
