package edu.store.kh.GeneralStore.service;

import edu.store.kh.GeneralStore.dto.Pizza;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PizzaService {
    // 피자 메뉴 모두 조회
    List<Pizza> selectAll();

    // 피자 상세 조회
    Pizza selectById(int id);

    // 피자 메뉴 저장 - 자료형  void or int
    int insertPizza(String name, int price, String description, MultipartFile image);

    // 피자 메뉴 수정 - 자료형  void or int
     int updatePizza(int id, String name, int price, String description, MultipartFile image);

    // 피자 메뉴 삭제 - 자료형  void or int
     int deletePizza(int id);
}
