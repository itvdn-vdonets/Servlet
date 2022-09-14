package dao;

import Utils.Util;
import entities.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    //    public int registerUser(User user) throws ClassNotFoundException {
//        String INSERT_USERS_SQL = "INSERT INTO users" +
//                "  (id, first_name, last_name, email, age) VALUES " +
//                " (?, ?, ?, ?, ?);";
//
//        int result = 0;
//
//        Class.forName("org.postgresql.Driver");
//
//        try (Connection connection = DriverManager
//                .getConnection("jdbc:postgresql://34.141.197.7:5432/postgres", "postgres", "postgres");
////        try (Connection connection = Util.getConnection())
//
//             // Step 2:Create a statement using connection object
//             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USERS_SQL)) {
//            preparedStatement.setInt(1, user.getId());
//            preparedStatement.setString(2, user.getFirstName());
//            preparedStatement.setString(3, user.getLastName());
//            preparedStatement.setString(4, user.getEmail());
//            preparedStatement.setInt(5, user.getAge());
//
//            System.out.println(preparedStatement);
//            // Step 3: Execute the query or update query
//            result = preparedStatement.executeUpdate();
//
//        } catch (SQLException e) {
//            // process sql exception
//            printSQLException(e);
//        }
//        return result;
//    }
    public static int registerUser(User user, Connection conn) throws SQLException {
        String INSERT_USERS_SQL = "INSERT INTO users" +
                "  (id, first_name, last_name, email, age) VALUES " +
                " (?, ?, ?, ?, ?);";

        int result = 0;

        PreparedStatement stm = conn.prepareStatement(INSERT_USERS_SQL);
        int i =1;
        stm.setInt(i++, user.getId());
        stm.setString(i++, user.getFirstName());
        stm.setString(i++, user.getLastName());
        stm.setString(i++, user.getEmail());
        stm.setInt(i++, user.getAge());

        System.out.println(stm);

        result = stm.executeUpdate();

        return result;
    }

    public static List<User> getUserList(Connection conn) throws SQLException {
        String sqlUsersList = "SELECT * FROM users";

        PreparedStatement stm = conn.prepareStatement(sqlUsersList);

        ResultSet rs = stm.executeQuery();

        List<User> list = new ArrayList<>();

        while (rs.next()) {
            User user = new User();
            user.setId(rs.getInt("id"));
            user.setFirstName(rs.getString("first_name"));
            user.setLastName(rs.getString("last_name"));
            user.setEmail(rs.getString("email"));
            user.setAge(rs.getInt("age"));
            list.add(user);
        }
        return list;

    }

    private void printSQLException(SQLException ex) {
        for (Throwable e : ex) {
            if (e instanceof SQLException) {
                e.printStackTrace(System.err);
                System.err.println("SQLState: " + ((SQLException) e).getSQLState());
                System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
                System.err.println("Message: " + e.getMessage());
                Throwable t = ex.getCause();
                while (t != null) {
                    System.out.println("Cause: " + t);
                    t = t.getCause();
                }
            }
        }
    }
}