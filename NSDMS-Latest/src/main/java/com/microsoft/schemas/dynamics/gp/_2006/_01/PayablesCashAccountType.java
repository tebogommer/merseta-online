
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PayablesCashAccountType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="PayablesCashAccountType"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="Bank Account"/&gt;
 *     &lt;enumeration value="Vendor"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "PayablesCashAccountType")
@XmlEnum
public enum PayablesCashAccountType {

    @XmlEnumValue("Bank Account")
    BANK_ACCOUNT("Bank Account"),
    @XmlEnumValue("Vendor")
    VENDOR("Vendor");
    private final String value;

    PayablesCashAccountType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static PayablesCashAccountType fromValue(String v) {
        for (PayablesCashAccountType c: PayablesCashAccountType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
