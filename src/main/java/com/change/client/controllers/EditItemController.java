package com.change.client.controllers;

import com.change.client.EnumScenes;
import com.change.client.config.annotations.Inject;
import com.change.client.repository.item.IItemDAO;
import com.change.client.service.StageFactory;
import com.change.client.service.Storage;
import com.change.model.*;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EditItemController implements IMenuHandle{
    @Inject
    private static StageFactory stageFactory;
    @Inject
    private static IMenuHandle menu;
    @Inject
    private static IItemDAO<Item> itemDao;

    @FXML
    private TextField title;
    @FXML
    private ComboBox<String> category;
    @FXML
    private TextArea description;
    @FXML
    private TextField value;
    @FXML
    private ComboBox<String> type;
    @FXML
    private RadioButton service;
    @FXML
    private RadioButton product;

    @FXML
    private Text errors;
    @FXML
    private Text success;

    private List<String> erros;

    private String code;
    private User owner;

    public void initialize(){
        for(EnumCategoria categoria: EnumCategoria.values()){
            category.getItems().add(categoria.getName());
        }

        for(EnumVendaDoacaoTroca tipo: EnumVendaDoacaoTroca.values()){
            type.getItems().add(tipo.getName());
        }

        Item item = Storage.getInstance().getItem();
        Storage.getInstance().setItem(null);

        title.setText(item.getTitle());
        category.getSelectionModel().select(item.getCategory().getName());
        description.setText(item.getDescription());

        String price = String.valueOf(item.getPrice()).replace('.',',');
        value.setText(price);
        type.getSelectionModel().select(item.getVdt().getName());

        if(item.getSp() == EnumServicoProduto.PRODUTO)
            product.setSelected(true);
        else
            service.setSelected(true);

        this.code = item.getCode();
        this.owner = item.getOwner();
        erros = new ArrayList<>();
    }

    public void handleEditar() {
        errors.setText("");
        success.setText("");

        if(valida()){
            Item item = mountItem();
            boolean res = itemDao.update(item);
            if(res){
                String success = itemDao.getMessage().get(0);
                this.setSuccess(Arrays.asList(success));
            }else{
                String error = itemDao.getMessage().get(0);
                this.setErrors(Arrays.asList(error));
            }
        }else{
            String error = erros.get(0);
            setErrors(Arrays.asList(error));
            erros.clear();
        }
    }

    private Item mountItem(){
        EnumCategoria categoria = null;
        EnumVendaDoacaoTroca vdt = null;
        for(EnumCategoria cat : EnumCategoria.values()){
            if(cat.getName().equals(category.getSelectionModel().getSelectedItem())){
                categoria = cat;
                break;
            }
        }

        for(EnumVendaDoacaoTroca typeVDT : EnumVendaDoacaoTroca.values()){
            if(typeVDT.getName().equals(type.getSelectionModel().getSelectedItem())){
                vdt = typeVDT;
                break;
            }
        }

        Item item = new Item(
                title.getText(),
                description.getText(),
                Storage.getInstance().getUser(),
                categoria,
                service.isSelected() ? EnumServicoProduto.SERVICO : EnumServicoProduto.PRODUTO,
                vdt
        );

        if(!"".equals(value.getText())){
            String price = value.getText().replace(',', '.');
            item.setPrice(Float.parseFloat(price));
        }

        item.setCode(code);
        item.setOwner(owner);
        item.setStatus(EnumStatus.ABERTO);
        return item;
    }

    private boolean valida(){
        boolean isValid = true;

        if("".equals(title.getText())) {
            erros.add("Titulo Vazio");
            isValid = false;
        }
        if(null == category.getSelectionModel().getSelectedItem()){
            erros.add("Categoria não selecionada");
            isValid = false;
        }
        if("".equals(description.getText())){
            erros.add("Descrição Vazia");
            isValid = false;
        }
        if(null == type.getSelectionModel().getSelectedItem()){
            erros.add("Tipo não selecionado");
            isValid = false;
        }
        if((!service.isSelected() && !product.isSelected())){
            erros.add("Serviço não selecionado");
            isValid = false;
        }
        return isValid;
    }

    public void setStageFactory(StageFactory stageFactory){
        EditItemController.stageFactory = stageFactory;
    }

    private void setErrors(List<String> errors){
        String errs = errors.stream()
                .map(error -> error + "\n")
                .reduce("", String::concat);

        this.errors.setText(errs);
    }

    private void setSuccess(List<String> success){
        String suc = success.stream()
                .map(s -> s + "\n")
                .reduce("", String::concat);

        this.success.setText(suc);
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
