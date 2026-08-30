
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CreditLimitSpecialAmount.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="CreditLimitSpecialAmount"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="No Credit"/&gt;
 *     &lt;enumeration value="Unlimited Credit"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "CreditLimitSpecialAmount")
@XmlEnum
public enum CreditLimitSpecialAmount {

    @XmlEnumValue("No Credit")
    NO_CREDIT("No Credit"),
    @XmlEnumValue("Unlimited Credit")
    UNLIMITED_CREDIT("Unlimited Credit");
    private final String value;

    CreditLimitSpecialAmount(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static CreditLimitSpecialAmount fromValue(String v) {
        for (CreditLimitSpecialAmount c: CreditLimitSpecialAmount.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
