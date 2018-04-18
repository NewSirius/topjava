package ru.javawebinar.topjava.web.meal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;

import javax.servlet.http.HttpServletRequest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalDate;
import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalTime;

@Controller
@RequestMapping("/meals")
public class JspMealController extends MealRestController {

    public JspMealController(MealService service) {
        super(service);
    }

    @RequestMapping()
    public String all(Model model) {
        model.addAttribute("meals", super.getAll());
        return "meals";
    }

    @PostMapping(value = "/delete")
    public String deleteMeal(@RequestParam(value = "id") int id) {
        super.delete(id);
        return "redirect:/meals";
    }

    @PostMapping("/update")
    public String updateGet(@RequestParam(value = "id") int id, Model model) {
        model.addAttribute("meal", super.get(id));
        return "mealForm";
    }

    @PostMapping("/mealForm")
    public String updateCreatePost(HttpServletRequest request) {
        Meal meal = new Meal(
                LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.parseInt(request.getParameter("calories")));
        if (request.getParameter("id").isEmpty()) {
            super.create(meal);
        } else {
            super.update(meal, Integer.parseInt(request.getParameter("id")));
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
        request.setAttribute("meals", super.getBetween(startDate, startTime, endDate, endTime));
        return "meals";
    }
}
