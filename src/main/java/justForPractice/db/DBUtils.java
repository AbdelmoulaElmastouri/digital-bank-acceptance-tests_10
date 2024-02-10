package justForPractice.db;

import java.sql.*;
import java.util.*;

public class DBUtils {

    private  static Connection connection = null;
    private static Statement statement = null;
    private static ResultSet resultSet = null;

// a methode that can dynamically send select statement
     public static void establishConnection() {

         String url = "jdbc:mysql://3.249.240.23:3306/abdelmou1327";
         String username = "abdelmou1327";
         String password = "asbbtmufagbuozby";

         try {
             Class.forName("com.mysql.cj.jdbc.Driver");
             connection = DriverManager.getConnection(url, username, password);

         } catch (ClassNotFoundException | SQLException e) {
             e.printStackTrace();
         }
     }

// create a methode that inserts and update intodb
// return the nums of rows updated or 0 when action not taken
     public static List<Map<String, Object>> runSQLSelectQuery(String sqlQuery) {

         List<Map<String, Object>> dbResultList = new ArrayList<>();
         try {
              statement = connection.createStatement();
              resultSet = statement.executeQuery(sqlQuery);

              // getMetaData method return info about your info.
             ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
             int columnCount = resultSetMetaData.getColumnCount();

              while (resultSet.next()) {
                  Map<String , Object> rowMap = new HashMap<>();

                  for (int col = 1; col <= columnCount; col++ ) {
                      rowMap.put(resultSetMetaData.getColumnName(col), resultSet.getObject(col));
                  }

                  dbResultList.add(rowMap);
              }

         } catch (SQLException throwables) {
             throwables.printStackTrace();
         }

         return dbResultList;
     }


//Update query
      public static int runSQLUpdateQuery(String sqlQuery) {
          int rowsAffected = 0;
          try {
              statement = connection.createStatement();
            rowsAffected = statement.executeUpdate(sqlQuery);
          } catch (SQLException throwables) {
              throwables.printStackTrace();
          }

          return rowsAffected;
      }

//close connection
      public  static void closeConnection() {
         try {
             if(resultSet != null) {
                resultSet.close();
             }
             if(statement != null) {
                 statement.close();
             }
             if(connection != null) {
                 connection.close();
             }
         } catch (SQLException throwables) {
             throwables.printStackTrace();
         }

      }


}
