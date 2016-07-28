package database.rds.server.dao;

import database.rds.base.GenericDao;
import database.rds.base.RdsDatabaseException;
import database.rds.server.entity.User;

public interface UserDao extends GenericDao<User, Long> {
    Boolean has(Long id) throws RdsDatabaseException;
}
