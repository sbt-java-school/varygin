package lesson24.db;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface Model {
    Optional<?> getById(Long id);

    Long create(Object model);

    boolean update(Object model);

    boolean remove(Long id);

    Optional<List<?>> getList();

    Optional<List<?>> getList(String field, String value);
}
