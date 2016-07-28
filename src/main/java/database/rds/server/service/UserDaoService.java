package database.rds.server.service;

import database.rds.base.DaoBaseService;
import database.rds.base.RdsDatabaseException;
import database.rds.server.entity.User;
import database.rds.server.impl.UserDaoImpl;

public class UserDaoService extends DaoBaseService {
    private static UserDaoImpl userDao;

    public UserDaoService() {
        userDao = (UserDaoImpl) getDaoBean(UserDaoImpl.class);
    }

    public Boolean has(Long id) throws RdsDatabaseException {
        return userDao.has(id);
    }

    public void setName(Long id, String name) throws RdsDatabaseException {
        User user = new User();
        user.setId(id);
        user.setName(name);
        userDao.update(user);
    }
}
