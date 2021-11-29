module com.change.client {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.json;
    requires org.hibernate.commons.annotations;
    requires org.hibernate.orm.core;
    requires java.persistence;


    opens com.change.client to javafx.fxml;
    exports com.change.client;
    exports com.change.client.controllers;
    opens com.change.client.controllers to javafx.fxml;
}