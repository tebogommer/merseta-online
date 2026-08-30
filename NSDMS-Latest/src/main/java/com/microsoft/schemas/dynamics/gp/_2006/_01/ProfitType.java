
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ProfitType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="ProfitType"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="Not Applicable"/&gt;
 *     &lt;enumeration value="Billing Rate"/&gt;
 *     &lt;enumeration value="Markup Percentage"/&gt;
 *     &lt;enumeration value="Profit Fixed"/&gt;
 *     &lt;enumeration value="Profit Variable"/&gt;
 *     &lt;enumeration value="Total Profit"/&gt;
 *     &lt;enumeration value="Percent of Baseline"/&gt;
 *     &lt;enumeration value="Percent of Actual"/&gt;
 *     &lt;enumeration value="None"/&gt;
 *     &lt;enumeration value="Price Level"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "ProfitType")
@XmlEnum
public enum ProfitType {

    @XmlEnumValue("Not Applicable")
    NOT_APPLICABLE("Not Applicable"),
    @XmlEnumValue("Billing Rate")
    BILLING_RATE("Billing Rate"),
    @XmlEnumValue("Markup Percentage")
    MARKUP_PERCENTAGE("Markup Percentage"),
    @XmlEnumValue("Profit Fixed")
    PROFIT_FIXED("Profit Fixed"),
    @XmlEnumValue("Profit Variable")
    PROFIT_VARIABLE("Profit Variable"),
    @XmlEnumValue("Total Profit")
    TOTAL_PROFIT("Total Profit"),
    @XmlEnumValue("Percent of Baseline")
    PERCENT_OF_BASELINE("Percent of Baseline"),
    @XmlEnumValue("Percent of Actual")
    PERCENT_OF_ACTUAL("Percent of Actual"),
    @XmlEnumValue("None")
    NONE("None"),
    @XmlEnumValue("Price Level")
    PRICE_LEVEL("Price Level");
    private final String value;

    ProfitType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ProfitType fromValue(String v) {
        for (ProfitType c: ProfitType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
