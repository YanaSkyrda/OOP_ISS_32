package dao.impl;

import dao.FactoryDao;
import dao.FlightDao;
import dao.TicketDao;
import dao.UserDao;

import java.sql.Connection;

public class FactoryDaoImpl extends FactoryDao {

    private final ConnectionPool connectionPool = new ConnectionPool();

    private Connection getConnection() {
        return connectionPool.getConnection();
    }

    @Override
    public UserDao createUserDao() {
        return new UserDaoImpl(getConnection());
    }

    @Override
    public TicketDao createTicketDao() {
        return new TicketDaoImpl(getConnection());
    }

    @Override
    public FlightDao createFlightDao() {
        return new FlightDaoImpl(getConnection());
    }
}
