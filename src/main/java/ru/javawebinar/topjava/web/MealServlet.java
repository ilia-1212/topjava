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
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);
    private static final String INSERT_OR_EDIT = "WEB-INF/editMeal.jsp";
    private static final String VIEW = "WEB-INF/viewMeal.jsp";
    private static final String LIST = "WEB-INF/listMeal.jsp";
    private Storage<Meal> storage;

    @Override
    public void init() throws ServletException {
        storage = new MealStorage();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("UTF-8");
        String id = req.getParameter("id");
        String dateTime = req.getParameter("dateTime");
        String description = req.getParameter("description");
        String calories = req.getParameter("calories");

        log.info("MealServlet doPost parameters: id = {}, dateTime = {}, description = {}, calories = {}"
                , id, dateTime, description, calories);

        Meal meal = new Meal(TimeUtil.fromHtml(dateTime), description, Integer.parseInt(calories));

        if (id.isEmpty()) {
            storage.add(meal);
            log.info("MealServlet storage.add");
        } else {
            meal.setId(Integer.valueOf(id));
            storage.update(meal);
            log.info("MealServlet storage.update");
        }
        resp.sendRedirect("meals");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String forward = "";
        String action = (request.getParameter("action") == null ? "" : request.getParameter("action"));
        String id = request.getParameter("id");
        log.info("MealServlet doGet action = {}, id = {}", action, id);

        Meal meal;
        switch (action) {
            case "delete": {
                storage.delete(Integer.parseInt(id));
                response.sendRedirect("meals");
                return;
            }
            case "add": {
                forward = INSERT_OR_EDIT;
                meal = new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 0);
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
