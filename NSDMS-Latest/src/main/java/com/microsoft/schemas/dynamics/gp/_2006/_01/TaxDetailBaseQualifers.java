
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for TaxDetailBaseQualifers.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="TaxDetailBaseQualifers"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="Not Applicable"/&gt;
 *     &lt;enumeration value="Unit Amount"/&gt;
 *     &lt;enumeration value="Extended Amount"/&gt;
 *     &lt;enumeration value="Invoice Total"/&gt;
 *     &lt;enumeration value="Unit Amount Plus Taxable Taxes"/&gt;
 *     &lt;enumeration value="Extended Amount Plus Taxable Taxes"/&gt;
 *     &lt;enumeration value="Invoice Total Plus Taxable Taxes"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "TaxDetailBaseQualifers")
@XmlEnum
public enum TaxDetailBaseQualifers {

    @XmlEnumValue("Not Applicable")
    NOT_APPLICABLE("Not Applicable"),
    @XmlEnumValue("Unit Amount")
    UNIT_AMOUNT("Unit Amount"),
    @XmlEnumValue("Extended Amount")
    EXTENDED_AMOUNT("Extended Amount"),
    @XmlEnumValue("Invoice Total")
    INVOICE_TOTAL("Invoice Total"),
    @XmlEnumValue("Unit Amount Plus Taxable Taxes")
    UNIT_AMOUNT_PLUS_TAXABLE_TAXES("Unit Amount Plus Taxable Taxes"),
    @XmlEnumValue("Extended Amount Plus Taxable Taxes")
    EXTENDED_AMOUNT_PLUS_TAXABLE_TAXES("Extended Amount Plus Taxable Taxes"),
    @XmlEnumValue("Invoice Total Plus Taxable Taxes")
    INVOICE_TOTAL_PLUS_TAXABLE_TAXES("Invoice Total Plus Taxable Taxes");
    private final String value;

    TaxDetailBaseQualifers(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static TaxDetailBaseQualifers fromValue(String v) {
        for (TaxDetailBaseQualifers c: TaxDetailBaseQualifers.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
