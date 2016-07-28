package database.rds.server.impl;

import database.rds.base.RdsDatabaseException;
import database.rds.base.SqlParam;
import database.rds.server.dao.ServerBaseDao;
import database.rds.server.dao.UserDao;
import database.rds.server.entity.User;
import org.springframework.stereotype.Repository;

@Repository("UserDaoImpl")
public class UserDaoImpl extends ServerBaseDao<User, Long> implements UserDao {
    @Override
    public Boolean has(Long id) throws RdsDatabaseException {
        String sql = "select id from User where id=:id";
        Long userId = (Long) findUnique(sql, new SqlParam("id", id));
        return (userId != null);
    }
}
