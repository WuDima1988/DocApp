package com.wudima.docApp.controllers;


import com.wudima.docApp.DocApplication;
import com.wudima.docApp.account.Account;
import com.wudima.docApp.exceptions.NoPickedAccountException;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class DataBaseController implements Initializable {

    @FXML
    private Button deleteBtn;

    @FXML
    private Button detailsBtn;

    @FXML
    private TextField nameField;

    @FXML
    private Button registerBtn;

    @FXML
    private Button searchBtn;

    @FXML
    private Button loadBtn;

    @FXML
    private TextField surnameField;

    @FXML
    private TableColumn<Account, String> nameTable;

    @FXML
    private TableColumn<Account, String> surnameTable;

    @FXML
    private TableColumn<Account, String> sexTable;

    @FXML
    private TableColumn<Account, String> birthPlaceTable;

    @FXML
    private TableColumn<Account,String> docTable;

    @FXML
    private TableView<Account> tableView;

    @FXML
    private TextField nameSearch;

    @FXML
    private TextField surnameSearch;


    public Parent root;
    public Stage stage;
    public Scene scene;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        nameTable.setCellValueFactory(new PropertyValueFactory<Account,String>("name"));
        surnameTable.setCellValueFactory(new PropertyValueFactory<Account,String>("surname"));
        sexTable.setCellValueFactory(new PropertyValueFactory<Account,String>("sex"));
        birthPlaceTable.setCellValueFactory(new PropertyValueFactory<Account,String>("birthPlace"));
        docTable.setCellValueFactory(new PropertyValueFactory<Account,String>("docBase"));

        setTableView();

        System.out.println("[DataBaseController]-[initialize] :: Base:");
        DocApplication.accountsList.forEach(System.out::println);


    }

    @FXML
    private void setTableView() {
        tableView.setItems(showBase());
    }


    @FXML
    public void deleteAccount(ActionEvent event) {

        System.out.println("[DataBaseController]-[deleteAccount] :: start");

        Account deleteAcc = DocApplication.accountsList.stream().filter(acc->acc.getId()==clickedAccount(event)).findFirst().get();

        DocApplication.accountsList.remove(deleteAcc);

        DocApplication.writeToBase();

        showBase();

        System.out.println("[DataBaseController]-[deleteAccount] :: ends");

    }

    public int clickedAccount(ActionEvent event){

        Account clickAccount = tableView.getSelectionModel().getSelectedItem();
        if(clickAccount==null){

            alertPickAccount();
            throw new NoPickedAccountException("Account wasn't picked from the table ");
        }
        int id = clickAccount.getId();
        return id;
    }


    public void switchToRegistration(ActionEvent event) throws IOException {

        root = FXMLLoader.load(getClass().getResource("registrationPage.fxml"));

        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToDetails(ActionEvent event) throws IOException {

        int accId = clickedAccount(event);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("detailsPageMain.fxml"));

        root = loader.load();

        DetailsMainController detailsMainController = loader.getController();

        detailsMainController.details(accId);

        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }




    public void switchToDLoad(ActionEvent event) throws IOException {

        int accId = clickedAccount(event);

        System.out.println("[switchToDLoad]:: id:"+accId);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("loadPage.fxml"));

        root = loader.load();

        LoadPageController loadPageController= loader.getController();

        loadPageController.setId(accId);

        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    public void alertPickAccount(){

        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning!");
        alert.setHeaderText(null); // або щось на зразок "Помилка вибору"
        alert.setContentText("Select an account from the table!");
        alert.showAndWait();

    }

    private ObservableList<Account> showBase() {

        tableView.getItems().removeAll(tableView.getItems());

        System.out.println("[DataBaseController] - [showBase]:run");

        ObservableList<Account> list = tableView.getItems();

        ArrayList<Account> accList = DocApplication.accountsList;

        list.addAll(accList);

        System.out.println("[DataBaseController] - [showBase]:end");

        return list;
    }

    public void search(){

        tableView.setItems(showBaseBySearch());
    }

    private ObservableList<Account> showBaseBySearch() {

        String name = Optional.of(nameSearch.getText()).orElseGet(()->"").toLowerCase();
        String surname = Optional.of(surnameSearch.getText()).orElseGet(()->"").toLowerCase();

        tableView.getItems().removeAll(tableView.getItems());

        System.out.println("[DataBaseController] - [showBase]:run");

        ObservableList<Account> list = tableView.getItems();

        ArrayList<Account> accList=new ArrayList<>();

        if(!name.isEmpty() && surname.isEmpty()){
            accList = DocApplication.accountsList.stream().filter(acc->acc.getName().toLowerCase().equals(name)).collect(Collectors.toCollection(ArrayList::new));
        }
        else if (name.isEmpty() && !surname.isEmpty()) {
            accList = DocApplication.accountsList.stream().filter(acc->acc.getSurname().toLowerCase().equals(surname)).collect(Collectors.toCollection(ArrayList::new));
        }
        else if (!name.isEmpty() && !surname.isEmpty()) {
            accList = DocApplication.accountsList.stream().filter(acc->acc.getName().toLowerCase().equals(surname)&&acc.getSurname().toLowerCase().equals(surname)).collect(Collectors.toCollection(ArrayList::new));
        } else if (name.isEmpty() && surname.isEmpty()) {
            return showBase();
        }


        list.addAll(accList);

        System.out.println("[DataBaseController] - [showBase]:end");

        return list;
    }


    public void switchToSettings(ActionEvent event) throws IOException {

        root = FXMLLoader.load(getClass().getResource("settingsPage.fxml"));

        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


}
