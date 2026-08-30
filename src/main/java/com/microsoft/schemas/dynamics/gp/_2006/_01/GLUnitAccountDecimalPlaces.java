
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for GLUnitAccountDecimalPlaces.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="GLUnitAccountDecimalPlaces"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="Zero"/&gt;
 *     &lt;enumeration value="One"/&gt;
 *     &lt;enumeration value="Two"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "GLUnitAccountDecimalPlaces")
@XmlEnum
public enum GLUnitAccountDecimalPlaces {

    @XmlEnumValue("Zero")
    ZERO("Zero"),
    @XmlEnumValue("One")
    ONE("One"),
    @XmlEnumValue("Two")
    TWO("Two");
    private final String value;

    GLUnitAccountDecimalPlaces(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static GLUnitAccountDecimalPlaces fromValue(String v) {
        for (GLUnitAccountDecimalPlaces c: GLUnitAccountDecimalPlaces.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
