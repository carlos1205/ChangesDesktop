package com.change.client.controllers;

import com.change.client.EnumScenes;
import com.change.client.config.annotations.Controller;
import com.change.client.config.annotations.Inject;
import com.change.client.service.StageFactory;
import com.change.model.EnumCategoria;
import com.change.model.EnumVendaDoacaoTroca;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;

@Controller
public class CadastroSPController implements IMenuHandle{
    @Inject
    private static StageFactory stageFactory;
    @Inject
    private static IMenuHandle menu;

    @FXML
    private TextField title;
    @FXML
    private ComboBox category;
    @FXML
    private TextArea description;
    @FXML
    private TextField value;
    @FXML
    private ComboBox type;
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
        System.out.println("Cadastrar: ");
        if(valida()){
            System.out.println("Cadastrado");
        }else{
            setErrors(erros);
            erros.clear();
        }
    }

    private boolean valida(){
        boolean isValid = true;

        if("" == title.getText()) {
            erros.add("Titulo Vazio");
            isValid = false;
        }
        if(null == category.getSelectionModel().getSelectedItem()){
            erros.add("Categoria não selecionada");
            isValid = false;
        }
        if("" == description.getText()){
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
