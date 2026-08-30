
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for SalespersonScope.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="SalespersonScope"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="Return All"/&gt;
 *     &lt;enumeration value="Return Based on Salesperson Id"/&gt;
 *     &lt;enumeration value="Return Based on Territory Id"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "SalespersonScope")
@XmlEnum
public enum SalespersonScope {

    @XmlEnumValue("Return All")
    RETURN_ALL("Return All"),
    @XmlEnumValue("Return Based on Salesperson Id")
    RETURN_BASED_ON_SALESPERSON_ID("Return Based on Salesperson Id"),
    @XmlEnumValue("Return Based on Territory Id")
    RETURN_BASED_ON_TERRITORY_ID("Return Based on Territory Id");
    private final String value;

    SalespersonScope(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static SalespersonScope fromValue(String v) {
        for (SalespersonScope c: SalespersonScope.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
