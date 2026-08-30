
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ManufacturingOrderRouteSequenceType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="ManufacturingOrderRouteSequenceType"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="Standard"/&gt;
 *     &lt;enumeration value="Alternate"/&gt;
 *     &lt;enumeration value="Rework"/&gt;
 *     &lt;enumeration value="Outside Proc."/&gt;
 *     &lt;enumeration value="Inspection"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "ManufacturingOrderRouteSequenceType")
@XmlEnum
public enum ManufacturingOrderRouteSequenceType {

    @XmlEnumValue("Standard")
    STANDARD("Standard"),
    @XmlEnumValue("Alternate")
    ALTERNATE("Alternate"),
    @XmlEnumValue("Rework")
    REWORK("Rework"),
    @XmlEnumValue("Outside Proc.")
    OUTSIDE_PROC("Outside Proc."),
    @XmlEnumValue("Inspection")
    INSPECTION("Inspection");
    private final String value;

    ManufacturingOrderRouteSequenceType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ManufacturingOrderRouteSequenceType fromValue(String v) {
        for (ManufacturingOrderRouteSequenceType c: ManufacturingOrderRouteSequenceType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
