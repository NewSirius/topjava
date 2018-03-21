package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

import static ru.javawebinar.topjava.MealTestData.MEAL1;
import static ru.javawebinar.topjava.MealTestData.MEAL2;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

import static ru.javawebinar.topjava.MealTestData.*;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    static {
        // Only for postgres driver logging
        // It uses java.util.logging and logged via jul-to-slf4j bridge
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private MealService service;

    @Test
    public void get() {
        Meal mealExpected = new Meal(MEAL1.getId(), LocalDateTime.of(2018, Month.MARCH, 21, 7, 0, 0), "завтрак", 500);
        assertMatch(service.get(MEAL1.getId(), USER_ID), mealExpected);
    }

    @Test(expected = NotFoundException.class)
    public void getNotFound() throws Exception {
        service.get(222, USER_ID);
    }

    @Test
    public void delete() {
        service.delete(MEAL4.getId(), USER_ID);
        List<Meal> expectedList = Arrays.asList(MEAL1, MEAL2, MEAL3, MEAL5, MEAL6, MEAL7);
        sortByDate(expectedList);
        assertMatch(service.getAll(USER_ID), expectedList);
    }

    @Test(expected = NotFoundException.class)
    public void notFoundDelete() throws Exception {
        service.delete(222, USER_ID);
    }

    @Test
    public void getBetweenDates() {
        List<Meal> actual = service.getBetweenDates(LocalDate.of(2018, 3, 21), LocalDate.of(2018,3,21), USER_ID);
        List<Meal> expected = Arrays.asList(MEAL1, MEAL2, MEAL3);
        sortByDate(expected);
        assertMatch(actual, expected);
    }

    @Test
    public void getBetweenDateTimes() {
        List<Meal> actual = service.getBetweenDateTimes(LocalDateTime.of(2018, Month.MARCH, 21, 7, 0, 0),
                LocalDateTime.of(2018, Month.MARCH, 22, 13, 0, 0), USER_ID);
        List<Meal> expected = Arrays.asList(MEAL1, MEAL2, MEAL3, MEAL4, MEAL5);
        sortByDate(expected);
        assertMatch(actual, expected);
    }

    @Test
    public void getAll() {
        List<Meal> listActual = service.getAll(USER_ID);
        List<Meal> listExpected = Arrays.asList(MEAL1, MEAL2, MEAL3, MEAL4, MEAL5, MEAL6, MEAL7);
        sortByDate(listExpected);
        assertMatch(listActual, listExpected);
    }

    @Test
    public void update() {
    Meal expected = new Meal(MEAL1);
    expected.setDescription("обед");
    expected.setCalories(200);

    service.update(expected, USER_ID);
    assertMatch(service.get(MEAL1.getId(), USER_ID), expected);
    }

    @Test(expected = NotFoundException.class)
    public void notFoundUpdate() throws Exception {
        service.update(MEAL1, ADMIN_ID);
    }

    @Test
    public void create() {
        Meal meal = new Meal(null, LocalDateTime.of(2018, Month.MARCH, 23, 11, 34, 0), "завтрак", 666);
        Meal created = service.create(meal, USER_ID);
        meal.setId(created.getId());
        List<Meal> expectedList = Arrays.asList(meal, MEAL1, MEAL2, MEAL3, MEAL4, MEAL5, MEAL6, MEAL7 );
        sortByDate(expectedList);
        assertMatch(service.getAll(USER_ID), expectedList);
    }
}