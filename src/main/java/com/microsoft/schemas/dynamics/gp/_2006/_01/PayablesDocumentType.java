
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PayablesDocumentType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="PayablesDocumentType"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="Invoice"/&gt;
 *     &lt;enumeration value="Finance Charge"/&gt;
 *     &lt;enumeration value="Miscellaneous Charge"/&gt;
 *     &lt;enumeration value="Return"/&gt;
 *     &lt;enumeration value="Credit Memo"/&gt;
 *     &lt;enumeration value="Payment"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "PayablesDocumentType")
@XmlEnum
public enum PayablesDocumentType {

    @XmlEnumValue("Invoice")
    INVOICE("Invoice"),
    @XmlEnumValue("Finance Charge")
    FINANCE_CHARGE("Finance Charge"),
    @XmlEnumValue("Miscellaneous Charge")
    MISCELLANEOUS_CHARGE("Miscellaneous Charge"),
    @XmlEnumValue("Return")
    RETURN("Return"),
    @XmlEnumValue("Credit Memo")
    CREDIT_MEMO("Credit Memo"),
    @XmlEnumValue("Payment")
    PAYMENT("Payment");
    private final String value;

    PayablesDocumentType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static PayablesDocumentType fromValue(String v) {
        for (PayablesDocumentType c: PayablesDocumentType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
