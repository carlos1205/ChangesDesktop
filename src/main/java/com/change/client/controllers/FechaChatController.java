package com.change.client.controllers;

import com.change.client.config.annotations.Inject;
import com.change.client.repository.chat.IChatDAO;
import com.change.client.service.StageFactory;
import com.change.client.service.Storage;
import com.change.model.Item;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;

public class FechaChatController implements IMenuHandle{
    @Inject
    private static StageFactory stageFactory;
    @Inject
    private static IMenuHandle menu;
    @Inject
    private static IChatDAO chatDao;

    @FXML
    private Text messages;
    @FXML
    private Button sim;
    @FXML
    private Button nao;
    @FXML
    private Pane pane;

    private Item item;

    @FXML
    public void initialize(){
        item = Storage.getInstance().getItem();
        Storage.getInstance().setItem(null);
        messages.setFill(Paint.valueOf("black"));
    }

    public void handleNao(){
        removeButtons();

        messages.setText("Ok, esperamos que tenha tido uma boa experiencia.");
        chatDao.fecharNegocio(false, item, null);
    }

    public void handleSim(){
        removeButtons();

        messages.setText("Muito bom, aguarde só um instante. Por Favor.\n OBS: caso saia a negociação será cancelada.");
        boolean res = chatDao.fecharNegocio(true, item, this);
        if(!res){
            handleConfirmacao(chatDao.getMessage().get(0), false);
        }
    }

    public void handleConfirmacao(String message, boolean isFechado){
        removeButtons();

        if(isFechado)
            messages.setFill(Paint.valueOf("green"));
        else
            messages.setFill(Paint.valueOf("red"));

        item = null;
        messages.setText(message);
    }

    private void removeButtons(){
        pane.getChildren().remove(sim);
        pane.getChildren().remove(nao);
    }

    private void exit(){
        if(null != item)
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