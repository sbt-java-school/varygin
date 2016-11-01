package lesson24.db;

import lesson24.db.configuration.ApplicationConfiguration;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.Closeable;

public class DaoFactory implements Closeable {
    private final AnnotationConfigApplicationContext context;
    public DaoFactory() {
        this.context = new AnnotationConfigApplicationContext(ApplicationConfiguration.class);
    }

    public <T> T create(Class<? extends T> aClass) {
        return context.getBean(aClass);
    }

    public void close() {
        context.close();
    }
}
