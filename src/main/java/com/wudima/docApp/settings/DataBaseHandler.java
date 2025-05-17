package com.wudima.docApp.settings;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DataBaseHandler {


    private String dataPath = System.getProperty("user.home") + File.separator + "Documents"+ File.separator + "data"+ File.separator+"mydb.db";

    private File base = new File(dataPath);

    private Connection connection;


    public Connection getConnection() throws SQLException {

        if(!base.exists()){
            base.getParentFile().mkdirs();
        }

        String url = "jdbc:sqlite:" + dataPath;

        connection = DriverManager.getConnection(url);

        return connection;
    }

    public void createTableIfNotExist(){
        String createTable = "CREATE TABLE IF NOT EXISTS "+Constant.TABLE_NAME
                + " (id INTEGER PRIMARY KEY, "+Constant.NAME+" TEXT, "
                +Constant.SURNAME+" INTEGER, "
                +Constant.SEX+" TEXT, "
                +Constant.BIRTHDATE+" DATE, "
                +Constant.SEX+" TEXT, "
                +Constant.BIRTHPLACE+ " TEXT, "
                +Constant.DOCNUMBER+ " INTEGER, "
                +Constant.DOCTYPE+ " TEXT, "
                +Constant.IDNumber+ " INTEGER, "
                +Constant.PHOTO+ " TEXT, "
                +Constant.DocFirstPage+" TEXT, "
                +Constant.DocumentSecondPage+" TEXT "
                +")";


        try {
            PreparedStatement preparedStatement = connection.prepareStatement(createTable);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void addClient(String name, int age) throws SQLException {

        String insert = "INSERT INTO " +Constant.TABLE_NAME+"("+Constant.NAME+" TEXT, "
                +Constant.SURNAME+" INTEGER, "
                +Constant.SEX
                +Constant.BIRTHDATE
                +Constant.SEX
                +Constant.BIRTHPLACE
                +Constant.DOCNUMBER
                +Constant.DOCTYPE
                +Constant.IDNumber
                +Constant.PHOTO
                +Constant.DocFirstPage
                +Constant.DocumentSecondPage
                +")"+"values(?,?,?,?,?,?,?,?,?,?,?)";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(insert);

            preparedStatement.setString(1,name);
            preparedStatement.setInt(2,age);

            preparedStatement.executeUpdate();


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }
}
