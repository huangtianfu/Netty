package database.rds.base;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Aaron Sheng on 6/4/16.
 */
public class GenericDaoHibernateImpl <T, PK extends Serializable> extends HibernateDaoSupport implements GenericDao<T, PK> {
    protected Class<T> mEntityClass;
    protected Class<PK> mPKClass;

    public GenericDaoHibernateImpl() {
        Type type = getClass().getGenericSuperclass();
        if(!(type instanceof ParameterizedType)){
            type = getClass().getSuperclass().getGenericSuperclass();
        }
        this.mEntityClass = (Class<T>)((ParameterizedType)type).getActualTypeArguments()[0];
        this.mPKClass = (Class<PK>)((ParameterizedType)type).getActualTypeArguments()[1];
    }

    public GenericDaoHibernateImpl(SessionFactory sessionFactory, Class<T> entityClass, Class<PK> pkClass) {
        super.setSessionFactory(sessionFactory);
        this.mEntityClass = entityClass;
        this.mPKClass = pkClass;
    }

    /* --------------< save start >----------------------*/
    @Override
    public void save(T entity) throws RdsDatabaseException {
        Transaction transaction = null;
        try {
            transaction = super.currentSession().getTransaction();
            transaction.begin();
            super.getHibernateTemplate().save(entity);
            transaction.commit();
        } catch (Exception exception) {
            if (transaction != null) {
                transaction.rollback();
            }
            String log = this.mEntityClass.getSimpleName() + "Dao save(entity) exception!\n";
            RdsDatabaseException databaseException = new RdsDatabaseException(log + exception.getMessage());
            throw databaseException;
        }
    }

    @Override
    public void saveOrUpdate(T entity) throws RdsDatabaseException {
        Transaction transaction = null;
        try {
            transaction = super.currentSession().getTransaction();
            transaction.begin();
            super.getHibernateTemplate().saveOrUpdate(entity);
            transaction.commit();
        } catch (Exception exception) {
            if (transaction != null) {
                transaction.rollback();
            }
            String log = this.mEntityClass.getSimpleName() + "Dao saveOrUpdate(entity) exception!\n";
            RdsDatabaseException databaseException = new RdsDatabaseException(log + exception.getMessage());
            throw databaseException;
        }
    }
    /* --------------< save end >------------------------*/

    /* --------------< update start >-------------------- */
    @Override
    public void update(T entity) throws RdsDatabaseException {
        Transaction transaction = null;
        try {
            transaction = super.currentSession().getTransaction();
            transaction.begin();
            super.getHibernateTemplate().update(entity);
            transaction.commit();
        } catch (Exception exception) {
            if (transaction != null) {
                transaction.rollback();
            }
            String log = this.mEntityClass.getSimpleName() + "Dao update(entity) exception!\n";
            RdsDatabaseException databaseException = new RdsDatabaseException(log + exception.getMessage());
            throw databaseException;
        }
    }

    @Override
    public void update(String sql, SqlParam... params) throws RdsDatabaseException {
        Transaction transaction = null;
        try {
            transaction = super.currentSession().getTransaction();
            transaction.begin();
            createQuery(sql, params).executeUpdate();
            transaction.commit();
        } catch (Exception exception) {
            if (transaction != null) {
                transaction.rollback();
            }
            String log = this.mEntityClass.getSimpleName() + "Dao update(sql, params...) exception!\n";
            RdsDatabaseException databaseException = new RdsDatabaseException(log + exception.getMessage());
            throw databaseException;
        }
    }
    /* --------------< update end >-------------------- */

    /* --------------< delete start >----------------------*/
    @Override
    public void delete(T entity) throws RdsDatabaseException {
        Transaction transaction = null;
        try {
            transaction = super.currentSession().getTransaction();
            transaction.begin();
            super.getHibernateTemplate().delete(entity);
            transaction.commit();
        } catch (Exception exception) {
            if (transaction != null) {
                transaction.rollback();
            }
            String log = this.mEntityClass.getSimpleName() + "Dao delete(entity) exception!\n";
            RdsDatabaseException databaseException = new RdsDatabaseException(log + exception.getMessage());
            throw databaseException;
        }
    }

    @Override
    public void delete(String sql, SqlParam... params) throws RdsDatabaseException {
        Transaction transaction = null;
        try {
            transaction = super.currentSession().getTransaction();
            transaction.begin();
            createQuery(sql, params).executeUpdate();
            transaction.commit();
        } catch (Exception exception) {
            if (transaction != null) {
                transaction.rollback();
            }
            String log = this.mEntityClass.getSimpleName() + "Dao delete(sql, params...) exception!\n";
            RdsDatabaseException databaseException = new RdsDatabaseException(log + exception.getMessage());
            throw databaseException;
        }
    }

