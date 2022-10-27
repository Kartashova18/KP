package com.megainf3d.informmodelsystcreator.Tables;

import com.megainf3d.informmodelsystcreator.StyleType;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.text.Text;

import javax.xml.bind.annotation.*;


@XmlRootElement(name = "attribute")
@XmlAccessorType(XmlAccessType.FIELD)
public class Attribute extends Group{
    @XmlElement(required = true)
    protected KeyType keyType;
    protected String text;
    @XmlElement(required = true)
    protected AttributeType type;
    @XmlTransient
    protected double Width;
    @XmlTransient
    protected double Height;
    @XmlTransient
    protected Text textBlock;
    public Attribute(){}
    public Attribute(String text,KeyType keyType,AttributeType attributeType,StyleType type)
    {
        this.text = text;
        this.keyType = keyType;
        this.type = attributeType;
        textBlock=new Text(6,0,type.toString()+" "+text);
        Width = computePrefWidth(-1);
        Height = computePrefHeight(-1);
        draw(type);
    }
    /**
     * @param text on attribute
     * @param keyType of attribute
     * @param attributeType of attribute
     */
    public void update(String text,KeyType keyType,AttributeType attributeType,StyleType styleType)
    {
        this.text=text;
        this.keyType=keyType;
        this.type=attributeType;
        draw(styleType);
       ((Table)getParent()).draw(styleType);
    }
    public void editStyle(StyleType styleType)
    {
        if(styleType == StyleType.BRIGHT)
            textBlock.setStyle("-fx-background-color: white; -fx-text-fill: black");
        else textBlock.setStyle("-fx-background-color: black; -fx-text-fill: white");
    }



    public KeyType getKeyType() {
        return keyType;
    }

    public String getText() {
        return text;
    }

    public AttributeType getType() {
        return type;
    }

    public double getHeight() {
        return Height;
    }

    public double getWidth() {
        return Width;
    }

    /**
     * drawning
     */
    public void draw(StyleType styleType)
    {
       getChildren().clear();
        Ellipse key =new Ellipse(0,-5,5,5);
        switch (keyType) {
            case NONE :
                key.setFill(Color.TRANSPARENT);
                break;
            case PRIMARY:
                key.setFill(Color.YELLOW);
                break;
            case FOREIGN:
                key.setFill(Color.RED);
                break;
        }
        Text textBlock=new Text(6,0,type.toString()+" "+text);
        if(styleType== StyleType.DARK) {
            textBlock.setFill(Color.WHITE);
        }
        else{
            textBlock.setFill(Color.BLACK);
        }
        Width = textBlock.prefWidth(-1)+6+ key.getRadiusX()*2;
        Height = key.getRadiusY()*2+6;
        getChildren().addAll(key,textBlock);
    }
}
