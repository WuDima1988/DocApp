package com.wudima.docApp.settings;

import com.wudima.docApp.account.Account;

import java.io.File;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

//        createTableIfNotExist();

        return connection;
    }

    public void createTableIfNotExist(){
        String createTable = "CREATE TABLE IF NOT EXISTS "+Constant.TABLE_NAME
                + " (id INTEGER PRIMARY KEY AUTOINCREMENT, "
                +Constant.NAME+" TEXT, "
                +Constant.SURNAME+" TEXT, "
                +Constant.SEX+" TEXT, "
                +Constant.BIRTHDATE+" DATE, "
                +Constant.BIRTHPLACE+ " TEXT, "
                +Constant.DOCNUMBER+ " TEXT, "
                +Constant.DOCTYPE+ " TEXT, "
                +Constant.IDNumber+ " LONG, "
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
            String documentFirstPage,
            String documentSecondPage,
            String photo) {

        String insert = "INSERT INTO " +Constant.TABLE_NAME+"("
                +Constant.NAME+","
                +Constant.SURNAME+","
                +Constant.SEX+","
                +Constant.BIRTHDATE+","
                +Constant.BIRTHPLACE+","
                +Constant.DOCNUMBER+","
                +Constant.DOCTYPE+","
                +Constant.IDNumber+","
                +Constant.PHOTO+","
                +Constant.DocFirstPage+","
                +Constant.DocumentSecondPage
                +")"+"values(?,?,?,?,?,?,?,?,?,?,?)";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(insert);

            preparedStatement.setString(1,name);
            preparedStatement.setString(2,surname);
            preparedStatement.setString(3,sex);
            preparedStatement.setString(4,birthPlace);
            if(birthDate!=null) {
                preparedStatement.setString(5, birthDate.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
            }
            preparedStatement.setString(6,docNumber);
            preparedStatement.setString(7,docType);
            preparedStatement.setLong(8,idNumber);
            preparedStatement.setString(9,photo);
            preparedStatement.setString(10, documentFirstPage);
            preparedStatement.setString(11,documentSecondPage);


            preparedStatement.executeUpdate();


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    public void deleteAccount(int id) throws SQLException {

        String delete = "DELETE FROM " +Constant.TABLE_NAME+" WHERE id="+id;

        PreparedStatement statement = connection.prepareStatement(delete);

        int result = statement.executeUpdate();
        System.out.println("[DataBaseHandler] - [deleteAccount] :: end"+result);
        connection.close();
    }

    public ArrayList<Account> getAllAccounts(){

        String getAll = "SELECT * FROM " + Constant.TABLE_NAME;

        ArrayList<Account> allAccounts = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery(getAll);

            while (resultSet.next()) {

                Account account = new Account();

                account.setId(resultSet.getInt(1));
                System.out.println("Account Id: "+resultSet.getLong(1));
                account.setName(resultSet.getString(2));
                account.setSurname(resultSet.getString(3));
                account.setSex(resultSet.getString(4));
                account.setBirthPlace(resultSet.getString(5));

                String birthDateStr = resultSet.getString(6);
                if (birthDateStr != null && !birthDateStr.isBlank()) {
                    account.setBirthDate(LocalDate.parse(birthDateStr));
                } else {
                    account.setBirthDate(null); // або значення за замовчуванням
                }


                account.setDocNumber(resultSet.getString(7));
                account.setDocType(resultSet.getString(8));
                account.setIdNumber(resultSet.getLong(9));
                String photoPath = resultSet.getString(10);
                System.out.println("PhotoPath: "+photoPath);
                account.setPhoto(photoPath != null && !photoPath.isBlank() ? new File(photoPath) : null);

                String doc1Path = resultSet.getString(11);
                System.out.println("doc1Path: "+doc1Path);
                account.setDocumentFirstPage(doc1Path != null && !doc1Path.isBlank() ? new File(doc1Path) : null);

                String doc2Path = resultSet.getString(12);
                account.setDocumentSecondPage(doc2Path != null && !doc2Path.isBlank() ? new File(doc2Path) : null);




                allAccounts.add(account);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return allAccounts;
    }
}
