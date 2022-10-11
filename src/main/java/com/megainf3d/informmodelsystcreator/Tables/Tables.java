package com.megainf3d.informmodelsystcreator.Tables;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.WritableImage;
import javafx.stage.FileChooser;

import javax.imageio.ImageIO;
import java.awt.image.RenderedImage;
import java.io.*;
import java.util.ArrayList;

/**
 * list of tables and relations
 */
public class Tables implements Serializable {
    private final ArrayList<Table> tables = new ArrayList<>();
    private final ArrayList<Relation> relations = new ArrayList<>();

    public Table addTable(double x, double y, String title)
    {
        Table table = new Table(x,y,title);
        tables.add(table);
        return table;
    }
    public boolean containsTable(Table table)
    {
        return tables.contains(table);
    }
    public Relation addRelation(Table first, Table last, RelationType relationType)
    {
        Relation relation = new Relation(first,last,relationType);
        relations.add(relation);
        return relation;
    }
    public void deleteAllTables()
    {
        tables.clear();
    }
    public void deleteAllRelations()
    {
        relations.clear();
    }

    public ArrayList<Table> getTables() {
        return tables;
    }

    public ArrayList<Relation> getAllRelations()
    {
        return relations;
    }
    public void deleteRelation(Relation relation)
    {
        relations.remove(relation);
    }
    public void deleteTable(Table table)
    {
        tables.remove(table);
    }

    /**
     * @param listElements for export on file
     */
    public static void exportSchema(Tables listElements) throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Выбор папки для сохранения");
        fileChooser.setInitialFileName("Модель");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Модель", "*.ims"));
        File file = fileChooser.showSaveDialog(null);
        if (file != null)
        {
            FileOutputStream fos = new FileOutputStream(file);
            ObjectOutputStream outStream = new ObjectOutputStream(fos);
            outStream.writeObject(listElements);
            outStream.flush();
            outStream.close();
            System.out.println("Успех!");
        }
    }

    /**
     * @param screenShoot exported image
     */
    public static void exportImage(WritableImage screenShoot) throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().clear();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Изображения (*.png)", "*.png"));
        fileChooser.setInitialFileName("Модель");
        File file = fileChooser.showSaveDialog(null);
        if(file != null){
            RenderedImage renderedImage = SwingFXUtils.fromFXImage(screenShoot, null);
            ImageIO.write(renderedImage, "png", file);
        }
    }

    /**
     * @return new list of tables and relations
     */
    public static Tables importSchema() throws IOException, ClassNotFoundException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Открыть файл .ims");
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Модель", "*.ims");
        fileChooser.getExtensionFilters().add(filter);
        File file = fileChooser.showOpenDialog(null);
        if (file!=null) {
            FileInputStream fis = new FileInputStream(file);
            ObjectInputStream inputStream = new ObjectInputStream(fis);
            Tables list = (Tables) inputStream.readObject();
            inputStream.close();
            return list;
        }
        return null;
    }

    public Table clone(Table tab) {
        Table table = new Table(tab.getX(),tab.getY(), tab.getTitle());
        for(Attribute attribute:tab.attributes)
            table.addAttribute(attribute.getText(),attribute.getKeyType(),attribute.getType());
        tables.add(table);
        return table;
    }
}
