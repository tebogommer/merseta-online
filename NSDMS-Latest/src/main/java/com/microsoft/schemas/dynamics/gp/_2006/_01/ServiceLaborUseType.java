
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ServiceLaborUseType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="ServiceLaborUseType"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="Hotline"/&gt;
 *     &lt;enumeration value="Standard"/&gt;
 *     &lt;enumeration value="Travel"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "ServiceLaborUseType")
@XmlEnum
public enum ServiceLaborUseType {

    @XmlEnumValue("Hotline")
    HOTLINE("Hotline"),
    @XmlEnumValue("Standard")
    STANDARD("Standard"),
    @XmlEnumValue("Travel")
    TRAVEL("Travel");
    private final String value;

    ServiceLaborUseType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ServiceLaborUseType fromValue(String v) {
        for (ServiceLaborUseType c: ServiceLaborUseType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
