
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for BillingStatus.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="BillingStatus"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="Billable"/&gt;
 *     &lt;enumeration value="In Process"/&gt;
 *     &lt;enumeration value="Closed"/&gt;
 *     &lt;enumeration value="No Billing"/&gt;
 *     &lt;enumeration value="Fixed Fee Transaction"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "BillingStatus")
@XmlEnum
public enum BillingStatus {

    @XmlEnumValue("Billable")
    BILLABLE("Billable"),
    @XmlEnumValue("In Process")
    IN_PROCESS("In Process"),
    @XmlEnumValue("Closed")
    CLOSED("Closed"),
    @XmlEnumValue("No Billing")
    NO_BILLING("No Billing"),
    @XmlEnumValue("Fixed Fee Transaction")
    FIXED_FEE_TRANSACTION("Fixed Fee Transaction");
    private final String value;

    BillingStatus(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static BillingStatus fromValue(String v) {
        for (BillingStatus c: BillingStatus.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
