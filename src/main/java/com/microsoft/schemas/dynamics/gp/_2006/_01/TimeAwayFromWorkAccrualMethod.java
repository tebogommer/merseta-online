
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for TimeAwayFromWorkAccrualMethod.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="TimeAwayFromWorkAccrualMethod"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="Hours Worked"/&gt;
 *     &lt;enumeration value="Set Hours"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "TimeAwayFromWorkAccrualMethod")
@XmlEnum
public enum TimeAwayFromWorkAccrualMethod {

    @XmlEnumValue("Hours Worked")
    HOURS_WORKED("Hours Worked"),
    @XmlEnumValue("Set Hours")
    SET_HOURS("Set Hours");
    private final String value;

    TimeAwayFromWorkAccrualMethod(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static TimeAwayFromWorkAccrualMethod fromValue(String v) {
        for (TimeAwayFromWorkAccrualMethod c: TimeAwayFromWorkAccrualMethod.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
