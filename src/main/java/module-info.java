module com.change.client {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.json;
    requires org.hibernate.commons.annotations;
    requires org.hibernate.orm.core;
    requires java.persistence;
    requires org.postgresql.jdbc;

    opens com.change.client to javafx.fxml;
    exports com.change.client;
    exports com.change.client.controllers;
    opens com.change.client.controllers to javafx.fxml;

    opens com.change.server to javafx.fxml;
    exports com.change.server;
    exports com.change.server.GUI.controllers;
    opens com.change.server.GUI.controllers to javafx.fxml;
}