
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PlannedOrderMRPType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="PlannedOrderMRPType"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="Real MO document"/&gt;
 *     &lt;enumeration value="Suggested MO document"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "PlannedOrderMRPType")
@XmlEnum
public enum PlannedOrderMRPType {

    @XmlEnumValue("Real MO document")
    REAL_MO_DOCUMENT("Real MO document"),
    @XmlEnumValue("Suggested MO document")
    SUGGESTED_MO_DOCUMENT("Suggested MO document");
    private final String value;

    PlannedOrderMRPType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static PlannedOrderMRPType fromValue(String v) {
        for (PlannedOrderMRPType c: PlannedOrderMRPType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
