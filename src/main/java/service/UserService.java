package service;

import dao.entity.User;
import dao.UserDaoImpl;

public class UserService extends BaseService {
    private UserDaoImpl userDao;

    public UserService() {
        userDao = (UserDaoImpl) getDaoBean(UserDaoImpl.class);
    }

    public Boolean has(Long id) {
        return userDao.has(id);
    }

    public void setName(Long id, String name) {
        User user = new User();
        user.setId(id);
        user.setName(name);
        userDao.update(user);
    }
}
