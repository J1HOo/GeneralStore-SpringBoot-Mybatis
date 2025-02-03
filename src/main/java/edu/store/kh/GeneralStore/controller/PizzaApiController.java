package edu.store.kh.GeneralStore.controller;


import edu.store.kh.GeneralStore.dto.Pizza;
import edu.store.kh.GeneralStore.service.PizzaService;
import edu.store.kh.GeneralStore.service.PizzaServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequestMapping("/api")
@RestController
public class PizzaApiController {
    // ServiceImpl -> Autowired 호출
    @Autowired
    private PizzaServiceImpl pizzaService;

    @GetMapping("/pizzas") //   /api/pizzas
    public List<Pizza> selectAll() {
        return pizzaService.selectAll();
    }

    @PostMapping("/pizzas") //   /api/pizzas
    public void createPizza() {
    }

    @GetMapping("/pizzas/{id}") //   /api/pizzas/1
    public Pizza selectById(@PathVariable int id) {
        return pizzaService.selectById(id);
    }

    @PutMapping("pizzas/{id}") //   /api/pizzas/1
    public void updatePizza(@PathVariable int id, @RequestBody Pizza pizza) {
        pizzaService.updatePizza(pizza);
    }

    @DeleteMapping("/pizzas/{id}") //   /api/pizzas/1
    public void deletePizza(@PathVariable int id) {
        pizzaService.deletePizza(id);
    }
}
