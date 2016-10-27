package dao;

import jdbc.JdbcTemplate;
import jdbc.Template;

public class TemplateManagerImpl implements TemplateManager {
    private static final TemplateManagerImpl ourInstance = new TemplateManagerImpl();
    private final Template template;


    public static TemplateManagerImpl getInstance() {
        return ourInstance;
    }

    private TemplateManagerImpl() {
        template = new JdbcTemplate();
    }

    @Override
    public Template getTemplate() {
        return template;
    }
}
