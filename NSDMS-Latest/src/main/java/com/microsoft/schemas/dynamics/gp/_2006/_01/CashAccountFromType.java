
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CashAccountFromType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="CashAccountFromType"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="Checkbook"/&gt;
 *     &lt;enumeration value="Employee"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "CashAccountFromType")
@XmlEnum
public enum CashAccountFromType {

    @XmlEnumValue("Checkbook")
    CHECKBOOK("Checkbook"),
    @XmlEnumValue("Employee")
    EMPLOYEE("Employee");
    private final String value;

    CashAccountFromType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static CashAccountFromType fromValue(String v) {
        for (CashAccountFromType c: CashAccountFromType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
