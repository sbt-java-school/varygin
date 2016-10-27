package migrations;

import exception.BusinessException;
import jdbc.JdbcTemplate;
import jdbc.Template;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;

public class DatabaseMigrations {

    private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseMigrations.class);
    private final Template template;

    public DatabaseMigrations() {
        this.template = new JdbcTemplate();
    }

    public static void main(String[] args) {
        DatabaseMigrations migrations = new DatabaseMigrations();
        migrations.executeScript("sql/drop");
        migrations.executeScript("sql");
    }

    /**
     * Выполняет sql скрипт/скрипты из файла/папки path
     *
     * @param path путь до файла/папки
     */
    private void executeScript(String path) {
        URL resource = this.getClass().getClassLoader().getResource(path);
        try {
            if (resource != null) {
                File pathFile = new File(resource.toURI());
                LOGGER.debug(pathFile.toString());
                if (pathFile.isFile()) {
                    executeFile(pathFile);
                } else if (pathFile.isDirectory()) {
                    Collection<File> files = FileUtils.listFiles(pathFile, null, false);
                    files.forEach(this::executeFile);
                }
            } else {
                LOGGER.debug("Directory or file " + path + " isn't exist");
            }
        } catch (URISyntaxException e) {
            throw new BusinessException("Wrong path: " + resource.toString());
        }
    }

    private void executeFile(File file) {
        try {
            LOGGER.info("New file execution: {}", file.getName());
            if (executeSql(FileUtils.readFileToString(file))) {
                throw new BusinessException("Can't execute file: " + file.getName());
            }
        } catch (SQLException e) {
            throw new BusinessException("Bad script: " + file.getName(), e);
        } catch (IOException e) {
            throw new BusinessException("Can't read file: " + file.getName(), e);
        }
    }

    private boolean executeSql(String sql) throws SQLException {
        return template.execute(connection -> {
            Statement statement = connection.createStatement();
            LOGGER.info("-------------Start Migration-------------------");
            LOGGER.info("SQL: " + sql);
            boolean result = statement.execute(sql);
            LOGGER.info("-------------Migration complete----------------");
            return result;
        });
    }
}
