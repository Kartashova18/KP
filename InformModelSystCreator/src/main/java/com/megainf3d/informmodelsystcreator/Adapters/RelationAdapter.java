package com.megainf3d.informmodelsystcreator.Adapters;

import com.megainf3d.informmodelsystcreator.StyleType;
import com.megainf3d.informmodelsystcreator.Tables.Attribute;
import com.megainf3d.informmodelsystcreator.Tables.Relation;
import com.megainf3d.informmodelsystcreator.Tables.Table;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class RelationAdapter extends XmlAdapter<XMLRelation, Relation> {

    @Override
    public Relation unmarshal(XMLRelation xmlRelation) {
        Table first=new Table(xmlRelation.First.x,xmlRelation.First.y, xmlRelation.First.title,StyleType.BRIGHT);
        for(XMLAttribute attribute:xmlRelation.First.attributes)
            first.addAttribute(attribute.text,attribute.keyType,attribute.attributeType,StyleType.BRIGHT);
        Table last=new Table(xmlRelation.Last.x,xmlRelation.Last.y, xmlRelation.Last.title,StyleType.BRIGHT);
        for(XMLAttribute attribute:xmlRelation.Last.attributes)
            last.addAttribute(attribute.text,attribute.keyType,attribute.attributeType,StyleType.BRIGHT);
        return new Relation(first,last,xmlRelation.Type);
    }

    @Override
    public XMLRelation marshal(Relation relation) {
        XMLTable first=new XMLTable(relation.getFirst().getX(),relation.getFirst().getY(),relation.getFirst().getTitle());
        for(Attribute attribute:relation.getFirst().getAttributes())
            first.attributes.add(new XMLAttribute(attribute.getText(),attribute.getKeyType(),attribute.getType()));
        XMLTable last=new XMLTable(relation.getLast().getX(),relation.getLast().getY(),relation.getLast().getTitle());
        for(Attribute attribute:relation.getLast().getAttributes())
            first.attributes.add(new XMLAttribute(attribute.getText(),attribute.getKeyType(),attribute.getType()));
        return new XMLRelation(first,last,relation.getType());
    }
}
