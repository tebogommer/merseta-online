
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ReceivablesPostResultsTo.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="ReceivablesPostResultsTo"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="Receivables/Discount Account"/&gt;
 *     &lt;enumeration value="Sales Offset Account"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "ReceivablesPostResultsTo")
@XmlEnum
public enum ReceivablesPostResultsTo {

    @XmlEnumValue("Receivables/Discount Account")
    RECEIVABLES_DISCOUNT_ACCOUNT("Receivables/Discount Account"),
    @XmlEnumValue("Sales Offset Account")
    SALES_OFFSET_ACCOUNT("Sales Offset Account");
    private final String value;

    ReceivablesPostResultsTo(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ReceivablesPostResultsTo fromValue(String v) {
        for (ReceivablesPostResultsTo c: ReceivablesPostResultsTo.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
