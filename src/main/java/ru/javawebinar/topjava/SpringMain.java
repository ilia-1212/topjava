package ru.javawebinar.topjava;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.repository.inmemory.InMemoryMealRepository;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.SecurityUtil;
import ru.javawebinar.topjava.web.meal.MealRestController;
import ru.javawebinar.topjava.web.user.AdminRestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public class SpringMain {
    public static void main(String[] args) {
        // java 7 automatic resource management (ARM)
        try (ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml")) {
//            System.out.println("Bean definition names: " + Arrays.toString(appCtx.getBeanDefinitionNames()));
            AdminRestController adminUserController = appCtx.getBean(AdminRestController.class);
            adminUserController.create(new User(null, "userName", "email@mail.ru", "password", Role.ADMIN));

            MealRestController mealRestController = appCtx.getBean(MealRestController.class);
            MealRepository repository = new InMemoryMealRepository();
            MealsUtil.meals.forEach(meal -> repository.save(meal, SecurityUtil.authUserId()));
            //add meal for second user
            int firstUserId = 1;
            int secondUserId = 2;
            Meal savedSecondUserMeal = repository.save(new Meal(LocalDateTime.now(), "Еда ВТОРОГО пользователя", 0), secondUserId);

            //get meals by first user before update
            System.out.println("\nFIRST USER's MEALS before update:" + repository.getAll(firstUserId, null, null, null, null));

            //get meals by second user before update
            System.out.println("\nSECOND USER's MEALS before update:" + repository.getAll(secondUserId, null, null, null, null));

            //update second user's meal by first user
            repository.save(new Meal(savedSecondUserMeal.getId(), LocalDateTime.MIN, "ИЗМЕНЕННАЯ еда ВТОРОГО пользователя", 1000, secondUserId), firstUserId);

            //get meals by first user after update
            System.out.println("\nFIRST USER's MEALS after update:" + repository.getAll(firstUserId, null, null, null, null));

            //get meals by second user after update
            System.out.println("\nSECOND USER's MEALS after update:" + repository.getAll(secondUserId, null, null, null, null));

            List<MealTo> mealsAll = mealRestController.getAll();
            List<MealTo> mealsDate = mealRestController.getAllFiltred(
                    LocalDate.of(2020, 01, 30),
                    null,//                    LocalTime.of(0, 0),
                    LocalDate.of(2020, 01, 31),
                    null//                    LocalTime.of(13, 0)
            );
            List<MealTo> mealsTime = mealRestController.getAllFiltred(
                    null,//     null.LocalDate.of(2020, 01, 30),
                    LocalTime.of(0, 0),
                    null,//     null,LocalDate.of(2020, 01, 31),
                    LocalTime.of(13, 0)
            );
        }
    }
}
