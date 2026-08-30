
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for BalanceType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="BalanceType"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="Open Item"/&gt;
 *     &lt;enumeration value="Balance Forward"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "BalanceType")
@XmlEnum
public enum BalanceType {

    @XmlEnumValue("Open Item")
    OPEN_ITEM("Open Item"),
    @XmlEnumValue("Balance Forward")
    BALANCE_FORWARD("Balance Forward");
    private final String value;

    BalanceType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static BalanceType fromValue(String v) {
        for (BalanceType c: BalanceType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
