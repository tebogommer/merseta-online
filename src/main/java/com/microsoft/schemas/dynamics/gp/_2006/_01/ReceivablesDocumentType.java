
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ReceivablesDocumentType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="ReceivablesDocumentType"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="Balance Brought Forward"/&gt;
 *     &lt;enumeration value="Invoice"/&gt;
 *     &lt;enumeration value="Scheduled Payment"/&gt;
 *     &lt;enumeration value="Debit Memo"/&gt;
 *     &lt;enumeration value="Finance Charge"/&gt;
 *     &lt;enumeration value="Service Repair"/&gt;
 *     &lt;enumeration value="Warranty"/&gt;
 *     &lt;enumeration value="Credit Memo"/&gt;
 *     &lt;enumeration value="Return"/&gt;
 *     &lt;enumeration value="Cash Receipt"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "ReceivablesDocumentType")
@XmlEnum
public enum ReceivablesDocumentType {

    @XmlEnumValue("Balance Brought Forward")
    BALANCE_BROUGHT_FORWARD("Balance Brought Forward"),
    @XmlEnumValue("Invoice")
    INVOICE("Invoice"),
    @XmlEnumValue("Scheduled Payment")
    SCHEDULED_PAYMENT("Scheduled Payment"),
    @XmlEnumValue("Debit Memo")
    DEBIT_MEMO("Debit Memo"),
    @XmlEnumValue("Finance Charge")
    FINANCE_CHARGE("Finance Charge"),
    @XmlEnumValue("Service Repair")
    SERVICE_REPAIR("Service Repair"),
    @XmlEnumValue("Warranty")
    WARRANTY("Warranty"),
    @XmlEnumValue("Credit Memo")
    CREDIT_MEMO("Credit Memo"),
    @XmlEnumValue("Return")
    RETURN("Return"),
    @XmlEnumValue("Cash Receipt")
    CASH_RECEIPT("Cash Receipt");
    private final String value;

    ReceivablesDocumentType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ReceivablesDocumentType fromValue(String v) {
        for (ReceivablesDocumentType c: ReceivablesDocumentType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
