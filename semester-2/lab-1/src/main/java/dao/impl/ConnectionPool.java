package dao.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.CopyOnWriteArrayList;

public class ConnectionPool {
    private final Logger logger = LogManager.getLogger(ConnectionPool.class);
    private final List<Connection> availableConnections = new CopyOnWriteArrayList<>();
    private final ResourceBundle bundle = ResourceBundle.getBundle("sql");

    private final int MAX_SIZE = 15;

    public ConnectionPool() {
        for (int count = 0; count < MAX_SIZE; count++) {
            availableConnections.add(this.createConnection());
        }
    }

    private Connection createConnection() {
        Connection connection = null;
        try {
            Class.forName(bundle.getString("database.driver"));
            connection = DriverManager.getConnection(bundle.getString("database.url"),
                    bundle.getString("database.user"), bundle.getString("database.pass"));
        } catch (SQLException | ClassNotFoundException e) {
            logger.info("Connection can`t be created");
        }
        Connection finalConnection = connection;
        return (Connection) Proxy.newProxyInstance(Connection.class.getClassLoader(), new Class[]{Connection.class},
                ((proxy, method, args) -> {
                    if (method.getName().equals("close")) {
                        if (finalConnection.isClosed()) {
                            logger.warn("Connection is already closed");
                        } else {
                            availableConnections.add((Connection) proxy);
                        }
                        return null;
                    } else {
                        return method.invoke(finalConnection, args);
                    }
                }));
    }

    public Connection getConnection() {
        if (availableConnections.size() == 0) {
            logger.info("All connections are used");
            return null;
        } else {
            return availableConnections.remove(availableConnections.size() - 1);
        }

    }

    public int getFreeConnectionCount() {
        return availableConnections.size();
    }
}
