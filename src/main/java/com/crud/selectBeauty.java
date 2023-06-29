package com.crud;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class selectBeauty {
    private static final String QUERY = "select * from beauty where id = ?";
    private static final String jdbcUrl = "jdbc:postgresql://localhost:5432/jdbc_connection_project";
    private static final String username = "postgres";
    private static final String password = "12345";

    public static void main(String[] args) {

        try (Connection conn = DriverManager.getConnection(jdbcUrl, username, password);) {
            conn.setAutoCommit(false);

            try (PreparedStatement selectOp = conn.prepareStatement(QUERY);) {
                selectOp.setInt(1, 7);
                System.out.println(selectOp);
                ResultSet rs = selectOp.executeQuery();
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String name = rs.getString("name");
                    String description = rs.getString("description");
                    String uses = rs.getString("uses");
                    System.out.println(id + "," + name + "," + description + "," + uses);
                }
                conn.commit();
                System.out.println("Transakcja zrealizowana");
            } catch (SQLException e) {
                printSQLException(e);
                if (conn != null) {
                    try {
                        System.out.println("Następuje rollback");
                        conn.rollback();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
    }

    public static void printSQLException(SQLException ex) {
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
