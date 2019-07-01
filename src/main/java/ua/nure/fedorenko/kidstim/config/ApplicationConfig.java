package ua.nure.fedorenko.kidstim.config;

import com.cloudinary.Cloudinary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableTransactionManagement
@ComponentScan({"ua.nure.fedorenko.kidstim"})
@PropertySource(value = {"classpath:application.properties"})
public class ApplicationConfig {
    @Autowired
    private Environment environment;

    @Bean
    public CommonsMultipartResolver multipartResolver() {
        System.out.println("test");
        CommonsMultipartResolver multipart = new CommonsMultipartResolver();
        multipart.setMaxUploadSize(-1);
        return multipart;
    }
/*
    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(environment.getRequiredProperty("database.driver"));
        dataSource.setUrl(environment.getRequiredProperty("database.url"));
        dataSource.setUsername(environment.getRequiredProperty("database.user"));
        dataSource.setPassword(environment.getRequiredProperty("database.password"));
        return dataSource;
    }
*/
    @Bean
    public Cloudinary cloudinary() {
        Map config = new HashMap();
        config.put("cloud_name", "dibxjqjtz");
        config.put("api_key", "146292983354175");
        config.put("api_secret", "C9p4xSyuFls8DVMu9Hy3iThR0kg");
        return new Cloudinary(config);
    }
}
