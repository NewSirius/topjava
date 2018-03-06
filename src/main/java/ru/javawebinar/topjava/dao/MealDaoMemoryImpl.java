package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.List;
import java.util.stream.Collectors;

public class MealDaoMemoryImpl implements MealDao {
    @Override
    public void add(Meal meal) {
        MealsUtil.meals.add(meal);
    }

    @Override
    public void remove(int id) {
        MealsUtil.meals.removeIf(meal -> meal.getId() == id);
    }

    @Override
    public void update(int id, Meal meal) {

        MealsUtil.meals.set(id, meal);

    }

    @Override
    public Meal getById(int id) {
        List<Meal> list = MealsUtil.meals.stream()
                .filter(meal -> meal.getId() == id)
                .collect(Collectors.toList());
        return list.get(0);

    }

    @Override
    public int getListIndex(Meal meal) {
        return MealsUtil.meals.indexOf(meal);
    }


}
