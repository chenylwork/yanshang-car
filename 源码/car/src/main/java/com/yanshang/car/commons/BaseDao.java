package com.yanshang.car.commons;

import java.sql.*;

/*
 * @ClassName BaseDao
 * @Description 作用描述
 * @Author 陈彦磊
 * @Date 2019/1/22- 10:31
 * @Version 1.0
 **/
public class BaseDao {

    public static boolean createDatabase(String name,String host,String username,String password) {
        Connection connection = getConn("mysql", host, username, password);
        Statement statement = null;
        Connection carConn = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            statement = connection.createStatement();
            statement.execute("CREATE DATABASE IF NOT EXISTS `"+name+"` DEFAULT CHARACTER SET utf8;");
            carConn = getConn(name, host, username, password);
            if(carConn != null) return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (statement != null) statement.close();
                if (connection != null) connection.close();
                if (carConn != null) carConn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
    public static Connection getConn(String database,String host,String username,String password) {
        String URL="jdbc:mysql://127.0.0.1:3306/"+database+"?useUnicode=true&characterEncoding=utf-8&useSSL=false&&serverTimezone=GMT%2B8";
        String USER= username;
        String PASSWORD=password;
        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
