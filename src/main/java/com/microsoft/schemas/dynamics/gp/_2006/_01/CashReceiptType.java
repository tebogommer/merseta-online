
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CashReceiptType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="CashReceiptType"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="Check"/&gt;
 *     &lt;enumeration value="Cash"/&gt;
 *     &lt;enumeration value="Payment Card"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "CashReceiptType")
@XmlEnum
public enum CashReceiptType {

    @XmlEnumValue("Check")
    CHECK("Check"),
    @XmlEnumValue("Cash")
    CASH("Cash"),
    @XmlEnumValue("Payment Card")
    PAYMENT_CARD("Payment Card");
    private final String value;

    CashReceiptType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static CashReceiptType fromValue(String v) {
        for (CashReceiptType c: CashReceiptType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
