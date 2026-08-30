
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for InventoriedItemType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="InventoriedItemType"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="Sales Item"/&gt;
 *     &lt;enumeration value="Kit"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "InventoriedItemType")
@XmlEnum
public enum InventoriedItemType {

    @XmlEnumValue("Sales Item")
    SALES_ITEM("Sales Item"),
    @XmlEnumValue("Kit")
    KIT("Kit");
    private final String value;

    InventoriedItemType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static InventoriedItemType fromValue(String v) {
        for (InventoriedItemType c: InventoriedItemType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
