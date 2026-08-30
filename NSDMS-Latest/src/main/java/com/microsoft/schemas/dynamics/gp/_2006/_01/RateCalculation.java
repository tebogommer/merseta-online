
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RateCalculation.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="RateCalculation"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="Multiply"/&gt;
 *     &lt;enumeration value="Divide"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "RateCalculation")
@XmlEnum
public enum RateCalculation {

    @XmlEnumValue("Multiply")
    MULTIPLY("Multiply"),
    @XmlEnumValue("Divide")
    DIVIDE("Divide");
    private final String value;

    RateCalculation(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static RateCalculation fromValue(String v) {
        for (RateCalculation c: RateCalculation.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
