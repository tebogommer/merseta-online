
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ReplenishmentMethod.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="ReplenishmentMethod"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="Make"/&gt;
 *     &lt;enumeration value="Buy"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "ReplenishmentMethod")
@XmlEnum
public enum ReplenishmentMethod {

    @XmlEnumValue("Make")
    MAKE("Make"),
    @XmlEnumValue("Buy")
    BUY("Buy");
    private final String value;

    ReplenishmentMethod(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ReplenishmentMethod fromValue(String v) {
        for (ReplenishmentMethod c: ReplenishmentMethod.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
