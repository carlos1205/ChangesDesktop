package com.change.client.controllers;

import com.change.client.EnumScenes;
import com.change.client.config.annotations.Controller;
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

@Controller
public class CadastroSPController implements IMenuHandle{
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

    private List<String> erros;

    public void initialize(){
        for(EnumCategoria categoria: EnumCategoria.values()){
            category.getItems().add(categoria.getName());
        }

        for(EnumVendaDoacaoTroca tipo: EnumVendaDoacaoTroca.values()){
            type.getItems().add(tipo.getName());
        }

        erros = new ArrayList<>();
    }

    public void handleCadastrar() {
        if(valida()){
            Item item = mountItem();
            String code = itemDao.insert(item);
            if(code != null){
                item.setCode(code);
                this.clear();
                stageFactory.changeScene(EnumScenes.HOME);
            }else{
                String error = itemDao.getMessage().get(0);
                this.setErrors(Arrays.asList(error));
            }
            System.out.println("Cadastrado");
        }else{
            String error = erros.get(0);
            setErrors(Arrays.asList(error));
            erros.clear();
        }
    }

    private void clear() {
        title.setText("");
        category.getSelectionModel().select(null);
        description.setText("");
        value.setText("");
        type.getSelectionModel().select(null);
        service.setSelected(false);
        product.setSelected(false);
        errors.setText("");
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
        CadastroSPController.stageFactory = stageFactory;
    }

    private void setErrors(List<String> errors){
        String errs = errors.stream()
                .map(error -> error + "\n")
                .reduce("", String::concat);

        this.errors.setText(errs);
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
}
