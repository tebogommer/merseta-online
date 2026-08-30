
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for SalesTaxBasis.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="SalesTaxBasis"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="Single Tax Schedule"/&gt;
 *     &lt;enumeration value="Taxable"/&gt;
 *     &lt;enumeration value="Nontaxable"/&gt;
 *     &lt;enumeration value="Based On Customer"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "SalesTaxBasis")
@XmlEnum
public enum SalesTaxBasis {

    @XmlEnumValue("Single Tax Schedule")
    SINGLE_TAX_SCHEDULE("Single Tax Schedule"),
    @XmlEnumValue("Taxable")
    TAXABLE("Taxable"),
    @XmlEnumValue("Nontaxable")
    NONTAXABLE("Nontaxable"),
    @XmlEnumValue("Based On Customer")
    BASED_ON_CUSTOMER("Based On Customer");
    private final String value;

    SalesTaxBasis(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static SalesTaxBasis fromValue(String v) {
        for (SalesTaxBasis c: SalesTaxBasis.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
