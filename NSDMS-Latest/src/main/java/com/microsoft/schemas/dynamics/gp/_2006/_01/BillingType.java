
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for BillingType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="BillingType"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="Standard"/&gt;
 *     &lt;enumeration value="No Charge"/&gt;
 *     &lt;enumeration value="No Billing"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "BillingType")
@XmlEnum
public enum BillingType {

    @XmlEnumValue("Standard")
    STANDARD("Standard"),
    @XmlEnumValue("No Charge")
    NO_CHARGE("No Charge"),
    @XmlEnumValue("No Billing")
    NO_BILLING("No Billing");
    private final String value;

    BillingType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static BillingType fromValue(String v) {
        for (BillingType c: BillingType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
