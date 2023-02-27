package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.storage.MealStorage;
import ru.javawebinar.topjava.storage.Storage;
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

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);
    private static final String INSERT_OR_EDIT = "WEB-INF/editMeal.jsp";
    private static final String VIEW = "WEB-INF/viewMeal.jsp";
    private static final String LIST = "WEB-INF/listMeal.jsp";
    private Storage<Meal> storage;

    @Override
    public void init() {
        storage = MealStorage.getStorage();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        log.info("MealServlet doPost");
        req.setCharacterEncoding("UTF-8");
        String id = req.getParameter("id");
        String dateTime = req.getParameter("dateTime");
        String description = req.getParameter("description");
        String calories = req.getParameter("calories");
        log.info("MealServlet parameters: id=" + id +
                "; dateTime=" + dateTime +
                "; description=" + description +
                ";calories=" + calories);
        Meal temp;
        int mealId = Integer.parseInt(id);
        temp = new Meal();
        temp.setDateTime(TimeUtil.fromHtml(dateTime));
        temp.setDescription(description);
        temp.setCalories(Integer.parseInt(calories));

        if (mealId == 0) {
            storage.add(temp);
            log.info("MealServlet storage.add");
        } else {
            temp.setId(mealId);
            storage.update(temp);
            log.info("MealServlet storage.update");
        }
        resp.sendRedirect("meals");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.info("MealServlet doGet");
        String forward = "";
        String action = (request.getParameter("action") == null ? "" : request.getParameter("action"));
        String id = request.getParameter("id");
        log.info("MealServlet action=" + action + ";id=" + id);

        Meal meal;
        switch (action) {
            case "delete": {
                storage.delete(Integer.parseInt(id));
                response.sendRedirect("meals");
                return;
            }
            case "add": {
                forward = INSERT_OR_EDIT;
                meal = new Meal(0, LocalDateTime.now(), "", 0);
                break;
            }
            case "view": {
                forward = VIEW;
                meal = storage.getById(Integer.parseInt(id));
                break;
            }
            case "edit": {
                forward = INSERT_OR_EDIT;
                meal = storage.getById(Integer.parseInt(id));
                break;
            }
            default:
                List<Meal> meals = storage.getAll();
                request.setAttribute("meals", MealsUtil.filteredByStreams(meals,
                        LocalTime.MIN, LocalTime.MAX, MealsUtil.MAX_CALORIES));
                request.getRequestDispatcher(LIST).forward(request, response);
                return;
        }
        request.setAttribute("meal", meal);
        request.getRequestDispatcher(forward).forward(request, response);
    }

}
