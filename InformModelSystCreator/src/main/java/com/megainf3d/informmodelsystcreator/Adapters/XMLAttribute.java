package com.megainf3d.informmodelsystcreator.Adapters;

import com.megainf3d.informmodelsystcreator.Tables.AttributeType;
import com.megainf3d.informmodelsystcreator.Tables.KeyType;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "attribute")
@XmlAccessorType(XmlAccessType.FIELD)
public class XMLAttribute {
    public String text;
    @XmlElement(required = true)
    public KeyType keyType;
    @XmlElement(required = true)
    public AttributeType attributeType;
    public XMLAttribute(){}
    public XMLAttribute(String text, KeyType keyType, AttributeType attributeType)
    {
        this.text = text;
        this.keyType = keyType;
        this.attributeType = attributeType;
    }
}
