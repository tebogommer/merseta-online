
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ManufacturingOrderPriority.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="ManufacturingOrderPriority"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="High"/&gt;
 *     &lt;enumeration value="Medium"/&gt;
 *     &lt;enumeration value="Low"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "ManufacturingOrderPriority")
@XmlEnum
public enum ManufacturingOrderPriority {

    @XmlEnumValue("High")
    HIGH("High"),
    @XmlEnumValue("Medium")
    MEDIUM("Medium"),
    @XmlEnumValue("Low")
    LOW("Low");
    private final String value;

    ManufacturingOrderPriority(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ManufacturingOrderPriority fromValue(String v) {
        for (ManufacturingOrderPriority c: ManufacturingOrderPriority.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
