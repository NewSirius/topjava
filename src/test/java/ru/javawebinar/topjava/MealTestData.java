package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Comparator;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class MealTestData {

    public static final Meal MEAL1 = new Meal(100002, LocalDateTime.of(2018, Month.MARCH, 21, 7, 0, 0), "завтрак", 500);
    public static final Meal MEAL2 = new Meal(100003, LocalDateTime.of(2018, Month.MARCH, 21, 12, 0, 0), "обед", 700);
    public static final Meal MEAL3 = new Meal(100004, LocalDateTime.of(2018, Month.MARCH, 21, 19, 0, 0), "ужин", 1000);
    public static final Meal MEAL4 = new Meal(100005, LocalDateTime.of(2018, Month.MARCH, 22, 7, 0, 0), "завтрак", 300);
    public static final Meal MEAL5 = new Meal(100006, LocalDateTime.of(2018, Month.MARCH, 22, 12, 0, 0), "обед", 700);
    public static final Meal MEAL6 = new Meal(100007, LocalDateTime.of(2018, Month.MARCH, 22, 16, 0, 0), "полдник", 400);
    public static final Meal MEAL7 = new Meal(100008, LocalDateTime.of(2018, Month.MARCH, 22, 19, 0, 0), "ужин", 900);
    public static final Meal MEAL8 = new Meal(100009, LocalDateTime.of(2018, Month.MARCH, 12, 0, 0, 0), "ужин админ", 700);


    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).isEqualToComparingFieldByField(expected);
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).usingFieldByFieldElementComparator().isEqualTo(expected);

    }

    public static void sortByDate(List<Meal> list) {
        list.sort(Comparator.comparing(Meal::getDateTime).reversed());
    }
}
