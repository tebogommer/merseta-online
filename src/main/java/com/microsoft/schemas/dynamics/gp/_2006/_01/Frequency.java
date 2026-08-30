
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Frequency.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="Frequency"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="None"/&gt;
 *     &lt;enumeration value="Weekly"/&gt;
 *     &lt;enumeration value="Biweekly"/&gt;
 *     &lt;enumeration value="Semimonthly"/&gt;
 *     &lt;enumeration value="Monthly"/&gt;
 *     &lt;enumeration value="Bimonthly"/&gt;
 *     &lt;enumeration value="Quarterly"/&gt;
 *     &lt;enumeration value="Miscellaneous"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "Frequency")
@XmlEnum
public enum Frequency {

    @XmlEnumValue("None")
    NONE("None"),
    @XmlEnumValue("Weekly")
    WEEKLY("Weekly"),
    @XmlEnumValue("Biweekly")
    BIWEEKLY("Biweekly"),
    @XmlEnumValue("Semimonthly")
    SEMIMONTHLY("Semimonthly"),
    @XmlEnumValue("Monthly")
    MONTHLY("Monthly"),
    @XmlEnumValue("Bimonthly")
    BIMONTHLY("Bimonthly"),
    @XmlEnumValue("Quarterly")
    QUARTERLY("Quarterly"),
    @XmlEnumValue("Miscellaneous")
    MISCELLANEOUS("Miscellaneous");
    private final String value;

    Frequency(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static Frequency fromValue(String v) {
        for (Frequency c: Frequency.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
