package com.megainf3d.informmodelsystcreator.Tables;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.text.Text;

import java.io.Serializable;


/**
 * Attribute class
 */
public class Attribute extends Group implements Serializable {
    private KeyType keyType;
    private String text;
    private AttributeType type;
    private double Width,Height;
    public Attribute(String text,KeyType keyType,AttributeType attributeType)
    {
        this.text = text;
        this.keyType = keyType;
        this.type = attributeType;
        Width = computePrefWidth(-1);
        Height = computePrefHeight(-1);
        draw();
    }

    /**
     * @param text on attribute
     * @param keyType of attribute
     * @param attributeType of attribute
     */
    public void update(String text,KeyType keyType,AttributeType attributeType)
    {
        this.text=text;
        this.keyType=keyType;
        this.type=attributeType;
        draw();
        ((Table)getParent()).draw();
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
    public void draw()
    {
        getChildren().clear();
        Ellipse key =new Ellipse(0,-5,5,5);
        switch (keyType) {
            case NONE -> key.setFill(Color.TRANSPARENT);
            case PRIMARY -> key.setFill(Color.YELLOW);
            case FOREIGN -> key.setFill(Color.RED);
        }
        Text textBlock=new Text(6,0,type.toString()+" "+text);
        Width = textBlock.prefWidth(-1)+6+ key.getRadiusX()*2;
        Height = key.getRadiusY()*2+6;
        getChildren().addAll(key,textBlock);
    }
}
