
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for OrderFullfillmentShortage.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="OrderFullfillmentShortage"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="None"/&gt;
 *     &lt;enumeration value="Backorder Remaining"/&gt;
 *     &lt;enumeration value="Cancel Remaining"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "OrderFullfillmentShortage")
@XmlEnum
public enum OrderFullfillmentShortage {

    @XmlEnumValue("None")
    NONE("None"),
    @XmlEnumValue("Backorder Remaining")
    BACKORDER_REMAINING("Backorder Remaining"),
    @XmlEnumValue("Cancel Remaining")
    CANCEL_REMAINING("Cancel Remaining");
    private final String value;

    OrderFullfillmentShortage(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static OrderFullfillmentShortage fromValue(String v) {
        for (OrderFullfillmentShortage c: OrderFullfillmentShortage.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
