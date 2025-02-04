package edu.store.kh.GeneralStore.controller;

import edu.store.kh.GeneralStore.dto.Pizza;
import edu.store.kh.GeneralStore.service.PizzaServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api")
public class PizzaApiController {

    @Autowired
    private PizzaServiceImpl pizzaService;

    private final String uploadDir = "/Users/parkjiho/Workspace/pizza-image-path/";

    private String saveImage(MultipartFile image) throws IOException {
        if (image == null || image.isEmpty()) {
            return null;
        }

        String originalFilename = image.getOriginalFilename();
        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        String filename = System.currentTimeMillis() + extension;

        File dest = new File(uploadDir, filename);
        image.transferTo(dest);
        return "/pizza-image/" + filename;
    }

    @GetMapping("/pizzas")
    public List<Pizza> selectAll() {
        return pizzaService.selectAll();
    }

    @GetMapping("/pizzas/{id}")
    public Pizza selectById(@PathVariable int id) {
        return pizzaService.selectById(id);
    }

    @PostMapping(value = "/pizzas", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public int insertPizza(
        @RequestParam("name") String name,
        @RequestParam("price") int price,
        @RequestParam(value = "description", required = false) String description,
        @RequestParam(value = "image", required = false) MultipartFile image
    ) throws IOException {
        Pizza pizza = new Pizza();
        pizza.setName(name);
        pizza.setPrice(price);
        pizza.setDescription(description);
        pizza.setImagePath(saveImage(image));
        return pizzaService.insertPizza(pizza);
    }

    @PutMapping(value = "/pizzas/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public int updatePizza(
        @PathVariable int id,
        @RequestParam("name") String name,
        @RequestParam("price") int price,
        @RequestParam(value = "description", required = false) String description,
        @RequestParam(value = "image", required = false) MultipartFile image
    ) throws IOException {
        Pizza pizza = pizzaService.selectById(id);
        pizza.setName(name);
        pizza.setPrice(price);
        pizza.setDescription(description);
        if (image != null && !image.isEmpty()) {
            pizza.setImagePath(saveImage(image));
        }
        return pizzaService.updatePizza(pizza);
    }

    @DeleteMapping("/pizzas/{id}")
    public void deletePizza(@PathVariable int id) {
        pizzaService.deletePizza(id);
    }
}
