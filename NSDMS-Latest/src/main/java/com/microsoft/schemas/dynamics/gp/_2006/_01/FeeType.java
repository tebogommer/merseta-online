
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for FeeType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="FeeType"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="Miscellaneous Charges"/&gt;
 *     &lt;enumeration value="Flat Fee"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "FeeType")
@XmlEnum
public enum FeeType {

    @XmlEnumValue("Miscellaneous Charges")
    MISCELLANEOUS_CHARGES("Miscellaneous Charges"),
    @XmlEnumValue("Flat Fee")
    FLAT_FEE("Flat Fee");
    private final String value;

    FeeType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static FeeType fromValue(String v) {
        for (FeeType c: FeeType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
