
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ServiceCallType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="ServiceCallType"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="Open"/&gt;
 *     &lt;enumeration value="Invoiced"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "ServiceCallType")
@XmlEnum
public enum ServiceCallType {

    @XmlEnumValue("Open")
    OPEN("Open"),
    @XmlEnumValue("Invoiced")
    INVOICED("Invoiced");
    private final String value;

    ServiceCallType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ServiceCallType fromValue(String v) {
        for (ServiceCallType c: ServiceCallType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
