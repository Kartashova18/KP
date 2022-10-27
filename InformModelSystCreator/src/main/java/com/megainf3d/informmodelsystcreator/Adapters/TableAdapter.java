package com.megainf3d.informmodelsystcreator.Adapters;

import com.megainf3d.informmodelsystcreator.StyleType;
import com.megainf3d.informmodelsystcreator.Tables.Attribute;
import com.megainf3d.informmodelsystcreator.Tables.Table;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class TableAdapter extends XmlAdapter<XMLTable, Table> {

    @Override
    public Table unmarshal(XMLTable xmlTable) {
        Table xtable=new Table(xmlTable.x, xmlTable.y, xmlTable.title,StyleType.BRIGHT);
        for (XMLAttribute attribute: xmlTable.attributes)
            xtable.addAttribute(attribute.text,attribute.keyType,attribute.attributeType,StyleType.BRIGHT);
        return xtable;
    }

    @Override
    public XMLTable marshal(Table table) {
        XMLTable xtable=new XMLTable(table.getX(),table.getY(),table.getTitle());
        for (Attribute attribute: table.getAttributes())
            xtable.attributes.add(new XMLAttribute(attribute.getText(),attribute.getKeyType(),attribute.getType()));
        return xtable;
    }
}
