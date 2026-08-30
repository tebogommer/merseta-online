
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ItemType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="ItemType"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="Sales Item"/&gt;
 *     &lt;enumeration value="Kit"/&gt;
 *     &lt;enumeration value="Miscellaneous Charges"/&gt;
 *     &lt;enumeration value="Service"/&gt;
 *     &lt;enumeration value="Flat Fee"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "ItemType")
@XmlEnum
public enum ItemType {

    @XmlEnumValue("Sales Item")
    SALES_ITEM("Sales Item"),
    @XmlEnumValue("Kit")
    KIT("Kit"),
    @XmlEnumValue("Miscellaneous Charges")
    MISCELLANEOUS_CHARGES("Miscellaneous Charges"),
    @XmlEnumValue("Service")
    SERVICE("Service"),
    @XmlEnumValue("Flat Fee")
    FLAT_FEE("Flat Fee");
    private final String value;

    ItemType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ItemType fromValue(String v) {
        for (ItemType c: ItemType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
