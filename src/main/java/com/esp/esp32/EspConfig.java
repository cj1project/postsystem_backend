/*package com.esp.esp32;

import com.esp.models.User;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

import javax.sql.DataSource;

@Configuration
public class EspConfig {
    @Bean
    public LocalContainerEntityManagerFactoryBean espEntityManagerFactory(
            EntityManagerFactoryBuilder builder) {
        return builder
                .dataSource(espDataSource())
                .packages(Esp.class)
                .persistenceUnit("esp")
                .build();
    }

    @Bean
    public DataSource espDataSource() {
        DriverManagerDataSource ds = new DriverManagerDataSource();
        ds.setDriverClassName("com.mysql.cj.jdbc.Driver");
        ds.setUsername("root");
        ds.setPassword("root");
        ds.setUrl("jdbc:mysql://localhost:3306/postbox?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC");
        return ds;
    }
}*/
