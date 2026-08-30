
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for StatusGroup.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="StatusGroup"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="Active"/&gt;
 *     &lt;enumeration value="Closed"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "StatusGroup")
@XmlEnum
public enum StatusGroup {

    @XmlEnumValue("Active")
    ACTIVE("Active"),
    @XmlEnumValue("Closed")
    CLOSED("Closed");
    private final String value;

    StatusGroup(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static StatusGroup fromValue(String v) {
        for (StatusGroup c: StatusGroup.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
