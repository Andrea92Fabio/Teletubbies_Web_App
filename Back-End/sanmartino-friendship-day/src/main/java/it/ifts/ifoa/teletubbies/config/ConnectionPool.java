package it.ifts.ifoa.teletubbies.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.Queue;

public class ConnectionPool {

    private static final int SIZE = 5;
    private final Queue<Connection> pool;
    private final String jdbcUrl; // Campo per memorizzare l'URL JDBC

    private ConnectionPool() {
        pool = new LinkedList<>();

        String dbHost = System.getenv("DB_HOST");
        String dbName = System.getenv("DB_NAME");
        String dbUser = System.getenv("DB_USER");
        String dbPass = System.getenv("DB_PASSWORD");

        // Initialize jdbcUrl here
        this.jdbcUrl = String.format("jdbc:mariadb://%s:3306/%s?user=%s&password=%s", dbHost, dbName, dbUser, dbPass);


        for(int i = 0; i < SIZE; i++){
            try {
                Connection con = DriverManager.getConnection(this.jdbcUrl);
                pool.offer(con);
            }
            catch (SQLException e) {
                throw new RuntimeException("Failed to create DB connection", e);
            }
        }
    }

    public void close() {
        for (Connection con : pool){
            try {
                con.close();
            }
            catch (SQLException ignored) {
            }
        }
        pool.clear();
    }

    // Inner static helper class - thread-safe lazy init
    private static class Holder {
        private static final ConnectionPool INSTANCE = new ConnectionPool();
    }

    public static ConnectionPool getInstance() {
        return Holder.INSTANCE;
    }

    // Pool management methods, synchronized for thread safety
    public synchronized Connection borrowConnection() throws InterruptedException, SQLException {
        while (pool.isEmpty()){
            wait();
        }
        Connection con = pool.poll();

        if (con == null || con.isClosed()){
            con = DriverManager.getConnection(this.jdbcUrl);
        }
        return con;
    }

    public synchronized void releaseConnection(Connection con) {
        if (con != null){
            pool.offer(con);
            notifyAll();
        }
    }
}