package justForPractice.db;

import java.sql.*;

public class PracticeSQL {
    public static void main(String[] args) {

        String url = "jdbc:mysql://3.249.240.23:3306/abdelmou1327";
        String username = "abdelmou1327";
        String password = "asbbtmufagbuozby";

        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            // Register the MySQL driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish connection
            connection = DriverManager.getConnection(url, username, password);
            statement = connection.createStatement();

            // Execute select Query
            resultSet = statement.executeQuery("SELECT * FROM users");

            // Print column headers
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();
            for (int i = 1; i <= columnCount; i++) {
                System.out.print(metaData.getColumnName(i) + " | ");
            }
            System.out.println();

            // Print data
            while (resultSet.next()) {
                for (int i = 1; i <= columnCount; i++) {
                    System.out.print(resultSet.getString(i) + " | ");
                }
                System.out.println();
            }

            // Update user_profile table
            int rowsAffected = statement.executeUpdate("UPDATE user_profile SET address = '284 Atlantic Ave' WHERE email_address = 'ebelee@gmail.com'");
            System.out.println("Rows affected: " + rowsAffected);

            // Print updated data
            resultSet = statement.executeQuery("SELECT * FROM user_profile");
            while (resultSet.next()) {
                System.out.println(resultSet.getInt("address") + " | " + resultSet.getString("email_address"));
            }

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }
}
