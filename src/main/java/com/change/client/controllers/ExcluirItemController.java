package com.change.client.controllers;

import com.change.client.EnumScenes;
import com.change.client.config.annotations.Controller;
import com.change.client.config.annotations.Inject;
import com.change.client.repository.item.IItemDAO;
import com.change.client.repository.user.IUserDAO;
import com.change.client.service.StageFactory;
import com.change.client.service.Storage;
import com.change.model.Item;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.text.Text;

import java.util.List;

@Controller
public class ExcluirItemController implements IMenuHandle{
    @Inject
    private static IItemDAO itemDao;
    @Inject
    private static StageFactory stageFactory;
    @Inject
    private static IMenuHandle menu;

    @FXML
    private Text sp;
    @FXML
    private Text errors;

    private Item item;

    public void initialize(){
        item = Storage.getInstance().getItem();
        Storage.getInstance().setItem(null);

        sp.setText("Item: "+item.getTitle());
    }

    public void handleExcluir(ActionEvent event){
        if(itemDao.delete(item.getCode())){
            this.clear();
            menu.handleGoMyItens();
        } else{
            setErrors(itemDao.getMessage());
        }
    }

    private void setErrors(List<String> errors){
        String errs = errors.stream()
                .map(error -> error + "\n")
                .reduce("", String::concat);

        this.errors.setText(errs);
    }

    private void clear(){
        this.errors.setText("");
    }

    @Override
    public void handleGoHome() {
        menu.handleGoHome();
    }

    @Override
    public void handleGoEdit() {
        menu.handleGoEdit();
    }

    @Override
    public void handleDelecao() {
        menu.handleDelecao();
    }

    @Override
    public void handleCadastrarItem() {
        menu.handleCadastrarItem();
    }

    @Override
    public void handleListagem() {
        menu.handleListagem();
    }

    @Override
    public void handleGoMyItens() {
        menu.handleGoMyItens();
    }
}