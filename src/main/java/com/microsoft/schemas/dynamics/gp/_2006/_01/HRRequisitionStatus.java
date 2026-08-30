
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for HRRequisitionStatus.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="HRRequisitionStatus"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="Open"/&gt;
 *     &lt;enumeration value="Closed"/&gt;
 *     &lt;enumeration value="On Hold"/&gt;
 *     &lt;enumeration value="Immediate"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "HRRequisitionStatus")
@XmlEnum
public enum HRRequisitionStatus {

    @XmlEnumValue("Open")
    OPEN("Open"),
    @XmlEnumValue("Closed")
    CLOSED("Closed"),
    @XmlEnumValue("On Hold")
    ON_HOLD("On Hold"),
    @XmlEnumValue("Immediate")
    IMMEDIATE("Immediate");
    private final String value;

    HRRequisitionStatus(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static HRRequisitionStatus fromValue(String v) {
        for (HRRequisitionStatus c: HRRequisitionStatus.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
