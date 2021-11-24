module com.change.client {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.json;


    opens com.change.client to javafx.fxml;
    exports com.change.client;
    exports com.change.client.controllers;
    opens com.change.client.controllers to javafx.fxml;
}