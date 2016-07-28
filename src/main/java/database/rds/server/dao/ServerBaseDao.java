package database.rds.server.dao;

import database.rds.base.GenericDaoHibernateImpl;
import org.hibernate.SessionFactory;

import javax.annotation.Resource;
import java.io.Serializable;

public class ServerBaseDao<T, PK extends Serializable> extends GenericDaoHibernateImpl<T, PK> {
    public ServerBaseDao() {
        super();
    }

    @Resource(name = "serverSessionFactory")
    public void setServerSessionFactory(SessionFactory sessionFactory) {
        super.setSessionFactory(sessionFactory);
    }
}
