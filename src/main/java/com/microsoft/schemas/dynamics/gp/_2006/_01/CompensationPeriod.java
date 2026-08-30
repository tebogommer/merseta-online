
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CompensationPeriod.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="CompensationPeriod"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="Undefined"/&gt;
 *     &lt;enumeration value="Hourly"/&gt;
 *     &lt;enumeration value="Weekly"/&gt;
 *     &lt;enumeration value="Biweekly"/&gt;
 *     &lt;enumeration value="Semimonthly"/&gt;
 *     &lt;enumeration value="Monthly"/&gt;
 *     &lt;enumeration value="Quarterly"/&gt;
 *     &lt;enumeration value="Semiannually"/&gt;
 *     &lt;enumeration value="Annually"/&gt;
 *     &lt;enumeration value="Daily/Miscellaneous"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "CompensationPeriod")
@XmlEnum
public enum CompensationPeriod {

    @XmlEnumValue("Undefined")
    UNDEFINED("Undefined"),
    @XmlEnumValue("Hourly")
    HOURLY("Hourly"),
    @XmlEnumValue("Weekly")
    WEEKLY("Weekly"),
    @XmlEnumValue("Biweekly")
    BIWEEKLY("Biweekly"),
    @XmlEnumValue("Semimonthly")
    SEMIMONTHLY("Semimonthly"),
    @XmlEnumValue("Monthly")
    MONTHLY("Monthly"),
    @XmlEnumValue("Quarterly")
    QUARTERLY("Quarterly"),
    @XmlEnumValue("Semiannually")
    SEMIANNUALLY("Semiannually"),
    @XmlEnumValue("Annually")
    ANNUALLY("Annually"),
    @XmlEnumValue("Daily/Miscellaneous")
    DAILY_MISCELLANEOUS("Daily/Miscellaneous");
    private final String value;

    CompensationPeriod(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static CompensationPeriod fromValue(String v) {
        for (CompensationPeriod c: CompensationPeriod.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
