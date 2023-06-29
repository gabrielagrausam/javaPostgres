package com.crud;

import java.sql.*;

public class insertBeauty {
    private static final String INSERT_BEAUTY_SQL = "INSERT INTO beauty" +
            "  (name, description, uses) VALUES " +
            " (?, ?, ?);";
    private static final String jdbcUrl = "jdbc:postgresql://localhost:5432/jdbc_connection_project";
    private static final String username = "postgres";
    private static final String password = "12345";

    public static void main(String[] args) {

        try (Connection conn = DriverManager.getConnection(jdbcUrl, username, password);) {
            conn.setAutoCommit(false);

            try (PreparedStatement insertOp = conn.prepareStatement(INSERT_BEAUTY_SQL);) {
                insertOp.setString(1, "balsam do ciała");
                insertOp.setString(2, "balsam");
                insertOp.setString(3, "8 użyć");
                insertOp.executeUpdate();
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
