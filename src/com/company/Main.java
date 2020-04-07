package com.company;

import java.sql.*;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Connection conn = null;
        Scanner myScanner = new Scanner(System.in);
        try {
            String url = "jdbc:mysql://localhost:3306/notizbuch?user=root";
            conn = DriverManager.getConnection(url);
            System.out.println("got it!");
            String query = "";

//Select in my calendar
            System.out.println("my calendar");
            String input1 = "";
            String input2 = "";

            System.out.println("it'll print: date (start and end) of an event with a comment. You can choose a specific time frame" +
                    "\n enter a date ('yyyy-mm-dd') or time frame (1.input: > 'yyyy-mm-dd' 2.input: < 'yyyy-mm-dd')!");
            input1 = myScanner.nextLine();
//time frame:
            if (input1.startsWith(">")) {
                input1 = "WHERE datum " + input1;
                input2 = myScanner.nextLine();
                input2 = "AND datum " + input2;
                query = "SELECT TIMESTAMP(`datum`,`zeit`) AS Wann, " +
                        "DATE_ADD(TIMESTAMP(`datum`,`zeit`), INTERVAL `zeitspanne` HOUR_SECOND) AS bis, " +
                        "`event`, `kommentar` FROM `kalender`" + input1 + input2;
            }
//one specific date:
            else {
                input1 = "WHERE datum =" + input1;
                query = "SELECT TIMESTAMP(`datum`,`zeit`) AS Wann, " +
                        "DATE_ADD(TIMESTAMP(`datum`,`zeit`), INTERVAL `zeitspanne` HOUR_SECOND) AS bis, " +
                        "`event`, `kommentar` FROM `kalender`" + input1;
            }
//output:
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


//select in my journal:
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
//insert to my journal:
            System.out.println("insert to journal");
            System.out.println("please enter the title of your entry");
            String title = myScanner.nextLine();
            System.out.println("now enter your log text");
            String log = myScanner.nextLine();
            String command = "INSERT INTO `eintrag`(`titel`, `log`) VALUES ('"+title+"', '"+log+"')";
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
