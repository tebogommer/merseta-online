
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ShippingMethodType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="ShippingMethodType"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="Pickup"/&gt;
 *     &lt;enumeration value="Delivery"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "ShippingMethodType")
@XmlEnum
public enum ShippingMethodType {

    @XmlEnumValue("Pickup")
    PICKUP("Pickup"),
    @XmlEnumValue("Delivery")
    DELIVERY("Delivery");
    private final String value;

    ShippingMethodType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ShippingMethodType fromValue(String v) {
        for (ShippingMethodType c: ShippingMethodType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
