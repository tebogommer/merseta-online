
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Tax1099Type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="Tax1099Type"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="None"/&gt;
 *     &lt;enumeration value="Dividend"/&gt;
 *     &lt;enumeration value="Interest"/&gt;
 *     &lt;enumeration value="Miscellaneous"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "Tax1099Type")
@XmlEnum
public enum Tax1099Type {

    @XmlEnumValue("None")
    NONE("None"),
    @XmlEnumValue("Dividend")
    DIVIDEND("Dividend"),
    @XmlEnumValue("Interest")
    INTEREST("Interest"),
    @XmlEnumValue("Miscellaneous")
    MISCELLANEOUS("Miscellaneous");
    private final String value;

    Tax1099Type(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static Tax1099Type fromValue(String v) {
        for (Tax1099Type c: Tax1099Type.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
