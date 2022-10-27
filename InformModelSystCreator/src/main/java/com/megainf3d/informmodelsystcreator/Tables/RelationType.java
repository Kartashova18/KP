package com.megainf3d.informmodelsystcreator.Tables;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "RelationType")
@XmlEnum
public enum RelationType {

    ONE_TO_ONE,

    MANY_TO_MANY,

    ONE_TO_MANY
}
