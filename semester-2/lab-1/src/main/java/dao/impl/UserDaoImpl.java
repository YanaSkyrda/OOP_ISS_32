package dao.impl;

import dao.UserDao;
import dao.mapper.UserMapper;
import model.user.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class UserDaoImpl implements UserDao {
    private final Logger logger = LogManager.getLogger(UserDaoImpl.class);
    private final Connection connection;
    private final ResourceBundle resourceBundle = ResourceBundle.getBundle("sql");
    private final UserMapper userMapper = new UserMapper();

    public UserDaoImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Optional<User> findByEmail(String email) {

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(resourceBundle.getString("user.findByEmail"));
            preparedStatement.setString(1, email);
            ResultSet set = preparedStatement.executeQuery();
            if (set.next())
                return Optional.of(userMapper.toUserFromResultSet(set));

        } catch (SQLException ex) {
            logger.warn("Could not find user with email address {}: {}", email, ex.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public Optional<User> create(User entity) {

        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement(resourceBundle.getString("user.create"),
                            Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, entity.getEmail());
            preparedStatement.setString(2, entity.getName());
            preparedStatement.setString(3, entity.getPassword());
            preparedStatement.setString(4, "ROLE_USER");
            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0) {
                System.out.println("Error User can`t be created");
            } else {
                System.out.println("User successfully created");
                try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        entity.setId(generatedKeys.getLong(1));
                    } else {
                        System.out.println("No id obtained. Cannot create");
                    }
                }
            }
        } catch (SQLException ex) {
            logger.warn("User already exist : {}", ex.getMessage());
        }

        return Optional.of(entity);
    }

    @Override
    public User findById(long id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<User> findAll() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Optional<User> update(User entity) {
        throw new UnsupportedOperationException();
    }


    @Override
    public void close() throws Exception {
        connection.close();

    }
}
