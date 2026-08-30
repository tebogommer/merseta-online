
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ServicePartUseType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="ServicePartUseType"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="Consumable"/&gt;
 *     &lt;enumeration value="Insert"/&gt;
 *     &lt;enumeration value="Return"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "ServicePartUseType")
@XmlEnum
public enum ServicePartUseType {

    @XmlEnumValue("Consumable")
    CONSUMABLE("Consumable"),
    @XmlEnumValue("Insert")
    INSERT("Insert"),
    @XmlEnumValue("Return")
    RETURN("Return");
    private final String value;

    ServicePartUseType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ServicePartUseType fromValue(String v) {
        for (ServicePartUseType c: ServicePartUseType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
