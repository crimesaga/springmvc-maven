package inf;

import java.util.Collection;
import java.util.List;

import org.hibernate.LockMode;

public interface GenericDAO<T, ID> {

    T findByAttribute(String attribute, Object value);

    T findByAttribute(String attribute, Object value, LockMode mode);

    List<T> findAllByAttribute(String attribute, Object value);

    T getById(ID id, boolean lock);

    List<T> findAllByAttribute(String attribute, Object value, LockMode mode);

    T findById(ID id, boolean lock);

    List<T> findAll();

    long countAll();

    long deleteAll();

    void deleteAllCascade();

    void lockTable();

    String getTableName();

    void unlockAllTables();

    List<T> findByExample(T exampleInstance, String[] excludeProperty);

    T makePersistent(T entity);

    T makePersistentBySave(T entity);

    void makePersistent(Collection<T> entities);

    void makePersistentByMerge(Collection<T> entities);

    void makePersistentBySave(Collection<T> entities);

    void makeTransient(T entity);

    void makeTransient(Collection<T> entities);

    T merge(T detachedEntity);

    void evict(T entity);

    void evictId(ID id);

    void flush();

    void clear();

}
