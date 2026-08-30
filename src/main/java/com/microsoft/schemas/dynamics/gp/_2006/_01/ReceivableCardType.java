
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ReceivableCardType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="ReceivableCardType"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="Bank Card"/&gt;
 *     &lt;enumeration value="Payment Card"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "ReceivableCardType")
@XmlEnum
public enum ReceivableCardType {

    @XmlEnumValue("Bank Card")
    BANK_CARD("Bank Card"),
    @XmlEnumValue("Payment Card")
    PAYMENT_CARD("Payment Card");
    private final String value;

    ReceivableCardType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ReceivableCardType fromValue(String v) {
        for (ReceivableCardType c: ReceivableCardType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
