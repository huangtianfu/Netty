package dao;

import dao.base.GenericDao;
import dao.base.RdsDatabaseException;
import dao.entity.User;

public interface UserDao extends GenericDao<User, Long> {
    Boolean has(Long id) throws RdsDatabaseException;
}
