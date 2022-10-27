package com.megainf3d.informmodelsystcreator.Tables;

import com.megainf3d.informmodelsystcreator.Adapters.AttributeAdapter;
import com.megainf3d.informmodelsystcreator.Adapters.TableAdapter;
import com.megainf3d.informmodelsystcreator.StyleType;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "table")
@XmlAccessorType(XmlAccessType.FIELD)
public class Table extends Group{
    @XmlElementWrapper(name = "attributes")
    @XmlElement(name = "attribute")
    @XmlJavaTypeAdapter( AttributeAdapter.class )
    List<Attribute> attributes = new ArrayList<>();

    protected double X;

    protected double Y;

    protected String Title;
    @XmlTransient
    protected double Width;
    @XmlTransient
    protected double Height;
    @XmlTransient
    protected TextField textFieldTitle;
    @XmlTransient
    protected TextObserver observer;
    @XmlTransient
    protected Label labelTitle;
    @XmlTransient
    protected double mouseAnchorX;
    @XmlTransient
    protected double mouseAnchorY;
    public Table()
    {

    }

       public Table(double x, double y,String title,StyleType styleType)
    {
        X=x;
        Y=y;
        Title = title;
        observer=new TextObserver(()->{});
        setTranslateX(X);
        setTranslateY(Y);
        labelTitle = new Label(Title);
        textFieldTitle= new TextField(Title);
        Width = computePrefWidth(-1);
        Height = computePrefHeight(-1);
        settingText(styleType);
        setOnMousePressed(mouseEvent -> {
            mouseAnchorX = mouseEvent.getX();
            mouseAnchorY = mouseEvent.getY();
        });
        draw(styleType);
    }

    public List<Attribute> getAttributes() {
        return attributes;
    }

    public double getY() {
        return Y;
    }

    public double getX() {
        return X;
    }

    public String getTitle() {
        return Title;
    }

    private void settingText(StyleType styleType)
    {
        textFieldTitle.setVisible(false);
        labelTitle.setOnMouseClicked(e -> {
            if (e.getClickCount() == 2) {
                Title = labelTitle.getText();
                textFieldTitle.setText(Title);
                labelTitle.setVisible(false);
                textFieldTitle.setVisible(true);
                textFieldTitle.requestFocus();
            }
        });
        textFieldTitle.setOnKeyTyped(e -> Title=textFieldTitle.getText());
        textFieldTitle.focusedProperty().addListener((observable, oldValue, newValue) ->{
            if(!newValue) {
                labelTitle.setVisible(true);
                textFieldTitle.setVisible(false);
                labelTitle.setText(Title);
                draw(styleType);
            }
        });
        textFieldTitle.setOnKeyReleased(e -> {
            Title=textFieldTitle.getText();
            if(e.getCode() == KeyCode.ENTER)
            {
                labelTitle.setVisible(true);
                textFieldTitle.setVisible(false);
                labelTitle.setText(Title);
                labelTitle.toFront();
                draw(styleType);
            }
        });
        labelTitle.setStyle("-fx-focus-color: -fx-control-inner-background ; -fx-border-color: transparent; -fx-background-color:transparent;  -fx-faint-focus-color: -fx-control-inner-background ;");
        textFieldTitle.setStyle("-fx-focus-color: -fx-control-inner-background ; -fx-border-color: transparent; -fx-background-color:transparent;  -fx-faint-focus-color: -fx-control-inner-background ;");
        getChildren().add(labelTitle);
        getChildren().add(textFieldTitle);
        Width=labelTitle.prefWidth(-1);
    }
    public void editStyle(StyleType style)
    {
        if(style== StyleType.BRIGHT) {
            labelTitle.setStyle(" -fx-text-fill: black");
        }
        else {
            labelTitle.setStyle(" -fx-text-fill: white");
        }
        for (Attribute attribute:attributes)
            attribute.editStyle(style);
    }
    public void updatePosition(double x, double y)
    {
        setTranslateX(Math.max(getTranslateX() + x - mouseAnchorX, 0));
        setTranslateY(Math.max(getTranslateY() + y -mouseAnchorY, 0));
        X= getTranslateX();
        Y= getTranslateY();
    }
    @XmlTransient
    public void setObserver(TextObserver listener)
    {
        observer = listener;
    }
    public void notifyObservers() {
        observer.update();
    }

    /**
     * @param text for attribute
     * @param keyType for attribute
     * @param attributeType for attribute
     * @return new attribute
     */
    public Attribute addAttribute(String text,KeyType keyType, AttributeType attributeType,StyleType styleType)
    {
        Attribute attribute = new Attribute(text,keyType,attributeType,styleType);
        attributes.add(attribute);
        attribute.setOnContextMenuRequested(e->{
            attributes.remove(attribute);
            draw(styleType);
        });
        draw(styleType);
        return attribute;
    }

