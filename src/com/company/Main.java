package com.company;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {

    public static void main(String[] args) {
        Connection conn = null;
        try{
            String url = "jdbc:mysql://localhost:3306/notizbuch?user=root";
            conn = DriverManager.getConnection(url);
            System.out.println("got it!");
        } catch (SQLException e){
            throw new Error ("Problem", e);
        } finally {
            try {
                if(conn != null){
                    conn.close();
                }
            }catch (SQLException e){
                System.out.println(e.getMessage());
            }
        }

    }
}
