package com.wudima.docApp;

import com.wudima.docApp.Entity.Account;
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
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class DocApplication extends Application {

    double x;
    double y;

    public static ArrayList <Account> accountsList;
    public static Image icon;
    public static AppSettings settings=AppSettings.load();

    private File logoPath = new File(settings.getLogoPath());
    private File photoPath = new File(settings.getPhotoPath());
    private File configPath = new File(settings.getConfigPath());


    @Override
    public void start(Stage stage) throws IOException {
        System.out.println("[DocApplication]::[start]--start");

        // make dirs to files
        if(!logoPath.exists()){
            logoPath.mkdirs();
        }

        if(!photoPath.exists()){
            photoPath.mkdirs();
        }

        if(!configPath.exists()){
            configPath.mkdirs();
        }


        //------------

        DataBaseHandler dataBaseHandler = new DataBaseHandler();
        try(Connection connection = dataBaseHandler.getConnection()){
            dataBaseHandler.createTableIfNotExist();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

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
        System.out.println("[DocApplication]::[start]--end");
    }

    public static void main(String[] args) throws IOException {

        System.out.println("[DocApplication]::[main]--start");

        launch();

        System.out.println("[DocApplication]::[main]--end");

    }









}