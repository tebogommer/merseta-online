
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DueDateBasedOn.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="DueDateBasedOn"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="Net Days"/&gt;
 *     &lt;enumeration value="Date"/&gt;
 *     &lt;enumeration value="EOM"/&gt;
 *     &lt;enumeration value="None"/&gt;
 *     &lt;enumeration value="Next Month"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "DueDateBasedOn")
@XmlEnum
public enum DueDateBasedOn {

    @XmlEnumValue("Net Days")
    NET_DAYS("Net Days"),
    @XmlEnumValue("Date")
    DATE("Date"),
    EOM("EOM"),
    @XmlEnumValue("None")
    NONE("None"),
    @XmlEnumValue("Next Month")
    NEXT_MONTH("Next Month");
    private final String value;

    DueDateBasedOn(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static DueDateBasedOn fromValue(String v) {
        for (DueDateBasedOn c: DueDateBasedOn.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
