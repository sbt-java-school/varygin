package lesson26.home.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

/**
 * Конфигурационный класс веб-приложения
 */
@Configuration
@EnableWebMvc
@ComponentScan("lesson26.home")
public class WebAppConfig extends WebMvcConfigurerAdapter {

    /**
     * Определяем папку с ресурсами
     *
     * @param registry instance of resource handler
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/WEB-INF/pages/**")
                .addResourceLocations("/WEB-INF/pages/");
        registry.addResourceHandler("/resources/**")
                .addResourceLocations("/resources/");
    }

    @Bean
    public InternalResourceViewResolver setupViewResolver() {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver("/WEB-INF/pages/", ".jsp");
        resolver.setViewClass(JstlView.class);
        return resolver;
    }

    /**
     * Бин для загрузки картинок на сервер
     * Максимальный допустимый размер загружаемых файлов: 3 мб
     *
     * @return инстанс обработчика загрузки картинок
     */
    @Bean
    public CommonsMultipartResolver multipartResolver() {
        CommonsMultipartResolver resolver = new CommonsMultipartResolver();
        resolver.setMaxUploadSize(3000000);
        return resolver;
    }
}
