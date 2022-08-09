package com.zhouhao.utils;

import javax.sound.midi.Soundbank;
import java.sql.*;

public class JDBCUtil {
    private static String url = "jdbc:mysql://localhost:3306/dormitory?serverTimezone=GMT%2B8";
    private static String user = "root";
    private static String pwd = "123456";

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection(){
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url, user, pwd);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public static void release(Connection connection, Statement statement, ResultSet resultSet){
        if(connection != null){
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if(statement != null){
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if(resultSet != null){
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        Connection connection = JDBCUtil.getConnection();
        try {
            PreparedStatement prepareStatement = connection.prepareStatement("select * from student");
            ResultSet resultSet = prepareStatement.executeQuery();
            resultSet.next();
            System.out.println(resultSet.getString(3));
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
