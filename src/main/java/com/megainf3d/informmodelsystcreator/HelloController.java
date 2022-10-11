package com.megainf3d.informmodelsystcreator;

import com.megainf3d.informmodelsystcreator.Tables.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * controller class
 */
public class HelloController implements Initializable {

    public AnchorPane pane;
    public ToggleButton delete;
    public ToggleButton manyToMany;
    public ToggleButton oneToMany;
    public ToggleButton oneToOne;
    public ToggleButton add;
    public ChoiceBox<AttributeType> types;
    public ChoiceBox<KeyType> keyTypes;
    public VBox attrSettings;
    public TextField atrTitle;
    Table[] connectionBlocks = new Table[2];
    RelationType relationType;
    Attribute selectedAttribute;
    Tables tables = new Tables();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        types.getItems().addAll(AttributeType.CHAR,AttributeType.DATE,AttributeType.IMG,AttributeType.INT,AttributeType.MONEY);
        types.setValue(AttributeType.CHAR);
        keyTypes.getItems().addAll(KeyType.NONE,KeyType.PRIMARY,KeyType.FOREIGN);
        keyTypes.setValue(KeyType.NONE);
        attrSettings.setVisible(false);
        pane.setOnMouseClicked(e->{
            if(add.isSelected()) {
                addTable(e);
                add.setSelected(!add.isSelected());
            }
        });
    }
    private void addTable(MouseEvent e)
    {
        drawTable(tables.addTable(e.getX(),e.getY(),"Таблица"));
    }

    /**
     * @param element for output on pane
     */
    private void drawTable(Table element) {
        element.setOnMouseDragged(mouseEvent -> {
            element.updatePosition(mouseEvent.getX(), mouseEvent.getY());
            updateConnections();
        });
        element.setObserver(new TextObserver(this::updateConnections));
        element.setOnMouseClicked(event->{
            if(delete.isSelected()) {
                pane.getChildren().remove(element);
                tables.deleteTable(element);
                updateConnections();
            }
            else if (event.isControlDown()) {
                Attribute attribute=element.addAttribute("Атрибут",KeyType.NONE,AttributeType.INT);
                attribute.setOnMouseClicked(e->{
                    if(e.isShiftDown()) {
                        attrSettings.setVisible(true);
                        atrTitle.setText(attribute.getText());
                        types.setValue(attribute.getType());
                        keyTypes.setValue(attribute.getKeyType());
                        selectedAttribute= attribute;
                    }
                });
            }
            else if(oneToMany.isSelected() || oneToOne.isSelected() || manyToMany.isSelected()) {
                if (connectionBlocks[0] == null) {
                    connectionBlocks[0] = element;
                    if(oneToMany.isSelected())
                        relationType =RelationType.ONE_TO_MANY;
                    else if(oneToOne.isSelected())
                        relationType = RelationType.ONE_TO_ONE;
                    else relationType = RelationType.MANY_TO_MANY;
                    element.draw();
                } else if (connectionBlocks[1] == null) {
                    connectionBlocks[1] = element;
                    addRelation(connectionBlocks[0], connectionBlocks[1],relationType);
                    connectionBlocks = new Table[2];
                    oneToOne.setSelected(false);
                    manyToMany.setSelected(false);
                    oneToMany.setSelected(false);
                }
            }
        });
        pane.getChildren().add(element);
        element.draw();
    }
    @FXML
    private void onSaveClick()
    {
        selectedAttribute.update(atrTitle.getText(),keyTypes.getValue(),types.getValue());
        attrSettings.setVisible(false);
    }

    /**
     * redraw relations
     */
    private void updateConnections()
    {
        ArrayList<Relation> relations = new ArrayList<>(tables.getAllRelations());
        for (Relation relation : relations) {
            pane.getChildren().remove(relation);
            if(tables.containsTable(relation.getFirst()) && tables.containsTable(relation.getLast()))
                addRelation(relation.getFirst(),relation.getLast(),relation.getType());
            tables.deleteRelation(relation);
        }
    }
    private void addRelation(Table first, Table last, RelationType relationType) {
        Relation relation = tables.addRelation(first, last,relationType);
        pane.getChildren().add(relation);
        relation.setOnMouseClicked(mouseEvent -> {
            if (delete.isSelected()) {
                pane.getChildren().remove(relation);
                tables.deleteRelation(relation);
            }
        });
    }
    @FXML
    private void deleteAll()
    {
        for (Table table: tables.getTables()) {
            pane.getChildren().remove(table);
        }
        for (Relation relation:tables.getAllRelations()) {
            pane.getChildren().remove(relation);
        }
        tables.deleteAllTables();
        tables.deleteAllRelations();
    }

    /**
     * help for users
     */
    @FXML
    private void help()
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, """
                Данная программа предназначена для разработки информационных моделей систем
                Управление:
                    Верхняя панель - основные функции работы с файлами и проектом
                    Нижняя панель - инструменты
                ЛКМх2 - ввод текста
                Shift+ЛКМ по атрибуту - настройка атрибута
                Ctrl+ЛКМ - Добавить атрибут в таблицу
                ПКМ по атрибуту - удаление атрибута
                ЛКМ(УДЕРЖ.) - перемещение объекта
                """, ButtonType.OK);
        alert.setTitle("Руководство по использованию");
        alert.setHeaderText("информационные модели систем\n");
        alert.show();
    }

    /**import model to programm
     */
    @FXML
    private void importModel() throws IOException, ClassNotFoundException {
        Tables temp=Tables.importSchema();
        assert temp != null;
        deleteAll();
        for (Table tab:temp.getTables()) {
            drawTable(tables.clone(tab));
        }
        for (Relation relation:temp.getAllRelations()) {
            Table[] connected = new Table[2];
            for (Table element:tables.getTables()) {
                if (element.equals(relation.getFirst()))
                    connected[0] = element;
                if (element.equals(relation.getLast()))
                    connected[1] = element;
            }
            addRelation(connected[0], connected[1],relation.getType());
        }
        System.out.println("Успех!");
    }

    /**export model to file
     */
    @FXML
    private void exportModel() throws IOException {
        Tables.exportSchema(tables);
    }

    /**take a screenshot
     */
    @FXML
    private void takeAPhoto() throws IOException {
        Tables.exportImage(pane.snapshot(new SnapshotParameters(), null));
    }
}