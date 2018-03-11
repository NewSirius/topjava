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
import java.time.LocalTime;

public class MealServlet extends HttpServlet {
    private MealDao mealDao;

    @Override
    public void init() throws ServletException {
        mealDao = new MealDaoMemoryImpl();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");

        String date = request.getParameter("date");
        String description = request.getParameter("description");
        String calories = request.getParameter("calories");

        if ("".equals(request.getParameter("id"))) {
            Meal meal = new Meal(
                    LocalDateTime.parse(date),
                    description,
                    Integer.parseInt(calories)
                    );
            mealDao.add(meal);
        } else {
            int id = Integer.parseInt(request.getParameter("id"));
            Meal meal = mealDao.getById(id);
            meal.setCalories(Integer.parseInt(calories));
            meal.setDateTime(LocalDateTime.parse(date));
            meal.setDescription(description);
            mealDao.update(meal);
        }
        response.sendRedirect("meals");
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
        request.setAttribute("listMeals", MealsUtil.getFilteredWithExceeded(mealDao.getAll(), LocalTime.MIN, LocalTime.MAX, 2000));
        request.getRequestDispatcher("/meals.jsp").forward(request, response);
    }
}