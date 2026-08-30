
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PaymentMethod.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="PaymentMethod"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="Cash"/&gt;
 *     &lt;enumeration value="Company Payment Card"/&gt;
 *     &lt;enumeration value="Personal Payment Card"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "PaymentMethod")
@XmlEnum
public enum PaymentMethod {

    @XmlEnumValue("Cash")
    CASH("Cash"),
    @XmlEnumValue("Company Payment Card")
    COMPANY_PAYMENT_CARD("Company Payment Card"),
    @XmlEnumValue("Personal Payment Card")
    PERSONAL_PAYMENT_CARD("Personal Payment Card");
    private final String value;

    PaymentMethod(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static PaymentMethod fromValue(String v) {
        for (PaymentMethod c: PaymentMethod.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
