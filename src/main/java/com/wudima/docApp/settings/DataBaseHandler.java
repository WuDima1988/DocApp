package com.wudima.docApp.settings;

import com.wudima.docApp.account.Account;

import java.io.File;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

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
        createTableIfNotExist();

        return connection;
    }

    public void createTableIfNotExist(){
        String createTable = "CREATE TABLE IF NOT EXISTS "+Constant.TABLE_NAME
                + " (id LONG PRIMARY KEY, "
                +Constant.NAME+" TEXT, "
                +Constant.SURNAME+" INTEGER, "
                +Constant.SEX+" TEXT, "
                +Constant.BIRTHDATE+" DATE, "
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

    public void addClient(
            String name,
            String surname,
            String birthPlace,
            String sex,
            String docNumber,
            Long idNumber,
            String docType,
            LocalDate birthDate,
            File documentFirstPage,
            File documentSecondPage,
            File photo) {

        String insert = "INSERT INTO " +Constant.TABLE_NAME+"("
                +Constant.NAME+" TEXT, "
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
            preparedStatement.setString(2,surname);
            preparedStatement.setString(3,birthPlace);
            preparedStatement.setString(4,sex);
            preparedStatement.setString(5,birthDate.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
            preparedStatement.setLong(6,idNumber);
            preparedStatement.setString(7,docType);
            preparedStatement.setString(8,docNumber);
            preparedStatement.setString(9,documentFirstPage.toString());
            preparedStatement.setString(10,documentSecondPage.toString());
            preparedStatement.setString(11,photo.toString());

            preparedStatement.executeUpdate();


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    public ArrayList<Account> getAllAccounts(){

        String getAll = "SELECT * FROM " + Constant.TABLE_NAME;

        ArrayList<Account> allAccounts = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery(getAll);

            while (resultSet.next()) {

                Account account = new Account();

                account.setId(resultSet.getLong(1));
                account.setName(resultSet.getString(2));
                account.setSurname(resultSet.getString(3));
                account.setSex(resultSet.getString(4));
                account.setBirthDate(LocalDate.parse(resultSet.getString(5)));
                account.setIdNumber(resultSet.getLong(6));
                account.setDocType(resultSet.getString(7));
                account.setDocNumber(resultSet.getString(8));
                account.setDocumentFirstPage(new File(resultSet.getString(9)));
                account.setDocumentSecondPage(new File(resultSet.getString(10)));
                account.setPhoto(new File(resultSet.getString(11)));


                allAccounts.add(account);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return allAccounts;
    }
}
