
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RoundHow.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="RoundHow"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="None"/&gt;
 *     &lt;enumeration value="Multiple Of"/&gt;
 *     &lt;enumeration value="Ends In"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "RoundHow")
@XmlEnum
public enum RoundHow {

    @XmlEnumValue("None")
    NONE("None"),
    @XmlEnumValue("Multiple Of")
    MULTIPLE_OF("Multiple Of"),
    @XmlEnumValue("Ends In")
    ENDS_IN("Ends In");
    private final String value;

    RoundHow(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static RoundHow fromValue(String v) {
        for (RoundHow c: RoundHow.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
