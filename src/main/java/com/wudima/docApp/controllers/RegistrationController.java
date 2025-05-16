package com.wudima.docApp.controllers;


import com.wudima.docApp.DocApplication;
import com.wudima.docApp.account.Account;
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

        Account newAcc = new Account();

        newAcc.setName(Optional.of(nameField.getText()).orElseGet(()->""));
        newAcc.setSurname(Optional.of(surnameField.getText()).orElseGet(()->""));
        if(sexField.getValue()!=null){
            newAcc.setSex(sexField.getValue());
        }
        if(datePicker.getValue()!=null){
            newAcc.setBirthDate(datePicker.getValue());
        }

        newAcc.setBirthPlace(Optional.of(birthPlaceField.getText()).orElseGet(()->""));
        newAcc.setDocNumber(Optional.of(docNumberField.getText()).orElseGet(()->""));
        if(!idField.getText().isEmpty()){
            newAcc.setIdNumber(Long.parseLong(idField.getText()));
        }
        newAcc.setDocType(Optional.of(docTypeField.getText()).orElseGet(()->""));

        if(filePhoto !=null){
            newAcc.setPhoto(filePhoto);
            System.out.println("[RegistrationController] - [save] : filePhoto set");
        }

        if(fileFirstPage !=null){
            newAcc.setDocumentFirstPage(fileFirstPage);
            System.out.println("[RegistrationController] - [save] : fileFirstPage set");
        }

        if(fileSecondPage !=null){
            newAcc.setDocumentSecondPage(fileSecondPage);
            System.out.println("[RegistrationController] - [save] : fileSecondPage set");
        }

        System.out.println("[RegistrationController] - [save] : new Account was made and setted");

        DocApplication.accountsList.add(newAcc);

        DocApplication.writeToBase();

        resultLabel.setText("Saved");

        System.out.println("[RegistrationController] - [save] : new Account was saved");

        Thread.sleep(1000);

        switchToDataBase(event);
    }

    public void switchToDataBase(ActionEvent event) throws IOException {

        root = FXMLLoader.load(getClass().getResource("dataBase.fxml"));

        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


}