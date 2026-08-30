
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for OrderPolicy.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="OrderPolicy"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="Not Planned"/&gt;
 *     &lt;enumeration value="Lot For Lot"/&gt;
 *     &lt;enumeration value="Fixed Order Quantity"/&gt;
 *     &lt;enumeration value="Period Order Quantity"/&gt;
 *     &lt;enumeration value="Order Point"/&gt;
 *     &lt;enumeration value="Manually Planned"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "OrderPolicy")
@XmlEnum
public enum OrderPolicy {

    @XmlEnumValue("Not Planned")
    NOT_PLANNED("Not Planned"),
    @XmlEnumValue("Lot For Lot")
    LOT_FOR_LOT("Lot For Lot"),
    @XmlEnumValue("Fixed Order Quantity")
    FIXED_ORDER_QUANTITY("Fixed Order Quantity"),
    @XmlEnumValue("Period Order Quantity")
    PERIOD_ORDER_QUANTITY("Period Order Quantity"),
    @XmlEnumValue("Order Point")
    ORDER_POINT("Order Point"),
    @XmlEnumValue("Manually Planned")
    MANUALLY_PLANNED("Manually Planned");
    private final String value;

    OrderPolicy(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static OrderPolicy fromValue(String v) {
        for (OrderPolicy c: OrderPolicy.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
