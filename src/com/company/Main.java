package com.company;

import java.sql.*;

public class Main {

    public static void main(String[] args) {
        Connection conn = null;
        try {
            String url = "jdbc:mysql://localhost:3306/notizbuch?user=root";
            conn = DriverManager.getConnection(url);
            System.out.println("got it!");
            System.out.println("my calendar");
            String query = "SELECT TIMESTAMP(`datum`,`zeit`) AS Wann, " +
                    "DATE_ADD(TIMESTAMP(`datum`,`zeit`), INTERVAL `zeitspanne` HOUR_SECOND) AS bis, " +
                    "`event`, `kommentar` FROM `kalender`";
            try (Statement stmt = conn.createStatement()) {
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
            }

            System.out.println("my journals");
            query = "SELECT * FROM `eintrag`";
            try (Statement stmt = conn.createStatement()) {
                ResultSet rs = stmt.executeQuery(query);
                while (rs.next()) {
                    String date = rs.getDate("datum").toString();
                    String title = rs.getString("titel");
                    String log = rs.getString("log");
                    System.out.println(date + " " + title + "\n\t" + log);
                }
            } catch (SQLException e) {
                throw new Error("Problem", e);
            }

            System.out.println("insert to journal");
            String command = "INSERT INTO `eintrag`(`titel`, `log`) VALUES ('Dienstag Nachmittag', 'Löwenzahnhonig ist fertig " +
                    "(wahrscheinlich misslungen), eir haben Mittag gegessen (Reste von gestern Abend), Gemüsekiste ist " +
                    "gekommen! Ich freue mich über Rhabarber, code nun weiter und komme gut voran.')";
            try (Statement stmt = conn.createStatement()) {
                stmt.executeUpdate(command);
            } catch (SQLException e) {
                throw new Error("Problem", e);
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
