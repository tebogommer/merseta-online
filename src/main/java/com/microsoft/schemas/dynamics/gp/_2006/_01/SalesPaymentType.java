
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for SalesPaymentType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="SalesPaymentType"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="Cash Deposit"/&gt;
 *     &lt;enumeration value="Check Deposit"/&gt;
 *     &lt;enumeration value="Payment Card Deposit"/&gt;
 *     &lt;enumeration value="Cash Payment"/&gt;
 *     &lt;enumeration value="Check Payment"/&gt;
 *     &lt;enumeration value="Payment Card Payment"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "SalesPaymentType")
@XmlEnum
public enum SalesPaymentType {

    @XmlEnumValue("Cash Deposit")
    CASH_DEPOSIT("Cash Deposit"),
    @XmlEnumValue("Check Deposit")
    CHECK_DEPOSIT("Check Deposit"),
    @XmlEnumValue("Payment Card Deposit")
    PAYMENT_CARD_DEPOSIT("Payment Card Deposit"),
    @XmlEnumValue("Cash Payment")
    CASH_PAYMENT("Cash Payment"),
    @XmlEnumValue("Check Payment")
    CHECK_PAYMENT("Check Payment"),
    @XmlEnumValue("Payment Card Payment")
    PAYMENT_CARD_PAYMENT("Payment Card Payment");
    private final String value;

    SalesPaymentType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static SalesPaymentType fromValue(String v) {
        for (SalesPaymentType c: SalesPaymentType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
