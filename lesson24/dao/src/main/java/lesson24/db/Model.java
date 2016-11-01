package lesson24.db;

import java.util.List;
import java.util.Optional;

public interface Model {
    Optional<Object> getById(Long id);

    Long create(Object table);

    boolean update(Object table);

    Optional<List<Object>> getList();

    Optional<List<Object>> getList(String field, String value);
}
