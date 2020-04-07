package com.company;

import java.sql.*;

public class Main {

    public static void main(String[] args) {
        Connection conn = null;
        try {
            String url = "jdbc:mysql://localhost:3306/notizbuch?user=root";
            conn = DriverManager.getConnection(url);
            System.out.println("got it!");
            Statement stmt = null;
            String query = "select * from kalender";
            try {
                stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(query);
                while (rs.next()) {
                    int entry = rs.getInt("id");
                    String date = rs.getDate("datum").toString();
                    String time = rs.getTime("zeit").toString();
                    String event = rs.getString("event");
                    String comment = rs.getString("kommentar");
                    System.out.println(entry + " " + date + " " + time + " " + event + " " + comment);
                }
            } catch (SQLException e) {
                throw new Error("Problem", e);
            } finally {
                if (stmt != null) {
                    stmt.close();
                }
            }
        } catch (SQLException e) {
            throw new Error("Problem", e);
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }

    }
}
