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
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.ResourceBundle;

public class DetailsMainController implements Initializable {


    @FXML
    private TextField nameField;

    @FXML
    private TextField surnameField;

    @FXML
    private TextField birthPlaceField;

    @FXML
    private TextField sexField;

    @FXML
    private TextField dateField;

    @FXML
    private TextField docNumberField;

    @FXML
    private TextField idField;

    @FXML
    private TextField docTypeField;








    @FXML
    private ImageView mainLogo;
    @FXML
    private ImageView photoImg;
    @FXML
    private ImageView doc1Img;
    @FXML
    private ImageView doc2Img;

    Image logoImg = new Image(new FileInputStream(DocApplication.settings.getMainLogo()));

    public Account pickedAccount;
    public Parent root;
    public Stage stage;
    public Scene scene;

    public DetailsMainController() throws FileNotFoundException {
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {


        mainLogo.setImage(logoImg);

        nameField.setEditable(false);
        nameField.setFocusTraversable(false);
        surnameField.setEditable(false);
        surnameField.setFocusTraversable(false);
        sexField.setEditable(false);
        sexField.setFocusTraversable(false);
        birthPlaceField.setEditable(false);
        birthPlaceField.setFocusTraversable(false);
        dateField.setEditable(false);
        dateField.setFocusTraversable(false);
        docNumberField.setEditable(false);
        docNumberField.setFocusTraversable(false);
        idField.setEditable(false);
        idField.setFocusTraversable(false);
        docTypeField.setEditable(false);
        docTypeField.setFocusTraversable(false);
    }

    public void details(int id) throws FileNotFoundException, SQLException {

        DataBaseHandler dbh = new DataBaseHandler();



        pickedAccount = dbh.findAccountById(id);

        nameField.setText(Optional.ofNullable(pickedAccount.getName()).orElse(""));
        surnameField.setText(Optional.ofNullable(pickedAccount.getSurname()).orElse(""));
        sexField.setText(Optional.ofNullable(pickedAccount.getSex()).orElse(("")));
        birthPlaceField.setText(Optional.ofNullable(pickedAccount.getBirthPlace()).orElse(""));
        dateField.setText(
                Optional.ofNullable(pickedAccount.getBirthDate())
                        .map(date -> date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                        .orElse("")
        );
        docNumberField.setText(Optional.ofNullable(pickedAccount.getDocNumber()).orElse(""));
        idField.setText(Optional.ofNullable(pickedAccount.getIdNumber()).map(i->i.toString()).orElse(("")));
        docTypeField.setText(Optional.ofNullable(pickedAccount.getDocType()).orElse(""));



        if(pickedAccount.getPhoto()!= null) {
            Image img = new Image(new FileInputStream(pickedAccount.getPhoto()));
            photoImg.setImage(img);
            photoImg.setPreserveRatio(DocApplication.settings.isPhotoFit());
        }

        if(pickedAccount.getDocumentFirstPage()!= null) {
            Image img1 = new Image(new FileInputStream(pickedAccount.getDocumentFirstPage()));
            doc1Img.setImage(img1);
            doc1Img.setPreserveRatio(DocApplication.settings.isDocumentsFit());
        }

        if(pickedAccount.getDocumentSecondPage()!= null) {
            Image img2 = new Image(new FileInputStream(pickedAccount.getDocumentSecondPage()));
            doc2Img.setImage(img2);
            doc2Img.setPreserveRatio(DocApplication.settings.isDocumentsFit());
        }

    }

    public void switchToEditn(ActionEvent event) throws IOException, SQLException {

        int accId = pickedAccount.getId();

        System.out.println("[switchToEditn]:: id:"+accId);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/wudima/docApp/editPage.fxml"));

        root = loader.load();

        editPageController editPageController= loader.getController();

        editPageController.details(accId);

        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToDataBase(ActionEvent event) throws IOException {

        root = FXMLLoader.load(getClass().getResource("/com/wudima/docApp/dataBase.fxml"));

        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle(DocApplication.settings.getProgName());
        stage.show();

    }




}

