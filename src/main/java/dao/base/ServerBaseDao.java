package dao.base;

import org.hibernate.SessionFactory;

import javax.annotation.Resource;
import java.io.Serializable;

public class ServerBaseDao<T, PK extends Serializable> extends GenericDaoImpl<T, PK> {
    public ServerBaseDao() {
        super();
    }

    @Resource(name = "serverSessionFactory")
    public void setServerSessionFactory(SessionFactory sessionFactory) {
        super.setSessionFactory(sessionFactory);
    }
}
