
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RoundTo.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="RoundTo"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="None"/&gt;
 *     &lt;enumeration value="Up"/&gt;
 *     &lt;enumeration value="Down"/&gt;
 *     &lt;enumeration value="To Nearest"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "RoundTo")
@XmlEnum
public enum RoundTo {

    @XmlEnumValue("None")
    NONE("None"),
    @XmlEnumValue("Up")
    UP("Up"),
    @XmlEnumValue("Down")
    DOWN("Down"),
    @XmlEnumValue("To Nearest")
    TO_NEAREST("To Nearest");
    private final String value;

    RoundTo(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static RoundTo fromValue(String v) {
        for (RoundTo c: RoundTo.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
