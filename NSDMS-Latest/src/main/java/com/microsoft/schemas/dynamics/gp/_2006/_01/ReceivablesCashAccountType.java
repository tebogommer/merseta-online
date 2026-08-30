
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ReceivablesCashAccountType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="ReceivablesCashAccountType"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="Bank Account"/&gt;
 *     &lt;enumeration value="Customer"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "ReceivablesCashAccountType")
@XmlEnum
public enum ReceivablesCashAccountType {

    @XmlEnumValue("Bank Account")
    BANK_ACCOUNT("Bank Account"),
    @XmlEnumValue("Customer")
    CUSTOMER("Customer");
    private final String value;

    ReceivablesCashAccountType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ReceivablesCashAccountType fromValue(String v) {
        for (ReceivablesCashAccountType c: ReceivablesCashAccountType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
