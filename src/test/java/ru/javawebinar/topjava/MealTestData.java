package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class MealTestData {

    public static final Meal MEAL1 = new Meal(1, LocalDateTime.of(2015, Month.MAY, 30, 7, 5, 6), "завтрак", 500);
    public static final Meal MEAL2 = new Meal(2, LocalDateTime.of(2015, Month.MAY, 31, 12, 5, 6), "ужин", 700);

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).isEqualToIgnoringGivenFields(expected);
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).isEqualTo(expected);
    }

    public static void sortByDate(List<Meal> list) {
        list.sort(((o1, o2) -> o2.getDateTime().compareTo(o1.getDateTime())));
    }
}
