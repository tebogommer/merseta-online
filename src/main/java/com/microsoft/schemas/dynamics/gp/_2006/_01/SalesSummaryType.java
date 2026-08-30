
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for SalesSummaryType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="SalesSummaryType"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="Calendar Year"/&gt;
 *     &lt;enumeration value="Fiscal Year"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "SalesSummaryType")
@XmlEnum
public enum SalesSummaryType {

    @XmlEnumValue("Calendar Year")
    CALENDAR_YEAR("Calendar Year"),
    @XmlEnumValue("Fiscal Year")
    FISCAL_YEAR("Fiscal Year");
    private final String value;

    SalesSummaryType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static SalesSummaryType fromValue(String v) {
        for (SalesSummaryType c: SalesSummaryType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
