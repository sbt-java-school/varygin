package lesson26.home.dao;

import java.util.List;

public interface EntityDao {
    Object save(Object object);

    Object getById(Long id);

    void delete(Object object);

    List getList();
}
