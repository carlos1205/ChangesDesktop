package com.change.client.controllers;

import com.change.client.EnumScenes;
import com.change.client.config.annotations.Inject;
import com.change.client.repository.chat.IChatDAO;
import com.change.client.service.StageFactory;
import com.change.client.service.Storage;
import com.change.model.Item;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.NodeOrientation;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class ChatController implements IMenuHandle{
    @Inject
    private static StageFactory stageFactory;
    @Inject
    private static IMenuHandle menu;
    @Inject
    private static IChatDAO chatDao;

    @FXML
    private TextField input;
    @FXML
    private Text itemName;
    @FXML
    private VBox messages;

    private Item item;

    @FXML
    public void initialize(){
        item = Storage.getInstance().getItem();
        Storage.getInstance().setItem(null);
        itemName.setText(item.getTitle());

        chatDao.setChat(this);
        chatDao.setPage(null);
    }

    public void handleSair(){
        Storage.getInstance().setItem(item);
        StageFactory.getInstance().destroy(EnumScenes.FECHA_CHAT);
        StageFactory.getInstance().changeScene(EnumScenes.FECHA_CHAT);
    }

    public void handleEnviar(){
        String text = input.getText();
        input.setText("");
        if(!"".equals(text)){
            addMessage(text);
            chatDao.sendMessage(text, this.item);
        }
    }

    public void handleReceive(String message){
        Text msg = new Text();
        msg.setText(message);
        msg.minHeight(20);
        msg.maxWidth(400);
        msg.setNodeOrientation(NodeOrientation.LEFT_TO_RIGHT);
        msg.setWrappingWidth(400);
        msg.setStyle("-fx-background-color: #F6F2D4");

        Pane pane = new Pane(msg);
        pane.setNodeOrientation(NodeOrientation.LEFT_TO_RIGHT);
        pane.setPadding(new Insets(10,100,0,0));

        messages.getChildren().add(pane);
    }

    private void addMessage(String message){
        Text msg = new Text();
        msg.setText(message);
        msg.minHeight(20);
        msg.maxWidth(400);
        msg.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
        msg.setWrappingWidth(400);
        msg.setStyle("-fx-background-color: #5584AC");

        Pane pane = new Pane(msg);
        pane.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
        pane.setPadding(new Insets(10,10,0,100));

        messages.getChildren().add(pane);
    }

    private void exit(){
        chatDao.fecharNegocio(false, item, null);
    }

    @Override
    public void handleGoHome() {
        this.exit();
        menu.handleGoHome();
    }

    @Override
    public void handleGoEdit() {
        this.exit();
        menu.handleGoEdit();
    }

    @Override
    public void handleDelecao() {
        this.exit();
        menu.handleDelecao();
    }

    @Override
    public void handleCadastrarItem() {
        this.exit();
        menu.handleCadastrarItem();
    }

    @Override
    public void handleListagem() {
        this.exit();
        menu.handleListagem();
    }

    @Override
    public void handleGoMyItens() {
        this.exit();
        menu.handleGoMyItens();
    }
}
