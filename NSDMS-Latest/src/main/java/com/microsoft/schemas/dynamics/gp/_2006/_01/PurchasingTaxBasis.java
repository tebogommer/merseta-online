
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PurchasingTaxBasis.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="PurchasingTaxBasis"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="Taxable"/&gt;
 *     &lt;enumeration value="Nontaxable"/&gt;
 *     &lt;enumeration value="Based On Vendor"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "PurchasingTaxBasis")
@XmlEnum
public enum PurchasingTaxBasis {

    @XmlEnumValue("Taxable")
    TAXABLE("Taxable"),
    @XmlEnumValue("Nontaxable")
    NONTAXABLE("Nontaxable"),
    @XmlEnumValue("Based On Vendor")
    BASED_ON_VENDOR("Based On Vendor");
    private final String value;

    PurchasingTaxBasis(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static PurchasingTaxBasis fromValue(String v) {
        for (PurchasingTaxBasis c: PurchasingTaxBasis.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
