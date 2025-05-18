package com.wudima.docApp.controllers;

import com.wudima.docApp.DocApplication;
import com.wudima.docApp.settings.AppSettings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ResourceBundle;

public class SettingsController implements Initializable{


    @FXML
    private TextField programeName;
    @FXML
    private CheckBox fitToFramePhoto;
    @FXML
    private CheckBox fitToFrameDocument;
    @FXML
    private TextField logoName;


    public Parent root;
    public Stage stage;
    public Scene scene;
    public AppSettings settingsConfig = DocApplication.settings;
    public File fileLogo;
    private FileChooser fileChooserSettings = new FileChooser();


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        if(!settingsConfig.isPhotoFit()){
            fitToFramePhoto.setSelected(true);
        }

        if(!settingsConfig.isDocumentsFit()){
            fitToFrameDocument.setSelected(true);
        }
        logoName.setText(settingsConfig.getMainLogo().getAbsolutePath());
    }

    public void fitToFramePhotoOption(ActionEvent event){

        if(fitToFramePhoto.isSelected()){

            settingsConfig.setPhotoFit(false);

        }else{
            settingsConfig.setPhotoFit(true);
        }

    }

    public void fitToFrameDocumentsOption(ActionEvent event){

        if(fitToFrameDocument.isSelected()){
            settingsConfig.setDocumentsFit(false);
        }else{
            settingsConfig.setDocumentsFit(true);
        }
    }

    public void fileChooseLogo() {

        System.out.println("[SettingsController] - [fileChooseLogo] : start");

        fileLogo = fileChooserSettings.showOpenDialog(new Stage());
        logoName.setText(fileLogo.getAbsolutePath());

        System.out.println("[SettingsController] - [fileChooseLogo] : end");

    }

    public void switchToDataBase(ActionEvent event) throws IOException {

        DocApplication.settings = AppSettings.load();

        root = FXMLLoader.load(getClass().getResource("/com/wudima/docApp/dataBase.fxml"));

        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle(settingsConfig.getProgName());
        stage.show();

    }




    public void saveSettings(ActionEvent event) throws IOException, InterruptedException {

        if(fileLogo!=null) {

            settingsConfig.setMainLogo(copyLogoToResource(fileLogo));
            System.out.println("[SettingsController] - [saveSettings] :: filelogo path:"+fileLogo.getName());
        }

        if(!programeName.getText().isEmpty()) {
            settingsConfig.setProgName(programeName.getText());
        }
        settingsConfig.save();
        Thread.sleep(1000);
        switchToDataBase(event);
    }

    private File copyLogoToResource(File sourceFile) throws IOException {
        System.out.println("[copyLogoToResource]::starts");
        Path source = Path.of(sourceFile.getAbsolutePath());
        Path target = Path.of(DocApplication.getLogoPath()+"/"+sourceFile.getName());

        Files.copy(source,target, StandardCopyOption.REPLACE_EXISTING);
        System.out.println("[copyLogoToResource]::ended");
        return new File(target.toString());
    }
}
