
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ManufacturingOrderStatus.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="ManufacturingOrderStatus"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="Quote/Estimate"/&gt;
 *     &lt;enumeration value="Open"/&gt;
 *     &lt;enumeration value="Released"/&gt;
 *     &lt;enumeration value="Hold"/&gt;
 *     &lt;enumeration value="Canceled"/&gt;
 *     &lt;enumeration value="Complete"/&gt;
 *     &lt;enumeration value="Partially Received"/&gt;
 *     &lt;enumeration value="Closed"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "ManufacturingOrderStatus")
@XmlEnum
public enum ManufacturingOrderStatus {

    @XmlEnumValue("Quote/Estimate")
    QUOTE_ESTIMATE("Quote/Estimate"),
    @XmlEnumValue("Open")
    OPEN("Open"),
    @XmlEnumValue("Released")
    RELEASED("Released"),
    @XmlEnumValue("Hold")
    HOLD("Hold"),
    @XmlEnumValue("Canceled")
    CANCELED("Canceled"),
    @XmlEnumValue("Complete")
    COMPLETE("Complete"),
    @XmlEnumValue("Partially Received")
    PARTIALLY_RECEIVED("Partially Received"),
    @XmlEnumValue("Closed")
    CLOSED("Closed");
    private final String value;

    ManufacturingOrderStatus(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ManufacturingOrderStatus fromValue(String v) {
        for (ManufacturingOrderStatus c: ManufacturingOrderStatus.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
