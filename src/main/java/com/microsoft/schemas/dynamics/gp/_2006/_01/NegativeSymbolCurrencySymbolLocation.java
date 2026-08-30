
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for NegativeSymbolCurrencySymbolLocation.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="NegativeSymbolCurrencySymbolLocation"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="Before Currency Symbol"/&gt;
 *     &lt;enumeration value="After Currency Symbol"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "NegativeSymbolCurrencySymbolLocation")
@XmlEnum
public enum NegativeSymbolCurrencySymbolLocation {

    @XmlEnumValue("Before Currency Symbol")
    BEFORE_CURRENCY_SYMBOL("Before Currency Symbol"),
    @XmlEnumValue("After Currency Symbol")
    AFTER_CURRENCY_SYMBOL("After Currency Symbol");
    private final String value;

    NegativeSymbolCurrencySymbolLocation(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static NegativeSymbolCurrencySymbolLocation fromValue(String v) {
        for (NegativeSymbolCurrencySymbolLocation c: NegativeSymbolCurrencySymbolLocation.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
