package service;

import entity.User;
import dao.UserDaoImpl;

public interface UserService {
    Boolean has(Long id);
}
