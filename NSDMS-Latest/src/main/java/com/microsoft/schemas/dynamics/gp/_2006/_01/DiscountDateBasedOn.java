
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DiscountDateBasedOn.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="DiscountDateBasedOn"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="Days"/&gt;
 *     &lt;enumeration value="Date"/&gt;
 *     &lt;enumeration value="EOM"/&gt;
 *     &lt;enumeration value="None"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "DiscountDateBasedOn")
@XmlEnum
public enum DiscountDateBasedOn {

    @XmlEnumValue("Days")
    DAYS("Days"),
    @XmlEnumValue("Date")
    DATE("Date"),
    EOM("EOM"),
    @XmlEnumValue("None")
    NONE("None");
    private final String value;

    DiscountDateBasedOn(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static DiscountDateBasedOn fromValue(String v) {
        for (DiscountDateBasedOn c: DiscountDateBasedOn.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
