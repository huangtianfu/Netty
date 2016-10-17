package dao;

import dao.base.ServerBaseDao;
import dao.base.SqlParam;
import dao.entity.User;
import org.springframework.stereotype.Repository;

@Repository("UserDaoImpl")
public class UserDaoImpl extends ServerBaseDao<User, Long> implements UserDao {
    @Override
    public Boolean has(Long id) {
        String sql = "select id from User where id=:id";
        Long userId = (Long) findUnique(sql, new SqlParam("id", id));
        return (userId != null);
    }
}
