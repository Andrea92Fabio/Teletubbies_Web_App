package it.ifts.ifoa.teletubbies.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.Queue;

public class ConnectionPool {

    private static final int SIZE = 5;
    private final Queue<Connection> pool;

    private ConnectionPool() {
        pool = new LinkedList<>();

        for(int i = 0; i < SIZE; i++){
            try {
                 Connection con = DriverManager.getConnection("jdbc:mariadb://localhost:3306/san_martino_friendship_day?user=root");
                 pool.offer(con);
            }
            catch (SQLException e) {
                throw new RuntimeException(e);
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
             con = DriverManager.getConnection("jdbc:mariadb://localhost:3306/san_martino_friendship_day?user=root");
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
