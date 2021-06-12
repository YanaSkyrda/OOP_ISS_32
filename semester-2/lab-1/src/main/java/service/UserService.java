package service;

import dao.FactoryDao;
import dao.UserDao;
import model.user.User;

public class UserService {

    private FactoryDao factoryDao = FactoryDao.getInstance();

    public User loginUser(String name, String password) throws Exception {
        User resultOfUser;
        try (UserDao userDao = factoryDao.createUserDao()) {
            resultOfUser = userDao.findByUserName(name)
                    .orElseThrow(() -> new RuntimeException("Empty user"));
        }
        if (resultOfUser.getPassword().equals(password))
            return resultOfUser;
        throw new IllegalArgumentException("Different password");
    }

    public void setFactoryDao(FactoryDao factoryDao) {
        this.factoryDao = factoryDao;
    }
}
