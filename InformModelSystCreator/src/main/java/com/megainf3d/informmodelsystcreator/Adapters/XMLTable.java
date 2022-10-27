package com.megainf3d.informmodelsystcreator.Adapters;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;
@XmlRootElement(name = "table")
@XmlAccessorType(XmlAccessType.FIELD)
public class XMLTable {
    public double x;
    public double y;
    @XmlElementWrapper(name = "attributes")
    @XmlElement(name = "attribute")
    public List<XMLAttribute> attributes = new ArrayList<>();
    public String title;
    public XMLTable()
    {

    }
    public XMLTable(double x, double y, String title)
    {
        this.title=title;
        this.x=x;
        this.y=y;
    }
}
