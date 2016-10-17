package service;

import dao.base.RdsDatabaseException;
import dao.entity.User;
import dao.UserDaoImpl;

public class UserService extends BaseService {
    private UserDaoImpl userDao;

    public UserService() {
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
