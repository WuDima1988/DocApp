package com.wudima.docApp.controllers;


import com.wudima.docApp.DocApplication;
import com.wudima.docApp.account.Account;
import com.wudima.docApp.settings.DataBaseHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.ResourceBundle;

public class RegistrationController implements Initializable {


    @FXML
    private Label resultLabel;

    @FXML
    private TextField nameField;

    @FXML
    private TextField surnameField;

    @FXML
    private TextField birthPlaceField;

    @FXML
    private ChoiceBox<String> sexField;

    @FXML
    private TextField docNumberField;

    @FXML
    private TextField idField;

    @FXML
    private TextField docTypeField;

    @FXML
    private TextField fileNamePhoto;

    @FXML
    private TextField fileNameFirstPage;

    @FXML
    private TextField fileNameSecondPage;

    @FXML
    private DatePicker datePicker;

    @FXML
    private ImageView mainLogo;


    FileChooser fileChooser = new FileChooser();

    String [] sexVariations = {"MALE","FEMALE"};

    public Parent root;
    public Stage stage;
    public Scene scene;
    public File fileFirstPage;
    public File filePhoto;
    public File fileSecondPage;
    public Image logoImg = new Image(new FileInputStream(DocApplication.settings.getMainLogo()));

    public RegistrationController() throws FileNotFoundException {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        mainLogo.setImage(logoImg);
        sexField.getItems().addAll(sexVariations);
    }

    public void fileChoosePhoto() {

        System.out.println("[RegistrationController] - [fileChoosePhoto] : start");

        filePhoto = fileChooser.showOpenDialog(new Stage());
        fileNamePhoto.setText(filePhoto.getName());

        System.out.println("[RegistrationController] - [fileChoosePhoto] : end");

    }

    public void fileChooseFirstPage() {

        System.out.println("[RegistrationController] - [fileChooseFirstPage] : start");

        fileFirstPage = fileChooser.showOpenDialog(new Stage());
        fileNameFirstPage.setText(fileFirstPage.getName());

        System.out.println("[RegistrationController] - [fileChooseFirstPage] : end");

    }

    public void fileChooseSecondPage() {

        System.out.println("[RegistrationController] - [fileChooseSecondPage] : start");

        fileSecondPage = fileChooser.showOpenDialog(new Stage());
        fileNameSecondPage.setText(fileSecondPage.getName());

        System.out.println("[RegistrationController] - [fileChooseSecondPage] : end");

    }

    public void save(ActionEvent event) throws IOException, InterruptedException {



        String name = Optional.of(nameField.getText()).orElseGet(()->"");
        String surname = Optional.of(surnameField.getText()).orElseGet(()->"");
        String sex="";
        if(sexField.getValue()!=null){
            sex = sexField.getValue();
        }

        LocalDate dateBirth =  datePicker.getValue();

        String birthPlace = birthPlaceField.getText();
        String docNumber = docNumberField.getText();

        Long idNumber;
        if(!idField.getText().isEmpty()){
            idNumber = Long.parseLong(idField.getText());
        }else{
            idNumber= 0L;
        }
        String docType = docTypeField.getText();

        String photo = null;
        if(filePhoto !=null){
            photo = filePhoto.getAbsolutePath();
            System.out.println("[RegistrationController] - [save] : filePhoto set");
        }

        System.out.println("Photo:" + photo);

        String DocumentFirstPage = null;
        if(fileFirstPage !=null){
            DocumentFirstPage = fileFirstPage.getAbsolutePath();
            System.out.println("[RegistrationController] - [save] : fileFirstPage set");
        }

        String DocumentSecondPage = null;
        if(fileSecondPage !=null){
            DocumentSecondPage = fileSecondPage.getAbsolutePath();
            System.out.println("[RegistrationController] - [save] : fileSecondPage set");
        }

        DataBaseHandler dataBaseHandler = new DataBaseHandler();

        try(Connection connection = dataBaseHandler.getConnection()){
            System.out.println("Photo:"+photo+"\n"+"Doc1:"+DocumentFirstPage+"\n"+"Doc2:"+DocumentSecondPage);
            dataBaseHandler.addClient(
                    name,
                    surname,
                    birthPlace,
                    sex,
                   docNumber,
                    idNumber,
                    docType,
                    dateBirth,
                    DocumentFirstPage,
                    DocumentSecondPage,
                    photo);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        System.out.println("[RegistrationController] - [save] : new Account was made and setted");



        resultLabel.setText("Saved");

        System.out.println("[RegistrationController] - [save] : new Account was saved");

        Thread.sleep(1000);

        switchToDataBase(event);
    }

    public void switchToDataBase(ActionEvent event) throws IOException {

        root = FXMLLoader.load(getClass().getResource("/com/wudima/docApp/dataBase.fxml"));

        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


}