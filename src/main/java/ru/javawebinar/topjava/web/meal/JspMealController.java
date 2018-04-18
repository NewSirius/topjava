package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.ValidationUtil;

import javax.servlet.http.HttpServletRequest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalDate;
import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalTime;
import static ru.javawebinar.topjava.util.Util.orElse;

@Controller
@RequestMapping("/meals")
public class JspMealController {
    private static final Logger log = LoggerFactory.getLogger(JspMealController.class);

    @Autowired
    private MealService mealService;

    @RequestMapping()
    public String all(Model model) {
        int userId = AuthorizedUser.id();
        log.info("getAll for user {}", userId);
        model.addAttribute("meals", MealsUtil.getWithExceeded(mealService.getAll(userId), AuthorizedUser.getCaloriesPerDay()));
        return "meals";
    }

    @PostMapping(value = "/delete")
    public String delete(@RequestParam(value = "id") int id) {
        int userId = AuthorizedUser.id();
        log.info("delete meal {} for user {}", id, userId);
        mealService.delete(id, userId);
        return "redirect:/meals";
    }

    @PostMapping("/update")
    public String updateGet(@RequestParam(value = "id") int id, Model model) {
        Meal meal = mealService.get(id, AuthorizedUser.id());
        model.addAttribute("meal", meal);
        return "mealForm";
    }

    @PostMapping("/mealForm")
    public String updateCreatePost(HttpServletRequest request) {
        Meal meal = new Meal(
                LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.parseInt(request.getParameter("calories")));
        int userId = AuthorizedUser.id();
        if (request.getParameter("id").isEmpty()) {
            log.info("create {} for user {}", meal, userId);
            mealService.create(meal, userId);
        } else {
            ValidationUtil.assureIdConsistent(meal, Integer.parseInt(request.getParameter("id")));
            log.info("update {} for user {}", meal, userId);
            mealService.update(meal, userId);
        }
        return "redirect:/meals";
    }

    @GetMapping("/create")
    public String createGet(Model model) {
        Meal meal = new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000);
        model.addAttribute("meal", meal);
        model.addAttribute("createTag", true);
        return "mealForm";
    }

    @PostMapping()
    public String filter(HttpServletRequest request) {
        LocalDate startDate = parseLocalDate(request.getParameter("startDate"));
        LocalDate endDate = parseLocalDate(request.getParameter("endDate"));
        LocalTime startTime = parseLocalTime(request.getParameter("startTime"));
        LocalTime endTime = parseLocalTime(request.getParameter("endTime"));
        int userId = AuthorizedUser.id();
        log.info("filter dates({} - {}) time({} - {}) for user {}", startDate, endDate, startTime, endTime, userId);
        List<Meal> mealsDateFiltered = mealService.getBetweenDates(
                orElse(startDate, DateTimeUtil.MIN_DATE), orElse(endDate, DateTimeUtil.MAX_DATE), userId);

        List<MealWithExceed> mealWithExceeds = MealsUtil.getFilteredWithExceeded(mealsDateFiltered, AuthorizedUser.getCaloriesPerDay(),
                orElse(startTime, LocalTime.MIN), orElse(endTime, LocalTime.MAX));
        request.setAttribute("meals", mealWithExceeds);
        return "meals";
    }
}
