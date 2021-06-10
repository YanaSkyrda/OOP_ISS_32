package dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface GenericDao<T> extends AutoCloseable {
    Optional<T> create(T entity);

    T findById(long id);

    List<T> findAll();

    Optional<T> update(T entity) throws SQLException;

}
