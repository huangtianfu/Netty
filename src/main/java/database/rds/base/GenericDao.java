package database.rds.base;

import org.hibernate.criterion.Criterion;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * Created by parallels on 6/4/16.
 */
public interface GenericDao <T, PK extends Serializable> {

    /* --------------< save >----------------------*/
    void save(T entity) throws RdsDatabaseException;

    void saveOrUpdate(T entity) throws RdsDatabaseException;


    /* -------------< update >-------------*/
    void update(T entity) throws RdsDatabaseException;

    void update(String sql, SqlParam... params) throws RdsDatabaseException;

    /* --------------< delete >----------------------*/
    void delete(T entity) throws RdsDatabaseException;

    void delete(String sql, SqlParam... params) throws RdsDatabaseException;

    void deleteAll(Collection<T> entitys) throws RdsDatabaseException;

    /* --------------< select >----------------------*/
    List<T> findAll() throws RdsDatabaseException;

    T findByPK(final PK id) throws RdsDatabaseException;

    List find(String sql, SqlParam... params) throws RdsDatabaseException;

    List find(String sql, int start, int limit, SqlParam... params) throws RdsDatabaseException;

    Object findUnique(String sql, SqlParam... params) throws RdsDatabaseException;

    List<T> findByCriteria(Criterion... criterions) throws RdsDatabaseException;

    List<T> findByProperty(String propertyName, Object value) throws RdsDatabaseException;

    T findUniqueByProperty(String propertyName, Object value) throws RdsDatabaseException;

    /* --------------< other >------------------------*/
    Long count(String sql, SqlParam... params) throws RdsDatabaseException;

    Long count(String column) throws RdsDatabaseException;

    Long countUnique(String column) throws RdsDatabaseException;
}
