package dao;

import dao.impl.FactoryDaoImpl;

public abstract class FactoryDao {
    private static FactoryDao daoFactory;

    public abstract UserDao createUserDao();

    public abstract TicketDao createTicketDao();

    public abstract FlightDao createFlightDao();

    public static FactoryDao getInstance() {
        if (daoFactory == null) {
            synchronized (FactoryDao.class) {
                if (daoFactory == null) {
                    daoFactory = new FactoryDaoImpl();
                }
            }
        }
        return daoFactory;
    }

}
