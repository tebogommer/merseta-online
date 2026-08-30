
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for FreeOnBoard.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="FreeOnBoard"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="None"/&gt;
 *     &lt;enumeration value="Origin"/&gt;
 *     &lt;enumeration value="Destination"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "FreeOnBoard")
@XmlEnum
public enum FreeOnBoard {

    @XmlEnumValue("None")
    NONE("None"),
    @XmlEnumValue("Origin")
    ORIGIN("Origin"),
    @XmlEnumValue("Destination")
    DESTINATION("Destination");
    private final String value;

    FreeOnBoard(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static FreeOnBoard fromValue(String v) {
        for (FreeOnBoard c: FreeOnBoard.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
