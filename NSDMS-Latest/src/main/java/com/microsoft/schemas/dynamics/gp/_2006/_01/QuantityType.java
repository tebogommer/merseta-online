
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for QuantityType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="QuantityType"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="On Hand"/&gt;
 *     &lt;enumeration value="Returned"/&gt;
 *     &lt;enumeration value="In Use"/&gt;
 *     &lt;enumeration value="In Service"/&gt;
 *     &lt;enumeration value="Damaged"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "QuantityType")
@XmlEnum
public enum QuantityType {

    @XmlEnumValue("On Hand")
    ON_HAND("On Hand"),
    @XmlEnumValue("Returned")
    RETURNED("Returned"),
    @XmlEnumValue("In Use")
    IN_USE("In Use"),
    @XmlEnumValue("In Service")
    IN_SERVICE("In Service"),
    @XmlEnumValue("Damaged")
    DAMAGED("Damaged");
    private final String value;

    QuantityType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static QuantityType fromValue(String v) {
        for (QuantityType c: QuantityType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
