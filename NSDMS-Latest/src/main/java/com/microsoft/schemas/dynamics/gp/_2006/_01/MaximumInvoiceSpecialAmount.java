
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for MaximumInvoiceSpecialAmount.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="MaximumInvoiceSpecialAmount"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="No Maximum"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "MaximumInvoiceSpecialAmount")
@XmlEnum
public enum MaximumInvoiceSpecialAmount {

    @XmlEnumValue("No Maximum")
    NO_MAXIMUM("No Maximum");
    private final String value;

    MaximumInvoiceSpecialAmount(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static MaximumInvoiceSpecialAmount fromValue(String v) {
        for (MaximumInvoiceSpecialAmount c: MaximumInvoiceSpecialAmount.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
