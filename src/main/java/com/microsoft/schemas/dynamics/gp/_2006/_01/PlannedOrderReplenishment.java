
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PlannedOrderReplenishment.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="PlannedOrderReplenishment"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="Make the item"/&gt;
 *     &lt;enumeration value="Buy the item"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "PlannedOrderReplenishment")
@XmlEnum
public enum PlannedOrderReplenishment {

    @XmlEnumValue("Make the item")
    MAKE_THE_ITEM("Make the item"),
    @XmlEnumValue("Buy the item")
    BUY_THE_ITEM("Buy the item");
    private final String value;

    PlannedOrderReplenishment(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static PlannedOrderReplenishment fromValue(String v) {
        for (PlannedOrderReplenishment c: PlannedOrderReplenishment.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
