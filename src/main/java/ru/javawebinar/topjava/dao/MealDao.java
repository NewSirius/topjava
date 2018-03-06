package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public interface MealDao {
    void add(Meal meal);
    void remove(int id);
    Meal update(int id, Meal meal);
    Meal getById(int id);
    List<Meal> getAll();

}
