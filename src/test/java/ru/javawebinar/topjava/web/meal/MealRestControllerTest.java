package ru.javawebinar.topjava.web.meal;

import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.TestUtil;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.AbstractControllerTest;
import ru.javawebinar.topjava.web.json.JsonUtil;

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.javawebinar.topjava.MealTestData.*;


public class MealRestControllerTest extends AbstractControllerTest {
    private final static String MEAL_URL = MealRestController.MEAL_URL + "/";

    @Test
    public void testGet() throws Exception {
        mockMvc.perform(get(MEAL_URL + MealTestData.MEAL1_ID))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(MEAL1));
    }

    @Test
    public void testDelete() throws Exception {
        mockMvc.perform(delete(MEAL_URL + MealTestData.MEAL1_ID))
                .andExpect(status().isNoContent())
                .andDo(print());
        assertMatch(mealService.getAll(AuthorizedUser.id()), Arrays.asList(MEAL6, MEAL5, MEAL4, MEAL3, MEAL2));

    }

    @Test
    public void getAll() throws Exception {
        mockMvc.perform(get(MealRestController.MEAL_URL))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(MEAL6, MEAL5, MEAL4, MEAL3, MEAL2, MEAL1));
    }

    @Test
    public void createWithLocation() throws Exception {
        Meal expected = new Meal(LocalDateTime.now(), "Завтракк", 666);
        ResultActions action = mockMvc.perform(post(MEAL_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(expected)))
                .andExpect(status().isCreated());

        Meal returned = TestUtil.readFromJson(action, Meal.class);
        expected.setId(returned.getId());

        assertMatch(returned, expected);
        assertMatch(mealService.getAll(AuthorizedUser.id()), Arrays.asList(expected, MEAL6, MEAL5, MEAL4, MEAL3, MEAL2, MEAL1));
    }

    @Test
    public void update() throws Exception {
        Meal expected = new Meal(MEAL1);
        expected.setCalories(777);
        mockMvc.perform(put(MEAL_URL + MEAL1_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(expected)))
                .andExpect(status().isOk());

        assertMatch(mealService.getAll(AuthorizedUser.id()), Arrays.asList(MEAL6, MEAL5, MEAL4, MEAL3, MEAL2, expected));
    }

    @Test
    public void getBetween() throws Exception {
        String startDateTime = "2015-01-03T10:15:30";
        String endDateTime = "2015-12-04T21:15:30";

        ResultActions action = mockMvc.perform(post(MEAL_URL + "filter")
                .param("startDate", startDateTime)
                .param("startTime", startDateTime)
                .param("endDate", endDateTime)
                .param("endTime", endDateTime)
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(
                        MealsUtil.createWithExceed(MEAL6, true),
                        MealsUtil.createWithExceed(MEAL5, true),
                        MealsUtil.createWithExceed(MEAL3, false),
                        MealsUtil.createWithExceed(MEAL2, false)));

    }
}