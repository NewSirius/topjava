package ru.javawebinar.topjava;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.env.AbstractEnvironment;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.datajpa.DataJpaMealRepositoryImpl;
import ru.javawebinar.topjava.repository.datajpa.DataJpaUserRepositoryImpl;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.web.meal.MealRestController;
import ru.javawebinar.topjava.web.user.AdminRestController;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

public class SpringMain {
    public static void main(String[] args) {
        // java 7 Automatic resource management
        System.setProperty(AbstractEnvironment.ACTIVE_PROFILES_PROPERTY_NAME, "datajpa, postgres");
        try (ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml","spring/spring-db.xml")) {
            System.out.println("Bean definition names: " + Arrays.toString(appCtx.getBeanDefinitionNames()));
            AdminRestController adminUserController = appCtx.getBean(AdminRestController.class);
            //adminUserController.create(new User(null, "userName", "email", "password", Role.ROLE_ADMIN));
            adminUserController.create(new User(null, "userName", "newsirius@mail.ru", "password", Role.ROLE_ADMIN));
            System.out.println();


            DataJpaUserRepositoryImpl repository = appCtx.getBean(DataJpaUserRepositoryImpl.class);
            User user = repository.getWithMeals(100002);
            System.out.println(user.getMeals());

            DataJpaMealRepositoryImpl repository1 = appCtx.getBean(DataJpaMealRepositoryImpl.class);
            Meal meal = repository1.getWithUser(100005, 100000);
            System.out.println(meal.getUser().getId());


            MealRestController mealController = appCtx.getBean(MealRestController.class);
            List<MealWithExceed> filteredMealsWithExceeded =
                    mealController.getBetween(
                            LocalDate.of(2015, Month.MAY, 30), LocalTime.of(7, 0),
                            LocalDate.of(2015, Month.MAY, 31), LocalTime.of(11, 0));
            filteredMealsWithExceeded.forEach(System.out::println);
        }
    }
}
