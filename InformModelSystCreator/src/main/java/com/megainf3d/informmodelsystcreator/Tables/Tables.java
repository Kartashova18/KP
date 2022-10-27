package com.megainf3d.informmodelsystcreator.Tables;

import com.megainf3d.informmodelsystcreator.Adapters.RelationAdapter;
import com.megainf3d.informmodelsystcreator.Adapters.TableAdapter;
import com.megainf3d.informmodelsystcreator.StyleType;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.WritableImage;
import javafx.stage.FileChooser;

import javax.imageio.ImageIO;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

@XmlRootElement(namespace = "com.megainf3d.informmodelsystcreator",name = "diagram")
@XmlAccessorType(XmlAccessType.FIELD)
public class Tables {
    @XmlElementWrapper(name = "tables")
    @XmlElement(name = "table")
    @XmlJavaTypeAdapter( TableAdapter.class )
    protected ArrayList<Table> tables = new ArrayList<>();
    @XmlElement(required = true)
    protected StyleType style=StyleType.BRIGHT;
    @XmlElementWrapper(name = "relations")
    @XmlElement(name = "relation")
    @XmlJavaTypeAdapter( RelationAdapter.class )
    protected final ArrayList<Relation> relations = new ArrayList<>();

    public Tables(){}
    public void setTables(ArrayList<Table> tables)
    {
        this.tables=tables;
    }


    public void setStyle(StyleType style)
    {
        this.style=style;
        for(Table table: tables) {
            table.editStyle(style);
            table.draw(style);
        }
    }
    public StyleType getStyle() {
        return style;
    }

    public Table addTable(double x, double y, String title)
    {
        Table table = new Table(x,y,title,style);
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
    public static void exportSchema(Tables listElements) throws JAXBException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Выбор папки для сохранения");
        fileChooser.setInitialFileName("Модель");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Модель", "*.xml"));
        File file = fileChooser.showSaveDialog(null);
        if (file != null)
        {
            JAXBContext context = JAXBContext.newInstance(Tables.class);
            Marshaller m = context.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            m.marshal(listElements, file);
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
    public static Tables importSchema() throws JAXBException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Открыть файл .xml");
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Модель", "*.xml");
        fileChooser.getExtensionFilters().add(filter);
        File file = fileChooser.showOpenDialog(null);
        if (file!=null) {
            return importSchema(file);
        }
        return null;
    }
    public static Tables importSchema(File file) throws JAXBException {
        JAXBContext context = JAXBContext
                .newInstance(Tables.class);
        Unmarshaller um = context.createUnmarshaller();
        return (Tables) um.unmarshal(file);
    }

    public Table clone(Table tab) {
        Table table = new Table(tab.getX(),tab.getY(), tab.getTitle(),style);
        for(Attribute attribute:tab.attributes)
            table.addAttribute(attribute.getText(),attribute.getKeyType(),attribute.getType(),style);
        tables.add(table);
        return table;
    }
}
