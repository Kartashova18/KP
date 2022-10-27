package com.megainf3d.informmodelsystcreator;

import com.megainf3d.informmodelsystcreator.Iterator.Diagrams;
import com.megainf3d.informmodelsystcreator.Iterator.Iterator;
import com.megainf3d.informmodelsystcreator.Tables.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
    public Button next;
    StyleType style=StyleType.BRIGHT;
    public RadioButton dark;
    public RadioButton bright;
    Table[] connectionBlocks = new Table[2];
    RelationType relationType;
    Attribute selectedAttribute;
    Diagrams diagrams;
    Iterator iterator;
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
        bright.setOnAction(e-> tables.setStyle(StyleType.BRIGHT));
        dark.setOnAction(e-> tables.setStyle(StyleType.DARK));
    }
    @FXML
    private void openDirectoryWithDiagrams() throws JAXBException {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Выбор папки с xml-диаграммами");
        directoryChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        File dir = directoryChooser.showDialog(null);
        List<String> result;
        if (dir != null) {
            try (Stream<Path> walk = Files.walk(Paths.get(dir.getAbsolutePath()))) {
                 result = walk
                        .filter(p -> !Files.isDirectory(p))   // not a directory
                        .map(Path::toString) // convert path to string
                        .filter(f -> f.endsWith("xml"))       // check end with
                        .collect(Collectors.toList());        // collect all matched to a List
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            deleteAll();
            diagrams = new Diagrams(new ArrayList<>(result));
            importModel(new File(result.get(0)));
            iterator = diagrams.getIterator();
            next.setDisable(false);
        }
    }
    @FXML
    private void next() throws JAXBException {
        importModel(new File((String) iterator.next()));
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
                Attribute attribute=element.addAttribute("Атрибут",KeyType.NONE,AttributeType.INT,tables.getStyle());
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
                    element.draw(tables.getStyle());
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
        element.draw(tables.getStyle());
    }
    @FXML
    private void onSaveClick()
    {
        selectedAttribute.update(atrTitle.getText(),keyTypes.getValue(),types.getValue(),tables.getStyle());
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
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Данная программа предназначена для разработки информационных моделей систем\n"+
                "Управление:\n"+
                    "Верхняя панель - основные функции работы с файлами и проектом\n"+
                    "Нижняя панель - инструменты\n"+
                "ЛКМх2 - ввод текста\n"+
                "Shift+ЛКМ по атрибуту - настройка атрибута\n"+
                "Ctrl+ЛКМ - Добавить атрибут в таблицу\n"+
                "ПКМ по атрибуту - удаление атрибута\n"+
                "ЛКМ(УДЕРЖ.) - перемещение объекта\n", ButtonType.OK);
        alert.setTitle("Руководство по использованию");
        alert.setHeaderText("информационные модели систем\n");
        alert.show();
    }
    private void importModel(File file) throws JAXBException {
        Tables temp=Tables.importSchema(file);
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
        tables.setStyle(style);
        System.out.println("Успех!");
    }
    /**import model to programm
     */
    @FXML
    private void importModel() throws JAXBException {
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
    private void exportModel() throws JAXBException {
        Tables.exportSchema(tables);
    }

    /**take a screenshot
     */
    @FXML
    private void takeAPhoto() throws IOException {
        Tables.exportImage(pane.snapshot(new SnapshotParameters(), null));
    }
}