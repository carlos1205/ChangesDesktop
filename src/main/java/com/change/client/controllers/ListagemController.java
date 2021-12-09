package com.change.client.controllers;

import com.change.client.EnumScenes;
import com.change.client.config.annotations.Controller;
import com.change.client.config.annotations.Inject;
import com.change.client.repository.chat.ChatDAO;
import com.change.client.repository.chat.IChatDAO;
import com.change.client.repository.item.IItemDAO;
import com.change.client.service.StageFactory;
import com.change.client.service.Storage;
import com.change.client.service.adapters.ItemViewAdapter;
import com.change.model.Item;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
public class ListagemController implements IMenuHandle{
    @Inject
    private static StageFactory stageFactory;
    @Inject
    private static IMenuHandle menu;
    @Inject
    private static IItemDAO<Item> itemDao;
    @Inject
    private static IChatDAO chatDao;

    @FXML
    private TableView<ItemViewAdapter> tableView;
    @FXML
    private Button conversar;
    @FXML
    private Text errors;

    private List<Item> itens;

    @FXML
    public void initialize(){
        setButtons(false);

        TableColumn<ItemViewAdapter, String> title = new TableColumn<>("Titulo");
        tableView.getColumns().add(title);
        title.setCellValueFactory(new PropertyValueFactory<>("title"));

        TableColumn<ItemViewAdapter, String> preco = new TableColumn<>("Preço");
        tableView.getColumns().add(preco);
        preco.setCellValueFactory(new PropertyValueFactory<>("price"));

        TableColumn<ItemViewAdapter, String> tipo = new TableColumn<>("Tipo");
        tableView.getColumns().add(tipo);
        tipo.setCellValueFactory(new PropertyValueFactory<>("type"));

        TableColumn<ItemViewAdapter, String> category = new TableColumn<>("Categoria");
        tableView.getColumns().add(category);
        category.setCellValueFactory(new PropertyValueFactory<>("category"));

        TableColumn<ItemViewAdapter, String> finalidade = new TableColumn<>("Finalidade");
        tableView.getColumns().add(finalidade);
        finalidade.setCellValueFactory(new PropertyValueFactory<>("finaly"));

        TableColumn<ItemViewAdapter, String> description = new TableColumn<>("Descrição");
        description.setMinWidth(200);
        tableView.getColumns().add(description);
        description.setCellValueFactory(new PropertyValueFactory<>("description"));

        itens = itemDao.getAll();
        tableView.getItems().setAll(converter(itens));
    }

    public void handleClick(){
        ItemViewAdapter selected = tableView.getSelectionModel().getSelectedItem();
        boolean buttonShow = false;
        buttonShow = selected != null;
        buttonShow = buttonShow && !selected.getOwner().equals(Storage.getInstance().getUser());
        setButtons(buttonShow);
    }

    public void handleChat(){
        Item itemEditar = itens.stream().filter(item -> item.getCode().equals(tableView.getSelectionModel().getSelectedItem().getCode()))
                .findFirst().orElse(null);
        this.errors.setText("");
        if(chatDao.openChat(itemEditar)){
            Storage.getInstance().setItem(itemEditar);
            StageFactory.getInstance().destroy(EnumScenes.CHAT);
            StageFactory.getInstance().changeScene(EnumScenes.CHAT);
        }else{
            String error = chatDao.getMessage().get(0);
            this.setErrors(Arrays.asList(error));
        }
    }

    private void setButtons(boolean active){
        conversar.setMouseTransparent(!active);
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

    private List<ItemViewAdapter> converter(List<Item> itens){
        List<ItemViewAdapter> viewAdapters = new ArrayList<>();
        itens.forEach(item -> viewAdapters.add(translate(item)));
        return viewAdapters;
    }

    private ItemViewAdapter translate(Item item) {
        ItemViewAdapter viewAdapter = new ItemViewAdapter();
        viewAdapter.setCode(item.getCode());
        viewAdapter.setTitle(item.getTitle());

        String price = String.valueOf(item.getPrice()).replace('.', ',');
        viewAdapter.setPrice(price);

        viewAdapter.setType(item.getSp().getName());
        viewAdapter.setCategory(item.getCategory().getName());
        viewAdapter.setFinaly(item.getVdt().getName());
        viewAdapter.setDescription(item.getDescription());
        viewAdapter.setOwner(item.getOwner());
        return viewAdapter;
    }

    private void setErrors(List<String> errors){
        String errs = errors.stream()
                .map(error -> error + "\n")
                .reduce("", String::concat);

        this.errors.setText(errs);
    }
}
