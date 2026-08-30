
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ApplicationColorCode.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="ApplicationColorCode"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="Blue"/&gt;
 *     &lt;enumeration value="Green"/&gt;
 *     &lt;enumeration value="Yellow"/&gt;
 *     &lt;enumeration value="Red"/&gt;
 *     &lt;enumeration value="N/A"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "ApplicationColorCode")
@XmlEnum
public enum ApplicationColorCode {

    @XmlEnumValue("Blue")
    BLUE("Blue"),
    @XmlEnumValue("Green")
    GREEN("Green"),
    @XmlEnumValue("Yellow")
    YELLOW("Yellow"),
    @XmlEnumValue("Red")
    RED("Red"),
    @XmlEnumValue("N/A")
    N_A("N/A");
    private final String value;

    ApplicationColorCode(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ApplicationColorCode fromValue(String v) {
        for (ApplicationColorCode c: ApplicationColorCode.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
