package kh.edu.react.provider.service;

import kh.edu.react.provider.dto.User;
import kh.edu.react.provider.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    // 유저 로그인
    public User getUserById(String userId) {
        return userMapper.getUserById(userId);
    }

    // 로그인한 유저 정보 가져오기
    public User loginUser(String userId, String userPassword) {
        return userMapper.loginUser(userId, userPassword);
    }
}
