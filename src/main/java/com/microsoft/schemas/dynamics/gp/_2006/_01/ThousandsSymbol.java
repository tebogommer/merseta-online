
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ThousandsSymbol.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="ThousandsSymbol"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="Space"/&gt;
 *     &lt;enumeration value="Period"/&gt;
 *     &lt;enumeration value="Comma"/&gt;
 *     &lt;enumeration value="Apostrophe"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "ThousandsSymbol")
@XmlEnum
public enum ThousandsSymbol {

    @XmlEnumValue("Space")
    SPACE("Space"),
    @XmlEnumValue("Period")
    PERIOD("Period"),
    @XmlEnumValue("Comma")
    COMMA("Comma"),
    @XmlEnumValue("Apostrophe")
    APOSTROPHE("Apostrophe");
    private final String value;

    ThousandsSymbol(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ThousandsSymbol fromValue(String v) {
        for (ThousandsSymbol c: ThousandsSymbol.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
