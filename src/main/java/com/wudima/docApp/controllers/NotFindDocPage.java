package com.wudima.docApp.controllers;

import com.wudima.docApp.DocApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class NotFindDocPage implements Initializable {

    @FXML
    private Button homeBtn;


    @FXML
    private ImageView mainLogoView;

    @FXML
    private AnchorPane rootPane;

    public Parent root;
    public Stage stage;
    public Scene scene;
    Image logoImg;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        if(Optional.ofNullable(DocApplication.settings.getMainLogo()).isEmpty()){
            logoImg = new Image(getClass().getResourceAsStream(DocApplication.settings.getDefaultLogo()));
            System.out.println("[DetailsMainController] - [initialize] :: defaultLog");
        }else{
            try {
                logoImg = new Image(new FileInputStream(DocApplication.settings.getMainLogo()));
            } catch (FileNotFoundException e) {
                System.out.println("[DetailsMainController] - [initialize] :: MainLogo not found");

            }
            System.out.println("[DetailsMainController] - [initialize] :: MainLogo "+DocApplication.settings.getMainLogo());
        }

        mainLogoView.setImage(logoImg);

    }

    @FXML
    void switchHome(ActionEvent event) throws IOException {

        root = FXMLLoader.load(getClass().getResource("/com/wudima/docApp/Main.fxml"));

        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }


}
