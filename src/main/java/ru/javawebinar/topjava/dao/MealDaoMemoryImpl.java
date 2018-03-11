package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class MealDaoMemoryImpl implements MealDao {

    private static AtomicInteger idCount = new AtomicInteger(0);
    private final Map<Integer, Meal> mealsMap = new ConcurrentHashMap<>();

    {
        mealsMap.put(idCount.get(), new Meal(idCount.get(), LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500));
        mealsMap.put(idCount.incrementAndGet(), new Meal(idCount.get(), LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000));
        mealsMap.put(idCount.incrementAndGet(), new Meal(idCount.get(), LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500));
        mealsMap.put(idCount.incrementAndGet(), new Meal(idCount.get(), LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000));
        mealsMap.put(idCount.incrementAndGet(), new Meal(idCount.get(), LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500));
        mealsMap.put(idCount.incrementAndGet(), new Meal(idCount.get(), LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510));
    }

    @Override
    public Meal add(Meal meal) {
        meal.setId(idCount.incrementAndGet());
        return mealsMap.put(idCount.get(), meal);
    }

    @Override
    public void remove(int id) {
        mealsMap.remove(id);
    }

    @Override
    public Meal update(Meal meal) {
        return mealsMap.put(meal.getId(), meal);
    }

    @Override
    public Meal getById(int id) {
        return mealsMap.get(id);
    }

    @Override
    public List<Meal> getAll() {
        return new ArrayList<>(mealsMap.values());
    }
}