    public double getTableWidth()
    {
        double width=0;
        for (Attribute attribute: attributes) {
            if(attribute.getWidth()>width)
                width=attribute.getWidth();
        }
        return width;
    }

    /**
     * @return height of top of table
     */
    public double getTableKeysHeight()
    {
        double height=0;
        for (Attribute attribute: attributes) {
            if(attribute.getKeyType()==KeyType.PRIMARY || attribute.getKeyType()==KeyType.FOREIGN)
                height+=attribute.getHeight();
        }
        return height;
    }

    /**
     * @return height of bottom of table
     */
    public double getTableAttributesHeight()
    {
        double height=0;
        for (Attribute attribute: attributes) {
            if(attribute.getKeyType()!=KeyType.PRIMARY && attribute.getKeyType()!=KeyType.FOREIGN)
                height+=attribute.getHeight();
        }
        return height;
    }

    /**
     * output
     */
    public void draw(StyleType styleType)
    {
        labelTitle.applyCss();
        labelTitle.layout();
        textFieldTitle.layout();
        textFieldTitle.applyCss();
        getChildren().clear();
        float offset = 0;

        var textWidth = labelTitle.prefWidth(-1);
        var textHeight = labelTitle.prefHeight(-1);

        Rectangle headerBlock = new Rectangle(
                0,
                0,
                Math.max(textWidth + offset,getTableWidth()),
                textHeight + offset);
        Rectangle primaryBlock = new Rectangle(
                0,
                headerBlock.getHeight(),
                Math.max(textWidth + offset,getTableWidth()),
                Math.max(getTableKeysHeight(),0));

        Rectangle attributesBlock = new Rectangle(
                0,
                headerBlock.getHeight()+primaryBlock.getHeight(),
                Math.max(textWidth + offset,getTableWidth()),
                Math.max(getTableAttributesHeight(),0));

        if(styleType==StyleType.BRIGHT)
        {
            headerBlock.setFill(Color.WHITE);
            labelTitle.setStyle("-fx-text-fill: black");
            primaryBlock.setFill(Color.WHITE);
            primaryBlock.setStroke(Color.BLACK);
            attributesBlock.setFill(Color.WHITE);
            attributesBlock.setStroke(Color.BLACK);
        }
        else {
            headerBlock.setFill(Color.BLACK);
            labelTitle.setStyle("-fx-text-fill: white");
            primaryBlock.setFill(Color.BLACK);
            primaryBlock.setStroke(Color.BLUEVIOLET);
            attributesBlock.setFill(Color.BLACK);
            attributesBlock.setStroke(Color.BLUEVIOLET);
        }
        int index=1;
        getChildren().addAll(headerBlock,primaryBlock,attributesBlock,labelTitle,textFieldTitle);
        for (Attribute attribute: attributes) {
            if(attribute.getKeyType()==KeyType.FOREIGN || attribute.getKeyType()==KeyType.PRIMARY) {
                getChildren().add(attribute);
                attribute.draw(styleType);
                attribute.setTranslateX(6);
                attribute.setTranslateY(headerBlock.getY()+headerBlock.getHeight()+(attribute.getHeight() * index)-3);
                index++;
            }
        }
        for (Attribute attribute: attributes) {
            if(attribute.getKeyType()==KeyType.NONE) {
                getChildren().add(attribute);
                attribute.draw(styleType);
                attribute.setTranslateX(0);
                attribute.setTranslateY(headerBlock.getY()+headerBlock.getHeight()+(attribute.getHeight() * index)-3);
                index++;
            }
        }
        X = getTranslateX();
        Y = getTranslateY();
        Width = headerBlock.getWidth();
        Height = headerBlock.getHeight()+primaryBlock.getHeight()+attributesBlock.getHeight();
        labelTitle.setTranslateX(0);
        labelTitle.setTranslateY(0);
        textFieldTitle.setTranslateX(-19);
        textFieldTitle.setTranslateY(0);
        notifyObservers();
    }

    /**
     * @return array of points
     */
    public ArrayList<Point2D> getArrayOfMinMaxPoints() {
        ArrayList<Point2D> fromPoints = new ArrayList<>();
        fromPoints.add(new Point2D(X+Width, Y+Height - Height * 0.5));
        fromPoints.add(new Point2D(X+Width - Width * 0.5, Y));
        fromPoints.add(new Point2D(X+Width - Width * 0.5, Y+Height));
        fromPoints.add(new Point2D(X, Y+Height - Height * 0.5));
        return fromPoints;
    }
    public boolean equals(Table element)
    {
        return (element.X==this.X && element.Y==this.Y && element.Title.equals(this.Title));
    }
}
