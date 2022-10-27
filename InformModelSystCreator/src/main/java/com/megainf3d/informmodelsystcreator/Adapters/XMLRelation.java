package com.megainf3d.informmodelsystcreator.Adapters;

import com.megainf3d.informmodelsystcreator.Tables.RelationType;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "relation")
@XmlAccessorType(XmlAccessType.FIELD)
public class XMLRelation {
    public XMLTable First;
    public XMLTable Last;
    @XmlElement(required = true)
    public RelationType Type;
    public XMLRelation()
    {

    }
    public XMLRelation(XMLTable first,XMLTable last, RelationType relationType)
    {
        First=first;
        Last=last;
        Type=relationType;
    }
}
