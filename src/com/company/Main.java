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
            String query = "SELECT TIMESTAMP(`datum`,`zeit`) AS Wann, " +
                    "DATE_ADD(TIMESTAMP(`datum`,`zeit`), INTERVAL `zeitspanne` HOUR_SECOND) AS bis, " +
                    "`event`, `kommentar` FROM `kalender`";
            try {
                stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(query);
                while (rs.next()) {
                    //int entry = rs.getInt("id");
                    String date = rs.getTimestamp("Wann").toString();
                    //String time = rs.getTime("zeit").toString();
                    String till = rs.getTimestamp("bis").toString();
                    String event = rs.getString("event");
                    String comment = rs.getString("kommentar");
                    System.out.println(date + " bis " + till + " " + event + " " + comment);
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
