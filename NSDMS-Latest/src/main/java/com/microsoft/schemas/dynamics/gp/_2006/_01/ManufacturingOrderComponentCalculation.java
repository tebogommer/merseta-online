
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ManufacturingOrderComponentCalculation.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="ManufacturingOrderComponentCalculation"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="None"/&gt;
 *     &lt;enumeration value="Start Quantity"/&gt;
 *     &lt;enumeration value="Total Quantity Received"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "ManufacturingOrderComponentCalculation")
@XmlEnum
public enum ManufacturingOrderComponentCalculation {

    @XmlEnumValue("None")
    NONE("None"),
    @XmlEnumValue("Start Quantity")
    START_QUANTITY("Start Quantity"),
    @XmlEnumValue("Total Quantity Received")
    TOTAL_QUANTITY_RECEIVED("Total Quantity Received");
    private final String value;

    ManufacturingOrderComponentCalculation(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ManufacturingOrderComponentCalculation fromValue(String v) {
        for (ManufacturingOrderComponentCalculation c: ManufacturingOrderComponentCalculation.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
