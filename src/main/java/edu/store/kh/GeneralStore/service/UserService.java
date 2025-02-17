package edu.store.kh.GeneralStore.service;

import edu.store.kh.GeneralStore.dto.User;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    // 유저 로그인 정보를 가져오는 메소드
    User findUserById(String userId);
    User loginUser(@Param("userId") String userId, @Param("password") String password);

    // User findUserById(@Param("userId") String userId); // 이름이 다 다르게 선언 되서 생기는 문제를 해결하기 위해 @Param을 사용, 최대한 이름을 같게 맞춰주는게 좋다.
}
