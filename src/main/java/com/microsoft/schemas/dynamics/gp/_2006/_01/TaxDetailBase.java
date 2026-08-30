
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for TaxDetailBase.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="TaxDetailBase"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="Tax Included with Item Price"/&gt;
 *     &lt;enumeration value="Flat Amount per Unit"/&gt;
 *     &lt;enumeration value="Percent of Sales/Purchase"/&gt;
 *     &lt;enumeration value="Percent of Cost"/&gt;
 *     &lt;enumeration value="Percent of Another Tax Detail"/&gt;
 *     &lt;enumeration value="Percent of Sales/Purchase plus Taxable Taxes"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "TaxDetailBase")
@XmlEnum
public enum TaxDetailBase {

    @XmlEnumValue("Tax Included with Item Price")
    TAX_INCLUDED_WITH_ITEM_PRICE("Tax Included with Item Price"),
    @XmlEnumValue("Flat Amount per Unit")
    FLAT_AMOUNT_PER_UNIT("Flat Amount per Unit"),
    @XmlEnumValue("Percent of Sales/Purchase")
    PERCENT_OF_SALES_PURCHASE("Percent of Sales/Purchase"),
    @XmlEnumValue("Percent of Cost")
    PERCENT_OF_COST("Percent of Cost"),
    @XmlEnumValue("Percent of Another Tax Detail")
    PERCENT_OF_ANOTHER_TAX_DETAIL("Percent of Another Tax Detail"),
    @XmlEnumValue("Percent of Sales/Purchase plus Taxable Taxes")
    PERCENT_OF_SALES_PURCHASE_PLUS_TAXABLE_TAXES("Percent of Sales/Purchase plus Taxable Taxes");
    private final String value;

    TaxDetailBase(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static TaxDetailBase fromValue(String v) {
        for (TaxDetailBase c: TaxDetailBase.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
