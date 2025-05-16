module com.wudima.docApp {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;

    requires java.sql;
    requires javafx.media;
    requires com.fasterxml.jackson.databind;


    exports com.wudima.docApp;
    opens com.wudima.docApp to javafx.fxml;
    opens com.wudima.docApp.account to javafx.fxml;
    exports com.wudima.docApp.account;
    exports com.wudima.docApp.settings;
    opens com.wudima.docApp.settings to javafx.fxml;
    exports com.wudima.docApp.controllers;
    opens com.wudima.docApp.controllers to javafx.fxml;

}