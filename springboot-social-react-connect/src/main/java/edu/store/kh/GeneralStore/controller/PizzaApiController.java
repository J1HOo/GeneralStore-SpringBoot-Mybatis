package edu.store.kh.GeneralStore.controller;

import edu.store.kh.GeneralStore.dto.Pizza;
import edu.store.kh.GeneralStore.service.PizzaServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api")
public class PizzaApiController {

    @Autowired
    private PizzaServiceImpl pizzaService;

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

        return pizzaService.insertPizza(name, price, description, image);
    }

    @PutMapping(value = "/pizzas/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public int updatePizza(
        @PathVariable int id,
        @RequestParam("name") String name,
        @RequestParam("price") int price,
        @RequestParam(value = "description", required = false) String description,
        @RequestParam(value = "image", required = false) MultipartFile image
    ) throws IOException {

        return pizzaService.updatePizza(id, name, price, description, image);
    }

    @DeleteMapping("/pizzas/{id}")
    public void deletePizza(@PathVariable int id) {
        pizzaService.deletePizza(id);
    }
}
