package com.wudima.docApp;

import com.wudima.docApp.account.Account;
import com.wudima.docApp.settings.AppSettings;
import com.wudima.docApp.settings.DataBaseHandler;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class DocApplication extends Application {

    double x;
    double y;

    private static final String photoPath = System.getProperty("user.home") +File.separator + "Documents"+ File.separator + "DocFinder" + File.separator + "photo";
    private static final String logoPath = System.getProperty("user.home") +File.separator + "Documents"+ File.separator + "DocFinder" + File.separator + "logo";

    private static File photoFolder = new File(photoPath);

    public static ArrayList <Account> accountsList;
    public static Image icon;
    public static AppSettings settings=AppSettings.load();


    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(DocApplication.class.getResource("dataBase.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        icon = new Image(getClass().getResourceAsStream("imgs/icon.png"));
        stage.setScene(scene);
        stage.setTitle(settings.getProgName());
        stage.getIcons().add(icon);


        root.setOnMousePressed((MouseEvent event)->{

            x = event.getScreenX();
            y = event.getScreenY();
        });

        root.setOnMouseDragged((MouseEvent event)->{

            stage.setX(event.getScreenX());
            stage.setY(event.getScreenY());
        });


        stage.isAlwaysOnTop();
        stage.show();
    }

    public static void main(String[] args) throws IOException {

        launch();

    }


    public static String getLogoPath(){
        return logoPath;
    }








}