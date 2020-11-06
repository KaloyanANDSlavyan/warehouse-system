package system.backend.dao;

import java.util.List;

public interface DAO<T> {
    void save(T object);
    List<T> selectAll(Class<T> c);
    T findBy2Values(Class<T> c, String column1, String column2, String value1, String value2);
}
