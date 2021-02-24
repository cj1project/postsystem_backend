package com.esp.user;

import com.esp.models.Esp;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.web.reactive.function.client.WebClient;

import javax.sql.DataSource;
import java.io.File;
import java.util.Properties;

@Configuration
public class UserConfig {
    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource ds = new DriverManagerDataSource();
        ds.setDriverClassName("com.mysql.cj.jdbc.Driver");
        ds.setUsername("root");
        ds.setPassword("root");
        ds.setUrl("jdbc:mysql://localhost:3306/postboxsys?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC");
        return ds;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setDataSource(dataSource());
        entityManagerFactoryBean.setPackagesToScan("com.esp.models");
        entityManagerFactoryBean.setPersistenceUnitName("models");
        HibernateJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
        entityManagerFactoryBean.setJpaVendorAdapter(jpaVendorAdapter);
        entityManagerFactoryBean.setJpaProperties(hibernateProperties());
        jpaVendorAdapter.setGenerateDdl(true);
        return entityManagerFactoryBean;
    }

    private Properties hibernateProperties() {
        Properties properties = new Properties();
        properties.setProperty("hibernate.ddl-auto", "create-drop");
        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
        properties.setProperty("hibernate.format_sql", "true");

       return properties;
    }

    //Server Route
    @Bean
    public WebClient localApiClient() {
        return WebClient.create("http://localhost:8080");
    }



    // @Bean
    public LocalContainerEntityManagerFactoryBean userEntityManagerFactory(
            EntityManagerFactoryBuilder builder) {
        return builder
                .dataSource(dataSource())
                .packages("com.esp.models")
                .persistenceUnit("models")
                .build();
    }

    //@Bean
    public LocalContainerEntityManagerFactoryBean espEntityManagerFactory(
            EntityManagerFactoryBuilder builder) {
        return builder
                .dataSource(dataSource())
                .packages(Esp.class)
                .persistenceUnit("esp")
                .build();
    }
/* @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation(){
        return new PersistenceExceptionTranslationPostProcessor();
    }
// @Bean
    public PlatformTransactionManager transactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());

        return transactionManager;
    }
    // it should be private not public?
    private Properties hibernateProperties() {
        Properties properties = new Properties();
        properties.setProperty("hibernate.ddl-auto", "create-drop");
        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
        properties.setProperty("hibernate.format_sql", "true");

        /*spring.jpa.hibernate.ddl-auto = create-drop
        spring.jpa.show-sql=true
        spring.jpa.properties.hibernate.dialect = org.hibernate.dialect.MySQL5Dialect
        spring.jpa.properties.hibernate.format_sql=true*/

        /*properties.put("hibernate.dialect", env.getRequiredProperty("hibernate.dialect"));
        properties.put("hibernate.show_sql", env.getRequiredProperty("hibernate.show_sql"));
        properties.put("hibernate.ddl-auto", env.getRequiredProperty("hibernate.ddl-auto"));

        properties.put("hibernate.connection.driver_class", env.getRequiredProperty("hibernate.connection.driver_class"));
        properties.put("hibernate.connection.url", env.getRequiredProperty("hibernate.connection.url"));
        properties.put("hibernate.connection.username", env.getRequiredProperty("hibernate.connection.username"));
        properties.put("hibernate.connection.password", env.getRequiredProperty("hibernate.connection.password"));
        properties.put("spring.jpa.open-in-view", env.getRequiredProperty("spring.jpa.open-in-view"));*/
       /* return properties;
    }*/
}
