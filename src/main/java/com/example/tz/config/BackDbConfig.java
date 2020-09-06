package com.example.tz.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.core.task.TaskExecutor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.HashMap;

/**
 * @author Andrey Nikola
 * @version 1.0
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "backEntityManagerFactory",
        transactionManagerRef = "backTransactionManager",
        basePackages = { "com.example.tz.back.repository" }
)
public class BackDbConfig {

    @Autowired
    private Environment env;

    /**
     * Bean Executor для параллельной обработки транзакций
     * @return TaskExecutor
     */
    @Primary
    @Bean(name = "transferExecutor")
    public TaskExecutor transferExecutor(){
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(512);
        executor.setThreadNamePrefix("TransactionTransferExecutor-");
        executor.initialize();
        return executor;
    }

    /**
     * DataSource Bean
     * @return DataSource
     */
    @Bean(name = "backDataSource")
    @ConfigurationProperties(prefix = "back.datasource")
    public DataSource backDataSource() {
        return DataSourceBuilder.create().build();
    }

    /**
     * LocalContainerEntityManagerFactoryBean Bean
     * @return LocalContainerEntityManagerFactoryBean
     */
    @Bean(name = "backEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean em
                = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(backDataSource());
        em.setPackagesToScan(new String[] { "com.example.tz.back.domain" });
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        HashMap<String, Object> properties = new HashMap<>();
        properties.put("hibernate.hbm2ddl.auto",
                env.getProperty("spring.jpa.hibernate.ddl-auto"));
        properties.put("hibernate.dialect",
                env.getProperty("spring.jpa.properties.hibernate.dialect"));
        em.setJpaPropertyMap(properties);

        return em;
    }

    /**
     * PlatformTransactionManager Bean
     * @return PlatformTransactionManager
     */
    @Bean(name = "backTransactionManager")
    public PlatformTransactionManager transactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
        return transactionManager;
    }

}
