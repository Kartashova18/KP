package com.megainf3d.informmodelsystcreator.Tables;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "KeyType")
@XmlEnum
public enum KeyType {

    PRIMARY,

    FOREIGN,

    NONE
}