    @Override
    public void deleteAll(Collection<T> entitys) throws RdsDatabaseException {
        Transaction transaction = null;
        try {
            transaction = super.currentSession().getTransaction();
            transaction.begin();
            Iterator it = entitys.iterator();
            while (it.hasNext()) {
                super.getHibernateTemplate().delete(it.next());
            }
            transaction.commit();
        } catch (Exception exception) {
            if (transaction != null) {
                transaction.rollback();
            }
            String log = this.mEntityClass.getSimpleName() + "Dao deleteAll(entitys) exception!\n";
            RdsDatabaseException databaseException = new RdsDatabaseException(log + exception.getMessage());
            throw databaseException;
        }
    }
    /* --------------< delete end >------------------------*/

    /* --------------< select start >----------------------*/
    @Override
    public List<T> findAll() throws RdsDatabaseException {
        try {
            return findByCriteria();
        } catch (Exception exception) {
            String log = this.mEntityClass.getSimpleName() + "Dao findAll() exception!\n";
            RdsDatabaseException databaseException = new RdsDatabaseException(log + exception.getMessage());
            throw databaseException;
        }
    }

    @Override
    public T findByPK(final PK id) throws RdsDatabaseException {
        Transaction transaction = null;
        try {
            transaction = super.currentSession().getTransaction();
            transaction.begin();
            T result = (T) super.getHibernateTemplate().get(mEntityClass, id);
            transaction.commit();

            return result;
        } catch (Exception exception) {
            if (transaction != null) {
                transaction.rollback();;
            }
            String log = this.mEntityClass.getSimpleName() + "Dao findByPK(id) exception!\n";
            RdsDatabaseException databaseException = new RdsDatabaseException(log + exception.getMessage());
            throw databaseException;
        }
    }

    @Override
    public List find(String sql, SqlParam... params) throws RdsDatabaseException {
        Transaction transaction = null;
        try {
            transaction = super.currentSession().getTransaction();
            transaction.begin();
            List result = createQuery(sql, params).list();
            transaction.commit();

            return result;
        } catch (Exception exception) {
            if (transaction != null) {
                transaction.rollback();
            }
            String log = this.mEntityClass.getSimpleName() + "Dao find(sql, params...) exception!\n";
            RdsDatabaseException databaseException = new RdsDatabaseException(log + exception.getMessage());
            throw databaseException;
        }
    }

    @Override
    public List find(String sql, int start, int limit, SqlParam... params) throws RdsDatabaseException {
        Transaction transaction = null;
        try {
            transaction = super.currentSession().getTransaction();
            transaction.begin();
            Query query = createQuery(sql, params);
            query.setFirstResult(start);
            query.setMaxResults(limit);
            List result = query.list();
            transaction.commit();

            return result;
        } catch (Exception exception) {
            if (transaction != null) {
                transaction.rollback();
            }
            String log = this.mEntityClass.getSimpleName() + "Dao find(sql, start, limit, params...) exception!\n";
            RdsDatabaseException databaseException = new RdsDatabaseException(log + exception.getMessage());
            throw databaseException;
        }
    }

    @Override
    public Object findUnique(String sql, SqlParam... params) throws RdsDatabaseException {
        Transaction transaction = null;
        try {
            transaction = super.currentSession().getTransaction();
            transaction.begin();
            Object result = createQuery(sql, params).uniqueResult();
            transaction.commit();

            return result;
        } catch (Exception exception) {
            if (transaction != null) {
                transaction.rollback();
            }
            String log = this.mEntityClass.getSimpleName() + "Dao findUnique(sql, params...) exception!\n";
            RdsDatabaseException databaseException = new RdsDatabaseException(log + exception.getMessage());
            throw databaseException;
        }
    }

    @Override
    public List<T> findByCriteria(Criterion... criterions) throws RdsDatabaseException {
        Transaction transaction = null;
        try {
            transaction = super.currentSession().getTransaction();
            transaction.begin();
            List<T> result = (List<T>) createCriteria(criterions).list();
            transaction.commit();

            return result;
        } catch (Exception exception) {
            if (transaction != null) {
                transaction.rollback();
            }
            String log = this.mEntityClass.getSimpleName() + "Dao findByCriteria(criterion...) exception!\n";
            RdsDatabaseException databaseException = new RdsDatabaseException(log + exception.getMessage());
            throw databaseException;
        }
    }

