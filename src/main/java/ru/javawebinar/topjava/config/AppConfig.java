package ru.javawebinar.topjava.config;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.repository.UserRepository;
import ru.javawebinar.topjava.repository.datajpa.DataJpaMealRepositoryImpl;
import ru.javawebinar.topjava.repository.datajpa.DataJpaUserRepositoryImpl;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.service.MealServiceImpl;
import ru.javawebinar.topjava.service.UserService;
import ru.javawebinar.topjava.service.UserServiceImpl;
import ru.javawebinar.topjava.web.meal.MealRestController;
import ru.javawebinar.topjava.web.user.AdminRestController;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories("ru.javawebinar.topjava.repository")
public class AppConfig {

    @Bean
    public AdminRestController getAdminRestController() {
        return new AdminRestController();
    }

    @Bean
    public UserService getUserService() {
        return new UserServiceImpl(getUserRepo());
    }

    @Bean
    public UserRepository getUserRepo() {
        return new DataJpaUserRepositoryImpl();
    }

    @Bean
    @Profile(Profiles.DATAJPA)
    public MealRepository getMealRepoDataJpa() {
        return new DataJpaMealRepositoryImpl();
    }

    @Bean
    public MealRestController getMealRestController() {
        return new MealRestController(getMealService());
    }

    @Bean
    public MealService getMealService() {
        return new MealServiceImpl(getMealRepoDataJpa());
    }

    @Bean
    @Profile(Profiles.POSTGRES_DB)
    public DataSource dataSource() {
        DataSource dataSource = new DataSource();

        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl("jdbc:postgresql://localhost:5432/topjava");
        dataSource.setUsername("user");
        dataSource.setPassword("password");

        return dataSource;
    }

    @Bean
    @Profile(Profiles.DATAJPA)
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setDataSource(dataSource());
        HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
        adapter.setShowSql(true);
        entityManagerFactoryBean.setJpaVendorAdapter(adapter);
        entityManagerFactoryBean.setPackagesToScan("ru.javawebinar.**.model");

        return entityManagerFactoryBean;
    }

    @Bean
    @Profile(Profiles.DATAJPA)
    public JpaTransactionManager transactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
        return transactionManager;
    }

}
