
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for EmployeeTipType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="EmployeeTipType"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="Directly"/&gt;
 *     &lt;enumeration value="Indirectly"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "EmployeeTipType")
@XmlEnum
public enum EmployeeTipType {

    @XmlEnumValue("Directly")
    DIRECTLY("Directly"),
    @XmlEnumValue("Indirectly")
    INDIRECTLY("Indirectly");
    private final String value;

    EmployeeTipType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static EmployeeTipType fromValue(String v) {
        for (EmployeeTipType c: EmployeeTipType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
