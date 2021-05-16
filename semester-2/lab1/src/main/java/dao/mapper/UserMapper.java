package dao.mapper;

import model.user.User;
import model.user.UserBuilder;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper {

    public User toUserFromResultSet(ResultSet set) throws SQLException {
        return new UserBuilder()
                .setId(set.getLong("id"))
                .setName(set.getString("name"))
                .setEmail(set.getString("email"))
                .setPassword(set.getString("password"))
                .setRole(set.getString("role")).build();
    }

}
