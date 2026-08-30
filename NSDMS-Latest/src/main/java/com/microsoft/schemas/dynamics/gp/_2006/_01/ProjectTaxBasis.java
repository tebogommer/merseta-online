
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ProjectTaxBasis.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="ProjectTaxBasis"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="Unspecified"/&gt;
 *     &lt;enumeration value="Taxable"/&gt;
 *     &lt;enumeration value="Nontaxable"/&gt;
 *     &lt;enumeration value="Based On Vendor"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "ProjectTaxBasis")
@XmlEnum
public enum ProjectTaxBasis {

    @XmlEnumValue("Unspecified")
    UNSPECIFIED("Unspecified"),
    @XmlEnumValue("Taxable")
    TAXABLE("Taxable"),
    @XmlEnumValue("Nontaxable")
    NONTAXABLE("Nontaxable"),
    @XmlEnumValue("Based On Vendor")
    BASED_ON_VENDOR("Based On Vendor");
    private final String value;

    ProjectTaxBasis(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ProjectTaxBasis fromValue(String v) {
        for (ProjectTaxBasis c: ProjectTaxBasis.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
