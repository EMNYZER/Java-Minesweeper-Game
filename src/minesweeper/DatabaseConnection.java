package minesweeper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    public static Connection getConnection() {
        Connection conn = null;

        try {
            String url = "jdbc:mysql://localhost:3306/minesweeper";
            String username = "root";
            String password = "";

            conn = DriverManager.getConnection(url, username, password);
            System.out.println("Koneksi ke database berhasil.");
        } catch (SQLException e) {
            System.out.println("Koneksi ke database gagal: " + e.getMessage());
        }

        return conn;
    }

    public static void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
                System.out.println("Koneksi ke database ditutup.");
            } catch (SQLException e) {
                System.out.println("Tidak dapat menutup koneksi: " + e.getMessage());
            }
        }
    }
}