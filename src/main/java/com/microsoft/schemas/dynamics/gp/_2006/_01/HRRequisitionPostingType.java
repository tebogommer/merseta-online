
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for HRRequisitionPostingType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="HRRequisitionPostingType"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="Internal"/&gt;
 *     &lt;enumeration value="External"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "HRRequisitionPostingType")
@XmlEnum
public enum HRRequisitionPostingType {

    @XmlEnumValue("Internal")
    INTERNAL("Internal"),
    @XmlEnumValue("External")
    EXTERNAL("External");
    private final String value;

    HRRequisitionPostingType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static HRRequisitionPostingType fromValue(String v) {
        for (HRRequisitionPostingType c: HRRequisitionPostingType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
