package com.nkpdqz.utils;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class ExportFromSql implements ExportFrom {

    private String url;
    private String username;
    private String password;

    public ExportFromSql() {
        Properties properties = new Properties();
        try {
            properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("database_excel.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        url = properties.getProperty("url");
        username = properties.getProperty("username");
        password = properties.getProperty("password");
    }

    //返回一个ResultSet供导出使用
    public ResultSet exportFrom(int start) throws SQLException {
        String SQL = "SELECT * FROM eztable WHERE id>= "+start+" ORDER BY id ASC LIMIT "+start+",500000";
        Connection con = getCon();
        PreparedStatement statement = con.prepareStatement(SQL);
        return statement.executeQuery();
    }

    //返回一个List供导出使用
    public List<String[]> exportFrom(int start, int num) {
        String SQL = "SELECT * FROM eztable WHERE id>= "+start+" ORDER BY id ASC LIMIT "+start+","+num;
        Connection con = getCon();
        PreparedStatement statement;
        List<String[]> dataList = null;
        try {
            statement = con.prepareStatement(SQL);
            ResultSet resultSet = statement.executeQuery();
            dataList = new ArrayList<String[]>();
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();
            String[] label = new String[columnCount];
            for (int i = 0; i < columnCount; i++) {
                String columnLabel = metaData.getColumnLabel(i + 1);
                label[i] = columnLabel;
            }
            dataList.add(label);
            while (resultSet.next()){
                String[] line = new String[columnCount];
                for (int i = 0; i < columnCount; i++) {
                    String string = resultSet.getString(i + 1);
                    line[i] = string;
                }
                dataList.add(line);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return dataList;
    }

    public Connection getCon(){
        Connection connection = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(url,username,password);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

}