    @Override
    public List<T> findByProperty(String propertyName, Object value) throws RdsDatabaseException {
        Transaction transaction = null;
        try {
            transaction = super.currentSession().getTransaction();
            transaction.begin();
            List<T> result = (List<T>) createCriteria(Restrictions.eq(propertyName, value)).list();
            transaction.commit();

            return result;
        } catch (Exception exception) {
            if (transaction != null) {
                transaction.rollback();
            }
            String log = this.mEntityClass.getSimpleName() + "Dao findByProperty(propertyName, value) exception!\n";
            RdsDatabaseException databaseException = new RdsDatabaseException(log + exception.getMessage());
            throw databaseException;
        }
    }

    @Override
    public T findUniqueByProperty(String propertyName, Object value) throws RdsDatabaseException {
        Transaction transaction = null;
        try {
            transaction = super.currentSession().getTransaction();
            transaction.begin();
            T result = (T) createCriteria(Restrictions.eq(propertyName, value)).uniqueResult();
            transaction.commit();

            return result;
        } catch (Exception exception) {
            if (transaction != null) {
                transaction.rollback();
            }
            String log = this.mEntityClass.getSimpleName() + "Dao findUniqueByProperty(propertyName, value) exception!\n";
            RdsDatabaseException databaseException = new RdsDatabaseException(log + exception.getMessage());
            throw databaseException;
        }
    }
    /* --------------< select end >------------------------*/

    /* --------------< other start >------------------------*/
    @Override
    public Long count(String sql, SqlParam... params) throws RdsDatabaseException {
        Transaction transaction = null;
        try {
            transaction = super.currentSession().getTransaction();
            transaction.begin();
            Object result = createQuery(sql, params).uniqueResult();
            transaction.commit();

            return (Long) result;
        } catch (Exception exception) {
            if (transaction != null) {
                transaction.rollback();
            }
            String log = this.mEntityClass.getSimpleName() + "Dao count(sql, params) exception!\n";
            RdsDatabaseException databaseException = new RdsDatabaseException(log + exception.getMessage());
            throw databaseException;
        }
    }

    @Override
    public Long count(String column) throws RdsDatabaseException {
        Transaction transaction = null;
        try {
            String sql = "SELECT COUNT(" + column + ") FROM " + this.mEntityClass.getSimpleName();

            transaction = super.currentSession().getTransaction();
            transaction.begin();
            Object result = createQuery(sql).uniqueResult();
            transaction.commit();

            return (Long) result;
        } catch (Exception exception) {
            if (transaction != null) {
                transaction.rollback();
            }
            String log = this.mEntityClass.getSimpleName() + "Dao count(column) exception!\n";
            RdsDatabaseException databaseException = new RdsDatabaseException(log + exception.getMessage());
            throw databaseException;
        }
    }

    @Override
    public Long countUnique(String column) throws RdsDatabaseException {
        Transaction transaction = null;
        try {
            String sql = "SELECT COUNT(DISTINCT " + column + ") FROM " + this.mEntityClass.getSimpleName();

            transaction = super.currentSession().getTransaction();
            transaction.begin();
            Object result = createQuery(sql).uniqueResult();
            transaction.commit();

            return (Long) result;
        } catch (Exception exception) {
            if (transaction != null) {
                transaction.rollback();
            }
            String log = this.mEntityClass.getSimpleName() + "Dao countUnique(column) exception!\n";
            RdsDatabaseException databaseException = new RdsDatabaseException(log + exception.getMessage());
            throw databaseException;
        }
    }

    /* --------------< other end >--------------------------*/


    /* ---------------------- 公用辅助的方法 start -------------------- */
    private Query createQuery(String sql, SqlParam... params) {
        Query queryObject = super.currentSession().createQuery(sql);
        if (params != null) {
            for (int i = 0; i < params.length; i++) {
                queryObject.setParameter(params[i].paramName, params[i].paramValue);
            }
        }
        return queryObject;
    }

    private Criteria createCriteria(Criterion... criterions) {
        Criteria criteria = super.currentSession().createCriteria(mEntityClass);
        if (null != criterions) {
            for (Criterion c : criterions) {
                criteria.add(c);
            }
        }
        return criteria;
    }
    /* ---------------------- 公用辅助的方法 end -------------------- */
}
