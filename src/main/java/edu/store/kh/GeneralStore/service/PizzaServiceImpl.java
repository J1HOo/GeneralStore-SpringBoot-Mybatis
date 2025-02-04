package edu.store.kh.GeneralStore.service;

import edu.store.kh.GeneralStore.dto.Pizza;
import edu.store.kh.GeneralStore.mapper.PizzaMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
public class PizzaServiceImpl implements PizzaService {

    @Autowired
    private PizzaMapper pizzaMapper;

    // 이미지 저장 경로
    private final String uploadDir = "/Users/parkjiho/Workspace/pizza-image-path/";

    private String saveImage(MultipartFile image) throws IOException {

        // 이미지가 없으면 기본 이미지로 대체
        if (image == null || image.isEmpty()) {
            return "/images/default-pizza.png";
        }

//        // 파일명: UUID + 원본파일명
//        String uuid = UUID.randomUUID().toString(); // 예: "a1b2c3"
//        String originalFilename = image.getOriginalFilename(); // 예: "abc.png"
//        String storedFilename = uuid + "_" + originalFilename; // 예: "a1b2c3_abc.png"

        // 파일명: 현재시간 + 확장자
        String originalFilename = image.getOriginalFilename(); // 예: "abc.png"
        String fileExtension = originalFilename.substring(originalFilename.lastIndexOf(".")); // 예: ".png"
        String filename = System.currentTimeMillis() + fileExtension; // 예: "1234567890.png"

        // 파일 저장
        File dest = new File(uploadDir, filename);

        // 파일이 저장될 디렉토리가 없으면 생성
        dest.getParentFile().mkdirs();

        image.transferTo(dest);
        return "/pizza-image/" + filename;
    }

    @Override
    public List<Pizza> selectAll() {
        return pizzaMapper.selectAll();
    }

    @Override
    public Pizza selectById(int id) {
        return pizzaMapper.selectById(id);
    }

    public int insertPizza(String name, int price, String description, MultipartFile image) throws IOException {
        Pizza pizza = new Pizza();
        pizza.setName(name);
        pizza.setPrice(price);
        pizza.setDescription(description);
        pizza.setImagePath(saveImage(image));
        return pizzaMapper.insertPizza(pizza);
    }

    public int updatePizza(int id, String name, int price, String description, MultipartFile image) throws IOException {
        Pizza pizza = pizzaMapper.selectById(id);

        pizza.setName(name);
        pizza.setPrice(price);
        pizza.setDescription(description);

        // 이미지가 있으면 이미지 경로를 업데이트
        if (image != null && !image.isEmpty()) {
            pizza.setImagePath(saveImage(image));
        }else {
            // 이미지가 없으면 기본 이미지로 대체
            pizza.setImagePath("/images/default-pizza.png");
        }
        return pizzaMapper.updatePizza(pizza);
    }

    @Override
    public int insertPizza(Pizza pizza) {
        return pizzaMapper.insertPizza(pizza);
    }

    @Override
    public int updatePizza(Pizza pizza) {
        return pizzaMapper.updatePizza(pizza);
    }

    @Override
    public int deletePizza(int id) {
        return pizzaMapper.deletePizza(id);
    }
}
