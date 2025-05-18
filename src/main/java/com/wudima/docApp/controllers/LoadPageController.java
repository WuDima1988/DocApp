package com.wudima.docApp.controllers;

import com.wudima.docApp.DocApplication;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.stage.Stage;
import javafx.scene.media.MediaPlayer;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class LoadPageController implements Initializable {

    @FXML
    private AnchorPane rootPane;
    @FXML
    private Label loadText;

    @FXML
    private ImageView mainLogoView;

    public Parent root;
    public Stage stage;
    public Scene scene;
    Image mainLogo = new Image(new FileInputStream(DocApplication.settings.getMainLogo()));
    int id;
    Thread t;
    public Runnable runnableThread;

    public LoadPageController() throws FileNotFoundException {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        mainLogoView.setImage(mainLogo);
        loadText.setText("Loading...");

        System.out.println("[init]:: id:"+id);

    }

    public void setId(int id) {
        System.out.println("[setid]:: income id:"+id);
        this.id = id;
        t=new Thread(new LoadPane(id));
        t.start();
        System.out.println("[setid]:: final id:"+id);
    }

    class LoadPane implements Runnable{


        int id;

        public LoadPane(int id) {
            this.id = id;
        }

        @Override
        public void run() {

            System.out.println("[run]:: id:"+id);

            try {


                Thread.sleep(4000);

                URL resource = getClass().getResource("/com/wudima/docApp/sound/bipsound.m4a");
                if (resource == null) {
                    System.err.println("[ERROR] Аудіофайл не знайдено!");
                } else {
                    Media media = new Media(resource.toExternalForm());
                    MediaPlayer mediaPlayer = new MediaPlayer(media);
                    mediaPlayer.play();
                }


                Thread.sleep(1500);

                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {

                        try {

                           FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/wudima/docApp/detailsPage.fxml"));

                               root = loader.load();


                           DetailsController detailsController = loader.getController();

                               detailsController.details(id);



                           stage = new Stage();
                           scene = new Scene(root);
                           stage.setScene(scene);
                           stage.setTitle(DocApplication.settings.getProgName());
                           stage.getIcons().add(DocApplication.icon);
                           stage.show();

                           rootPane.getScene().getWindow().hide();

                       }catch (IOException e){
                           throw new RuntimeException(e);
                       } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }

                    }
                });



            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }


        }
    }
}
