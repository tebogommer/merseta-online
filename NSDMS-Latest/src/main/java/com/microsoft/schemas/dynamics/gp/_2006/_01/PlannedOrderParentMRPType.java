
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PlannedOrderParentMRPType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="PlannedOrderParentMRPType"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="Suggested MO document"/&gt;
 *     &lt;enumeration value="Real MO document"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "PlannedOrderParentMRPType")
@XmlEnum
public enum PlannedOrderParentMRPType {

    @XmlEnumValue("Suggested MO document")
    SUGGESTED_MO_DOCUMENT("Suggested MO document"),
    @XmlEnumValue("Real MO document")
    REAL_MO_DOCUMENT("Real MO document");
    private final String value;

    PlannedOrderParentMRPType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static PlannedOrderParentMRPType fromValue(String v) {
        for (PlannedOrderParentMRPType c: PlannedOrderParentMRPType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
