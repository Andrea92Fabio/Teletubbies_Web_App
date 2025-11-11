package it.ifts.ifoa.teletubbies.utils;

import it.ifts.ifoa.teletubbies.config.ConnectionPool;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JdbcUtils {
    public static void closeAndRelease(Statement statement, Connection con, ConnectionPool pool){
        if (statement != null){
            try {
                statement.close();
            }
            catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
        if (con != null) {
            pool.releaseConnection(con);
        }
    }


    public static void closeAndRelease(Statement statement, ResultSet resultSet, Connection con, ConnectionPool pool){
        if (resultSet != null){
            try {
                resultSet.close();
            }
            catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
        closeAndRelease(statement, con, pool);
    }
}
