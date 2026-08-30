
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for NegativeSymbol.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="NegativeSymbol"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="Negative Sign"/&gt;
 *     &lt;enumeration value="Parenthesis"/&gt;
 *     &lt;enumeration value="Credit"/&gt;
 *     &lt;enumeration value="Uppercase Credit"/&gt;
 *     &lt;enumeration value="Lowercase Credit"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "NegativeSymbol")
@XmlEnum
public enum NegativeSymbol {

    @XmlEnumValue("Negative Sign")
    NEGATIVE_SIGN("Negative Sign"),
    @XmlEnumValue("Parenthesis")
    PARENTHESIS("Parenthesis"),
    @XmlEnumValue("Credit")
    CREDIT("Credit"),
    @XmlEnumValue("Uppercase Credit")
    UPPERCASE_CREDIT("Uppercase Credit"),
    @XmlEnumValue("Lowercase Credit")
    LOWERCASE_CREDIT("Lowercase Credit");
    private final String value;

    NegativeSymbol(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static NegativeSymbol fromValue(String v) {
        for (NegativeSymbol c: NegativeSymbol.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
