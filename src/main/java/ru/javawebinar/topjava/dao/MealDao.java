package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

public interface MealDao {
    void add(Meal meal);
    void remove(int id);
    void update(int id, Meal meal);
    Meal getById(int id);
    int getListIndex(Meal meal);
}
