
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PayablesVendorPaymentScope.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="PayablesVendorPaymentScope"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="Return All"/&gt;
 *     &lt;enumeration value="Return Based on VendorId"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "PayablesVendorPaymentScope")
@XmlEnum
public enum PayablesVendorPaymentScope {

    @XmlEnumValue("Return All")
    RETURN_ALL("Return All"),
    @XmlEnumValue("Return Based on VendorId")
    RETURN_BASED_ON_VENDOR_ID("Return Based on VendorId");
    private final String value;

    PayablesVendorPaymentScope(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static PayablesVendorPaymentScope fromValue(String v) {
        for (PayablesVendorPaymentScope c: PayablesVendorPaymentScope.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
