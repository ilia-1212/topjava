package ru.javawebinar.topjava.web.meal;

import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;

import java.util.List;

@Controller
public class MealRestController extends AbstractMealController {
    public MealRestController(MealService service) {
        super(service);
    }

    public Meal get(int id) {
        return super.get(id);
    }

    public List<MealTo> getAll() {
        return super.getAll();
    }

    public String delete(int id) {
        return super.delete(id);
    }

    public Meal create(Meal meal) {
        return super.create(meal);
    }

    public void update(Meal meal, int id) {
        super.update(meal, id);
    }
}