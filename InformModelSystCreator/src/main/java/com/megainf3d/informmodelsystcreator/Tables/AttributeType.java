package com.megainf3d.informmodelsystcreator.Tables;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "AttributeType")
@XmlEnum
public enum AttributeType {

    CHAR,

    INT,

    MONEY,

    DATE,
    IMG
}
