package com.wudima.docApp;

import com.wudima.docApp.account.Account;
import com.wudima.docApp.settings.AppSettings;
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

    private static final String path = System.getProperty("user.home") +File.separator + "Documents"+ File.separator + "DocFinder" + File.separator + "data" + File.separator + "accountsBase.bin";
    private static final String photoPath = System.getProperty("user.home") +File.separator + "Documents"+ File.separator + "DocFinder" + File.separator + "photo";
    private static final String logoPath = System.getProperty("user.home") +File.separator + "Documents"+ File.separator + "DocFinder" + File.separator + "logo";

    private static File photoFolder = new File(photoPath);
    private static File base = new File(path);
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

        getList();
        if(!Files.exists(Path.of(photoPath))){
            Files.createDirectories(Path.of(photoPath));
        }
        if(!Files.exists(Path.of(logoPath))){
            Files.createDirectories(Path.of(logoPath));
        }
        launch();

    }

    public static void writeToBase(){
        serialization();
    }

    public static String getLogoPath(){
        return logoPath;
    }

//    public static String getMainLogo() {
//        return mainLogo;
//    }

    private static void getList() throws IOException {

        if(base.exists()){
            accountsList = deserialisation();
            System.out.println("[deserialization]::Ends");
            System.out.println("Main Base:"+base.getAbsolutePath());
            accountsList.forEach(System.out::println);
        }else{
            base.getParentFile().mkdirs();
            var creatingNewFileBase = base.createNewFile();
            System.out.println("[getList]::{creatingNewFileBase}"+creatingNewFileBase+base.getAbsolutePath());
            accountsList = new ArrayList<>();
            serialization();

        }
    }


    private static ArrayList deserialisation() {

        System.out.println("[deserialization]::Start");
        try(ObjectInputStream inputStream = new ObjectInputStream(Files.newInputStream(Path.of(path)))){
            return (ArrayList)inputStream.readObject();


        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


    private static void serialization() {
        System.out.println("[serialization]::Start");

        try (ObjectOutputStream outputStream = new ObjectOutputStream(Files.newOutputStream(Path.of(path)))){

            outputStream.writeObject(accountsList);
//            result.setText("Data saved!");
            System.out.println("[serialization]::Data saved");

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}