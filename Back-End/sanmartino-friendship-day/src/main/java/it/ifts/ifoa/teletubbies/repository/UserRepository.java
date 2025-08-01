package it.ifts.ifoa.teletubbies.repository;

import java.sql.*;
import java.time.Instant;
import java.util.Optional;

import it.ifts.ifoa.teletubbies.config.ConnectionPool;
import it.ifts.ifoa.teletubbies.entity.User;
import it.ifts.ifoa.teletubbies.exception.InsertFailedException;
import it.ifts.ifoa.teletubbies.utils.JdbcUtils;

public class UserRepository {
    private final ConnectionPool pool;

    public UserRepository(ConnectionPool pool) {
        this.pool = pool;
    }


    public void saveUser(User user) {
        String sql = """
                INSERT INTO customers (email, name, surname,\
                 birthdate, gender, fiscalCode,\
                 phoneNumber, residencyCountry, residencyAddress,\
                 residencyZipCode, shipCountry, shipAddress,\
                 shipZipCode, privacy, rules, tokenId, residencyProvince, shipProvince) \
                VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)""";
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = pool.borrowConnection();
            statement = connection.prepareStatement(sql);

            statement.setString(1, user.getEmail());
            statement.setString(2, user.getName());
            statement.setString(3, user.getSurname());

            statement.setDate(4, Date.valueOf(user.getBirthDate()));
            statement.setString(5, user.getGender());
            statement.setString(6, user.getFiscalCode());

            statement.setString(7, user.getPhoneNumber());
            statement.setString(8, user.getResidencyCountry());
            statement.setString(9, user.getResidencyAddress());

            statement.setString(10, user.getResidencyZipCode());
            statement.setString(11, user.getShipCountry());
            statement.setString(12, user.getShipAddress());

            statement.setString(13, user.getShipZipCode());
            statement.setBoolean(14, user.getPrivacy());
            statement.setBoolean(15, user.getRules());
            statement.setString(16, user.getTokenId());
            statement.setString(17, user.getResidencyProvince());
            statement.setString(18, user.getShipProvince());

            if (statement.executeUpdate() < 1) {
                //should never happen, just to be sure
                throw new InsertFailedException("insert failed");
            }
        }
        catch (SQLException | InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            JdbcUtils.closeAndRelease(statement, connection, pool);
        }
    }


    //if user is italian, verify that fiscal code isn't already taken
    public Optional<Integer> idFromFiscalCode(String fiscalCode) {
        String sql = "SELECT id FROM customers WHERE fiscalCode = ?";

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        Optional<Integer> id = Optional.empty();

        try {
            connection = pool.borrowConnection();
            statement = connection.prepareStatement(sql);
            statement.setString(1, fiscalCode);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                id = Optional.of(resultSet.getInt("id"));
            }
        }
        catch (SQLException | InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            JdbcUtils.closeAndRelease(statement, resultSet, connection, pool);
        }
        return id;
    }

    public Optional<Integer> idFromEmail(String email) {
        String sql = "SELECT id FROM customers WHERE email = ?";

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        Optional<Integer> id = Optional.empty();

        try {
            connection = pool.borrowConnection();
            statement = connection.prepareStatement(sql);
            statement.setString(1, email);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                id = Optional.of(resultSet.getInt("id"));
            }
        }
        catch (SQLException | InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            JdbcUtils.closeAndRelease(statement, resultSet, connection, pool);
        }
        return id;
    }



    public Optional<String> tokenIdFromEmail(String email) {
        String sql = "SELECT tokenId FROM customers WHERE email = ?";

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        Optional<String> tokenId = Optional.empty();

        try {
            connection = pool.borrowConnection();
            statement = connection.prepareStatement(sql);
            statement.setString(1, email);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                tokenId = Optional.of(resultSet.getString("tokenId"));
            }
        }
        catch (SQLException | InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            JdbcUtils.closeAndRelease(statement, resultSet, connection, pool);
        }
        return tokenId;
    }


    public String doubleOptIn(String tokenId) {
        String insertSql = "UPDATE customers SET confirmedDate = ? WHERE tokenId = ? AND confirmedDate IS NULL";
        String selectQuery = "SELECT email FROM customers WHERE tokenId = ?";

        Connection connection = null;
        PreparedStatement insertStm = null;
        PreparedStatement queryStm = null;

        try {
            connection = pool.borrowConnection();
            insertStm = connection.prepareStatement(insertSql);

            insertStm.setTimestamp(1, Timestamp.from(Instant.now()));
            insertStm.setString(2, tokenId);
            System.out.println(insertStm.executeUpdate());

            queryStm = connection.prepareStatement(selectQuery);
            queryStm.setString(1, tokenId);

            ResultSet rs = queryStm.executeQuery();
            if (rs.next()){
                return rs.getString("email");
            }

        }
        catch (SQLException | InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            JdbcUtils.closeAndRelease(insertStm, connection, pool);
        }

        return "";
    }


    public boolean isEmailConfirmed(String email) {
        String sql = "SELECT 1 FROM customers WHERE email = ? AND confirmedDate IS NOT NULL";

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        boolean retvalue = false;
        try {
            connection = pool.borrowConnection();
            statement = connection.prepareStatement(sql);

            statement.setString(1, email);

            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                retvalue = true;
            }
        }
        catch (SQLException | InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            JdbcUtils.closeAndRelease(statement, resultSet, connection, pool);
        }
        return retvalue;
    }

    public String getResidencyCountryFromId(int id){

        String sql = "SELECT residencyCountry FROM customers WHERE id = ?";

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
       String retvalue = null;
        try {
            connection = pool.borrowConnection();
            statement = connection.prepareStatement(sql);

            statement.setInt(1, id);

            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                retvalue = resultSet.getString("residencyCountry");
            }
        }
        catch (SQLException | InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            JdbcUtils.closeAndRelease(statement, resultSet, connection, pool);
        }
        return retvalue;
    }

    public boolean isConfirmationTop499(String tokenId) {
        String sql = "SELECT COUNT(*) FROM (" + "SELECT tokenId FROM customers WHERE confirmedDate IS NOT NULL ORDER BY confirmedDate ASC LIMIT 499) AS s " + "WHERE tokenId = ?";

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        boolean retvalue = false;
        try {
            connection = pool.borrowConnection();
            statement = connection.prepareStatement(sql);

            statement.setString(1, tokenId);

             resultSet = statement.executeQuery();

            if (resultSet.next()) {
                retvalue = resultSet.getInt(1) > 0;
            }
        }
        catch (SQLException | InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            JdbcUtils.closeAndRelease(statement, resultSet, connection, pool);
        }
        return retvalue;
    }
}




