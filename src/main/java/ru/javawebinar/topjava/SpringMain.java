package ru.javawebinar.topjava;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.SecurityUtil;
import ru.javawebinar.topjava.web.meal.MealRestController;
import ru.javawebinar.topjava.web.user.AdminRestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

public class SpringMain {
    public static void main(String[] args) {
        // java 7 automatic resource management (ARM)
        try (ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml")) {
            System.out.println("Bean definition names: " + Arrays.toString(appCtx.getBeanDefinitionNames()));
            AdminRestController adminUserController = appCtx.getBean(AdminRestController.class);
            adminUserController.create(new User(null, "userName", "email@mail.ru", "password", Role.ADMIN));

            MealRestController mealRestController = appCtx.getBean(MealRestController.class);
            MealsUtil.meals.forEach(mealRestController::create);

            mealRestController.create(new Meal(
                    LocalDateTime.of(2020, Month.MAY, 01, 00, 0),
                    "Ночной ужин второго пользователя", 5000)
            );

            List<MealTo> meals = mealRestController.getAll();
            System.out.println(meals);


            List<MealTo> meals1 = mealRestController.getAllFiltred(
                    LocalDate.of(2020, 01, 31),
                    LocalTime.of(0, 0),
                    LocalDate.of(2020, 01, 31),
                    LocalTime.of(13, 0)
            );
            System.out.println(meals1);
        }
    }
}
