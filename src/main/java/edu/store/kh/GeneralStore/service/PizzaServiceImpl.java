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

    private final String uploadDir = "/Users/parkjiho/Workspace/pizza-image-path/";

    private String saveImage(MultipartFile image) throws IOException {
        if (image == null || image.isEmpty()) {
            return null;
        }

        String originalFilename = image.getOriginalFilename();
        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        String filename = System.currentTimeMillis() + extension;

        File dest = new File(uploadDir, filename);
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
        if (pizza == null) {
            return 0;
        }
        pizza.setName(name);
        pizza.setPrice(price);
        pizza.setDescription(description);

        if (image != null && !image.isEmpty()) {
            pizza.setImagePath(saveImage(image));
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
