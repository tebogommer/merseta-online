
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CurrencyDecimalPlaces.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="CurrencyDecimalPlaces"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="Zero"/&gt;
 *     &lt;enumeration value="One"/&gt;
 *     &lt;enumeration value="Two"/&gt;
 *     &lt;enumeration value="Three"/&gt;
 *     &lt;enumeration value="Four"/&gt;
 *     &lt;enumeration value="Five"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "CurrencyDecimalPlaces")
@XmlEnum
public enum CurrencyDecimalPlaces {

    @XmlEnumValue("Zero")
    ZERO("Zero"),
    @XmlEnumValue("One")
    ONE("One"),
    @XmlEnumValue("Two")
    TWO("Two"),
    @XmlEnumValue("Three")
    THREE("Three"),
    @XmlEnumValue("Four")
    FOUR("Four"),
    @XmlEnumValue("Five")
    FIVE("Five");
    private final String value;

    CurrencyDecimalPlaces(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static CurrencyDecimalPlaces fromValue(String v) {
        for (CurrencyDecimalPlaces c: CurrencyDecimalPlaces.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
