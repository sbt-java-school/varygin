package lesson26.home.web.config;

import liquibase.integration.spring.SpringLiquibase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

/**
 * Конфигурационный класс приложения.
 * Использует файл настроект базы данных
 * для создания подключения к БД (jdbc.properties).
 * В файле настроек также находятся настройки Hibernate
 * для создания менеджера сущностей.
 * <p>
 * Для создания базы данных и таблиц используется liquibase
 * Конфигурационный файл для liquibase - database.xml
 * SQL файлы импорта начальных ингредиентов и единиц измерения
 * находятся в папке import ресурсов проекта.
 */
@Configuration
@EnableTransactionManagement
@PropertySource("classpath:jdbc.properties")
@ComponentScan("lesson26.home.dao")
public class DaoConfig {
    private static final String PACKAGE_TO_SCAN = "lesson26.home.dao.entities";

    private final Environment environment;

    @Autowired
    public DaoConfig(Environment environment) {
        this.environment = environment;
    }

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource(
                environment.getProperty("jdbc.dbUrl"),
                environment.getProperty("jdbc.username"),
                environment.getProperty("jdbc.password")
        );
        driverManagerDataSource.setDriverClassName(
                environment.getProperty("jdbc.driverClassName"));

        return driverManagerDataSource;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource) {
        LocalContainerEntityManagerFactoryBean entityManager = new LocalContainerEntityManagerFactoryBean();
        entityManager.setDataSource(dataSource);
        entityManager.setPackagesToScan(PACKAGE_TO_SCAN);

        JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        entityManager.setJpaVendorAdapter(vendorAdapter);
        entityManager.setJpaProperties(additionalProperties());

        return entityManager;
    }

    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
        return new PersistenceExceptionTranslationPostProcessor();
    }

    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(emf);
        return transactionManager;
    }

    @Bean
    public SpringLiquibase liquibase(DataSource dataSource) {
        SpringLiquibase liquibase = new SpringLiquibase();

        liquibase.setDataSource(dataSource);
        liquibase.setChangeLog("classpath:database.xml");

        return liquibase;
    }

    @Bean
    public String staticPath() {
        String staticPath = environment.getProperty("site.static.path");
        if (staticPath.isEmpty()) {
            staticPath = System.getProperty("user.dir");
        }
        return staticPath;
    }

    private Properties additionalProperties() {
        return new Properties() {
            {
                setProperty("hibernate.show_sql",
                        environment.getProperty("hibernate.show_sql"));

                setProperty("hibernate.connection.charSet",
                        environment.getProperty("hibernate.charSet"));

                setProperty("hibernate.connection.characterEncoding",
                        environment.getProperty("hibernate.charSet"));

                setProperty("hibernate.dialect",
                        environment.getProperty("hibernate.dialect"));

//                setProperty("hibernate.hbm2ddl.auto", "create-drop");
            }
        };
    }
}
