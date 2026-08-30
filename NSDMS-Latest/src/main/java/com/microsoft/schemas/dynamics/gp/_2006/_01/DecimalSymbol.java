
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DecimalSymbol.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="DecimalSymbol"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="Space"/&gt;
 *     &lt;enumeration value="Period"/&gt;
 *     &lt;enumeration value="Comma"/&gt;
 *     &lt;enumeration value="Dollar Symbol"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "DecimalSymbol")
@XmlEnum
public enum DecimalSymbol {

    @XmlEnumValue("Space")
    SPACE("Space"),
    @XmlEnumValue("Period")
    PERIOD("Period"),
    @XmlEnumValue("Comma")
    COMMA("Comma"),
    @XmlEnumValue("Dollar Symbol")
    DOLLAR_SYMBOL("Dollar Symbol");
    private final String value;

    DecimalSymbol(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static DecimalSymbol fromValue(String v) {
        for (DecimalSymbol c: DecimalSymbol.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
