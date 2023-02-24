package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.storage.Storage;
import ru.javawebinar.topjava.util.MapUtil;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.TimeUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Random;

import static org.slf4j.LoggerFactory.getLogger;


public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);
    private static final String INSERT_OR_EDIT = "WEB-INF/jsp/editMeal.jsp";
    private static final String VIEW = "WEB-INF/jsp/viewMeal.jsp";
    private static final String LIST = "WEB-INF/jsp/listMeal.jsp";
    private Storage<Meal> storage;

    @Override
    public void init() throws ServletException {
        super.init();
        storage = MapUtil.get().getStorage();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("doPost meal");
        req.setCharacterEncoding("UTF-8");
        Boolean isSubmit = (req.getParameter("submit") != null);
        if (!isSubmit) {
            resp.sendRedirect("meals");
            return;
        }
        String uuid = req.getParameter("uuid");
        String dateTime = req.getParameter("dateTime");
        String description = req.getParameter("description");
        String calories = req.getParameter("calories");
        Meal m;

        if (MealsUtil.isEmpty(description)) {
            log.info("empty description, nothing to change");
            resp.sendRedirect("meals");
            return;
        } else {
            m = new Meal(Integer.parseInt(uuid), "");
            if (!MealsUtil.isEmpty(description)) {
                m.setDescription(description.trim());
            }
        }

        if (!MealsUtil.isEmpty(dateTime)) {
            m.setDateTime(TimeUtil.fromHtml(dateTime.trim()));
        }

        if (!MealsUtil.isEmpty(calories)) {
            m.setCalories(Integer.parseInt(calories.trim()));
        }

        storage.update(m);
        resp.sendRedirect("meals");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.info("doGet meal");
        String forward = "";
        String action = request.getParameter("action");
        String uuid = request.getParameter("uuid");

        if (action == null) {
            request.setAttribute("meals", filteredByStreams(storage.getAllSorted()));
            request.getRequestDispatcher(LIST).forward(request, response);
            return;
        }
        Meal meal;
        switch (action) {
            case "delete": {
                storage.delete(Integer.parseInt(uuid));
                response.sendRedirect("meals");
                return;
            }
            case "add": {
                forward = INSERT_OR_EDIT;
                meal = new Meal();
                meal.setUuid(new Random().nextInt());
                setEmptyMeal(meal);
                break;
            }
            case "view": {
                forward = VIEW;
                meal = storage.getById(Integer.parseInt(uuid));
                break;
            }
            case "edit": {
                forward = INSERT_OR_EDIT;
                meal = storage.getById(Integer.parseInt(uuid));
                setEmptyMeal(meal);
                break;
            }
            default:
                throw new IllegalArgumentException("Action " + action + " is illegal");
        }
        request.setAttribute("meal", meal);
        request.getRequestDispatcher(forward).forward(request, response);
    }

    private void setEmptyMeal(Meal meal) {
        if (meal.getCalories() == 0) {
            meal.setCalories(0);
        }
        if (meal.getDateTime() == null) {
            meal.setDateTime(LocalDateTime.now());
        }
    }

    private List<MealTo> filteredByStreams(List<Meal> meals) {
        return MealsUtil.filteredByStreams(meals,
                LocalTime.of(0, 0), LocalTime.of(23, 59), MealsUtil.MAX_CALORIES);
    }
}
