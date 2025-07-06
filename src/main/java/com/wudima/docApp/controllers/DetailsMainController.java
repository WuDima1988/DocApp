package com.wudima.docApp.controllers;

import com.wudima.docApp.DocApplication;
import com.wudima.docApp.Entity.Account;
import com.wudima.docApp.settings.DataBaseHandler;
import javafx.animation.ScaleTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.awt.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
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

    Image logoImg ;


    private Stage zoomStage;
    public Account pickedAccount;
    public Parent root;
    public Stage stage;
    public Scene scene;

    public DetailsMainController() throws FileNotFoundException {
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        System.out.println("[DetailsMainController] - [initialize] :: MainLogo "+DocApplication.settings.getMainLogo());
        if(Optional.ofNullable(DocApplication.settings.getMainLogo()).isEmpty()){
            logoImg = new Image(getClass().getResourceAsStream(DocApplication.settings.getDefaultLogo()));
            System.out.println("[DetailsMainController] - [initialize] :: defaultLog");
        }else{
            logoImg = new Image(getClass().getResourceAsStream(DocApplication.settings.getMainLogo()));
            System.out.println("[DetailsMainController] - [initialize] :: MainLogo "+DocApplication.settings.getMainLogo());
        }



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


        photoImg.setOnMouseEntered(event -> showZoomedImage(photoImg));
        photoImg.setOnMouseExited(event -> hideZoomedImage());

        doc1Img.setOnMouseEntered(event -> showZoomedImage(doc1Img));
        doc1Img.setOnMouseExited(event -> hideZoomedImage());

        doc2Img.setOnMouseEntered(event -> showZoomedImage(doc2Img));
        doc2Img.setOnMouseExited(event -> hideZoomedImage());


    }

    public void details(int id) throws FileNotFoundException, SQLException {

        System.out.println("[DetailsMainController] - [details] : start / int id = "+id );

        DataBaseHandler dbh = new DataBaseHandler();

        System.out.println("[DetailsMainController] - [details] : find pickedAccount " );
        pickedAccount = dbh.findAccountById(id);

        System.out.println("[DetailsMainController] - [details] : set all fields " );
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
        idField.setText(Optional.ofNullable(pickedAccount.getIdNumber()).orElse(("")));
        docTypeField.setText(Optional.ofNullable(pickedAccount.getDocType()).orElse(""));



        if(pickedAccount.getPhoto()!= null) {
            Image img = new Image(new FileInputStream(pickedAccount.getPhoto()));
            photoImg.setImage(img);
            photoImg.setPreserveRatio(DocApplication.settings.isPhotoFit());
        }else{
            Image img = new Image(new FileInputStream(DocApplication.settings.getNoPhotoImg()));
            photoImg.setImage(img);

        }

        if(pickedAccount.getDocumentFirstPage()!= null) {
            Image img1 = new Image(new FileInputStream(pickedAccount.getDocumentFirstPage()));
            doc1Img.setImage(img1);
            doc1Img.setPreserveRatio(DocApplication.settings.isDocumentsFit());
        }else{
            Image img = new Image(new FileInputStream(DocApplication.settings.getNoDocImg()));
            doc1Img.setImage(img);
        }

        if(pickedAccount.getDocumentSecondPage()!= null) {
            Image img2 = new Image(new FileInputStream(pickedAccount.getDocumentSecondPage()));
            doc2Img.setImage(img2);
            doc2Img.setPreserveRatio(DocApplication.settings.isDocumentsFit());
        }else {
            Image img = new Image(new FileInputStream(DocApplication.settings.getNoDocImg()));
            doc2Img.setImage(img);
        }

        System.out.println("[DetailsMainController] - [details] : end");

    }

    public void switchToEditn(ActionEvent event) throws IOException, SQLException {

        int accId = pickedAccount.getId();

        System.out.println("[DetailsMainController] - [switchToEditn]:: id:"+accId);

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


    private void showZoomedImage(ImageView imageView) {

        if (zoomStage != null && zoomStage.isShowing()) return;

        Image image = imageView.getImage();

        ImageView zoomedImageView = new ImageView(image);
        zoomedImageView.setFitWidth(400);  // можна змінити розмір
        zoomedImageView.setPreserveRatio(true);

        StackPane root = new StackPane(zoomedImageView);
        Scene scene = new Scene(root); // або автоматично по картинці

        zoomStage = new Stage();
        zoomStage.initStyle(StageStyle.UNDECORATED); // без рамки
        zoomStage.setAlwaysOnTop(true);              // поверх інших
        zoomStage.setScene(scene);

        // Отримуємо позицію imageView на екрані
        Bounds bounds = imageView.localToScreen(imageView.getBoundsInLocal());

        // Встановлюємо позицію вікна ПРАВОРУЧ І НИЖЧЕ imageView
        zoomStage.setX(bounds.getMaxX() + 10);
        zoomStage.setY(bounds.getMinY());


        zoomStage.show();
    }

    private void hideZoomedImage() {
        if (zoomStage != null) {
            zoomStage.close();
        }
    }




}

