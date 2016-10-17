package dao;

import dao.base.GenericDao;
import dao.entity.User;

public interface UserDao extends GenericDao<User, Long> {
    Boolean has(Long id);
}
