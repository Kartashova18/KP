package com.megainf3d.informmodelsystcreator;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "StyleType")
@XmlEnum
public enum StyleType {

    DARK,
    BRIGHT
}
