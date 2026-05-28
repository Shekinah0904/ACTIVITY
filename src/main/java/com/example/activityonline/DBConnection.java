package com.example.activityonline;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

    public static Connection connect() {
        try {
            return DriverManager.getConnection(
                    "jdbc:postgresql://aws-1-ap-southeast-2.pooler.supabase.com:5432/postgres",
                    "postgres.xaepyotjobtkdsdyhszn",
                    "Shekinah0904"
            );
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}