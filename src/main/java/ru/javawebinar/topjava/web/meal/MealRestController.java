package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;

import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.assureIdConsistent;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;

@Controller
public class MealRestController {
    protected final Logger log = LoggerFactory.getLogger(getClass());
    @Autowired
    private MealService service;

    public List<Meal> getAll(int userId) {
        log.info("getAll for userId {}", userId);
        return service.getAll(userId);
    }

    public Meal get(int id, int userId) {
        log.info("get {} for userId {}", id, userId);
        return service.get(id, userId);
    }

    public Meal create(Meal meal, int userId) {
        log.info("create {} for userId {}", meal, userId);
        checkNew(meal);
        return service.create(meal, userId);
    }

    public void delete(int id, int userId) {
        log.info("delete {} for userId {}", id, userId);
        service.delete(id, userId);
    }

    public Meal update(Meal meal, int id, int userId) {
        log.info("update {} with id={} for userId {}", meal, id, userId);
        assureIdConsistent(meal, id);
        return service.update(meal, userId);
    }
}