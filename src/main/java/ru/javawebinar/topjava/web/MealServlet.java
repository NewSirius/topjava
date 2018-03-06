package ru.javawebinar.topjava.web;

import ru.javawebinar.topjava.dao.MealDao;
import ru.javawebinar.topjava.dao.MealDaoMemoryImpl;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

public class MealServlet extends HttpServlet {
    private MealDao mealDao = new MealDaoMemoryImpl();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");

        if (" ".equals(request.getParameter("id"))) {
            Meal meal = new Meal(
                    LocalDateTime.parse(request.getParameter("date")),
                    request.getParameter("description"),
                    Integer.parseInt(request.getParameter("calories")),
                    MealsUtil.getIdIterator().getAndIncrement());
            mealDao.add(meal);
            response.sendRedirect("meals");
        } else {
            Meal meal = mealDao.getById(Integer.parseInt(request.getParameter("id")));
            int index = mealDao.getListIndex(meal);
            meal.setCalories(Integer.parseInt(request.getParameter("calories")));
            meal.setDateTime(LocalDateTime.parse(request.getParameter("date")));
            meal.setDescription(request.getParameter("description"));
            mealDao.update(index, meal);
            response.sendRedirect("meals");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String action = request.getParameter("action");
        if (action != null) {
            if (action.equalsIgnoreCase("remove")) {
                mealDao.remove(Integer.parseInt(request.getParameter("id")));
                response.sendRedirect("meals");
                return;
            } else if (action.equalsIgnoreCase("edit")) {
                Meal meal = mealDao.getById(Integer.parseInt(request.getParameter("id")));
                request.setAttribute("meal", meal);
            }
        }
        request.setAttribute("listMeals", MealsUtil.getListWithExceeded(MealsUtil.meals, 2000));
        request.getRequestDispatcher("/meals.jsp").forward(request, response);
    }
}
