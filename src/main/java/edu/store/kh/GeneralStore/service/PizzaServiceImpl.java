package edu.store.kh.GeneralStore.service;

import edu.store.kh.GeneralStore.dto.Pizza;
import edu.store.kh.GeneralStore.mapper.PizzaMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
public class PizzaServiceImpl implements PizzaService {

    @Autowired
    private PizzaMapper pizzaMapper;

    // config.properties 에 설정된 파일 업로드 경로를 주입받음
    @Value("${upload-img}")
    private String uploadDir;

    private String saveImage(MultipartFile image) {
        // 이미지가 없으면 기본 이미지로 대체
        if (image == null || image.isEmpty()) {
            return "/images/default-pizza.png";
        }

        try {
            // 파일명: 현재시간 + 확장자
            String originalFilename = image.getOriginalFilename(); // 예: "abc.png"
            String fileExtension = originalFilename.substring(originalFilename.lastIndexOf(".")); // 예: ".png"
            String filename = System.currentTimeMillis() + fileExtension; // 예: "1234567890.png"

            // 파일 저장
            File dest = new File(uploadDir, filename);

            // 파일이 저장될 디렉토리가 없으면 생성
            if (!dest.getParentFile().exists()) {
                dest.getParentFile().mkdirs();
            }

            image.transferTo(dest);
            return "/pizza-image/" + filename;
        } catch (IOException e) {
            e.printStackTrace();
            // 예외 발생 시 기본 이미지 경로를 반환
            return "/images/default-pizza.png";
        }
    }

    @Override
    public List<Pizza> selectAll() {
        return pizzaMapper.selectAll();
    }

    @Override
    public Pizza selectById(int id) {
        return pizzaMapper.selectById(id);
    }

    @Override
    public int insertPizza(String name, int price, String description, MultipartFile image) {
        try {
            Pizza pizza = new Pizza();
            pizza.setName(name);
            pizza.setPrice(price);
            pizza.setDescription(description);
            pizza.setImagePath(saveImage(image));
            return pizzaMapper.insertPizza(pizza);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public int updatePizza(int id, String name, int price, String description, MultipartFile image) {
        try {
            Pizza pizza = pizzaMapper.selectById(id);
            pizza.setName(name);
            pizza.setPrice(price);
            pizza.setDescription(description);

            // 이미지가 있으면 이미지 경로를 업데이트
            if (image != null && !image.isEmpty()) {
                pizza.setImagePath(saveImage(image));
            } else {
                // 이미지가 없으면 기본 이미지로 대체
                pizza.setImagePath("/images/default-pizza.png");
            }
            return pizzaMapper.updatePizza(pizza);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public int deletePizza(int id) {
        return pizzaMapper.deletePizza(id);
    }
}
