
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PayablesPostResultsTo.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="PayablesPostResultsTo"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="Payables/Discount Account"/&gt;
 *     &lt;enumeration value="Payables Offset Account"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "PayablesPostResultsTo")
@XmlEnum
public enum PayablesPostResultsTo {

    @XmlEnumValue("Payables/Discount Account")
    PAYABLES_DISCOUNT_ACCOUNT("Payables/Discount Account"),
    @XmlEnumValue("Payables Offset Account")
    PAYABLES_OFFSET_ACCOUNT("Payables Offset Account");
    private final String value;

    PayablesPostResultsTo(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static PayablesPostResultsTo fromValue(String v) {
        for (PayablesPostResultsTo c: PayablesPostResultsTo.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
