package com.megainf3d.informmodelsystcreator.Adapters;

import com.megainf3d.informmodelsystcreator.StyleType;
import com.megainf3d.informmodelsystcreator.Tables.Attribute;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class AttributeAdapter extends XmlAdapter<XMLAttribute,Attribute> {

    @Override
    public Attribute unmarshal(XMLAttribute o) {
        return new Attribute(o.text,o.keyType,o.attributeType, StyleType.BRIGHT);
    }

    @Override
    public XMLAttribute marshal(Attribute o) {
        return new XMLAttribute(o.getText(),o.getKeyType(),o.getType());
    }

}
