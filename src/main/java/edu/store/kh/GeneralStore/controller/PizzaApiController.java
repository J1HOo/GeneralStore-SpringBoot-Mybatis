package edu.store.kh.GeneralStore.controller;


import edu.store.kh.GeneralStore.dto.Pizza;
import edu.store.kh.GeneralStore.service.PizzaServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;


@RequestMapping("/api")
@RestController
public class PizzaApiController {

    @Autowired
    private PizzaServiceImpl pizzaService;

    @GetMapping("/pizzas") //   /api/pizzas
    public List<Pizza> selectAll() {
        return pizzaService.selectAll();
    }

    @GetMapping("/pizzas/{id}") //   /api/pizzas/1
    public Pizza selectById(@PathVariable int id) {
        return pizzaService.selectById(id);
    }

    private final String UPLOAD_DIR = "/Users/parkjiho/Workspace/pizza-image-path/";

    private String saveImage(MultipartFile image) throws IOException {
        if (image == null || image.isEmpty()) {
            return null;
        }

        File uploadDirFile = new File(UPLOAD_DIR);
        if (!uploadDirFile.exists()) {
            uploadDirFile.mkdirs();
        }

        // 파일명: UUID + 원본파일명
        String uuid = UUID.randomUUID().toString();
        String originalFilename = image.getOriginalFilename(); // 예: "abc.png"
        String storedFilename = uuid + "_" + originalFilename; // 예: "a1b2c3_abc.png"

        File dest = new File(UPLOAD_DIR, storedFilename);
        image.transferTo(dest);

        return "/pizza-image/" + storedFilename;
    }

    @PostMapping(value = "/pizzas", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public int insertPizza(
        @RequestParam("name") String name,
        @RequestParam("price") int price,
        @RequestParam(value = "description", required = false) String description,
        @RequestParam(value = "image", required = false) MultipartFile image
    ) throws IOException {
        String imagePath = saveImage(image);

        Pizza pizza = new Pizza();
        pizza.setName(name);
        pizza.setPrice(price);
        pizza.setDescription(description);
        pizza.setImagePath(imagePath);

        int inserted = pizzaService.insertPizza(pizza);
        if (inserted == 0) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Insert failed");
        }

        return inserted;
    }

    @PutMapping(value = "/pizzas/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public int updatePizza(
        @PathVariable int id,
        @RequestParam("name") String name,
        @RequestParam("price") int price,
        @RequestParam(value = "description", required = false) String description,
        @RequestParam(value = "image", required = false) MultipartFile image
    ) throws IOException {

        Pizza existing = pizzaService.selectById(id);
        if (existing == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pizza not found");
        }

        existing.setName(name);
        existing.setPrice(price);
        existing.setDescription(description);


        if (image != null && !image.isEmpty()) {
            if (existing.getImagePath() != null && !existing.getImagePath().isEmpty()) {
                String oldFileName = existing.getImagePath().replace("/pizza-image/", "");
                File oldFile = new File(UPLOAD_DIR, oldFileName);
                if (oldFile.exists()) {
                    oldFile.delete();
                }
            }
            String newImagePath = saveImage(image);
            existing.setImagePath(newImagePath);
        }
        return pizzaService.updatePizza(existing);
    }

    @DeleteMapping("/pizzas/{id}") //   /api/pizzas/1
    public void deletePizza(@PathVariable int id) {
        pizzaService.deletePizza(id);
    }
}
