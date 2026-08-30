
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RelativeLocation.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="RelativeLocation"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="Before Amount"/&gt;
 *     &lt;enumeration value="After Amount"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "RelativeLocation")
@XmlEnum
public enum RelativeLocation {

    @XmlEnumValue("Before Amount")
    BEFORE_AMOUNT("Before Amount"),
    @XmlEnumValue("After Amount")
    AFTER_AMOUNT("After Amount");
    private final String value;

    RelativeLocation(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static RelativeLocation fromValue(String v) {
        for (RelativeLocation c: RelativeLocation.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
