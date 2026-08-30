
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for TaxDetailType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="TaxDetailType"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="Sales"/&gt;
 *     &lt;enumeration value="Purchases"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "TaxDetailType")
@XmlEnum
public enum TaxDetailType {

    @XmlEnumValue("Sales")
    SALES("Sales"),
    @XmlEnumValue("Purchases")
    PURCHASES("Purchases");
    private final String value;

    TaxDetailType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static TaxDetailType fromValue(String v) {
        for (TaxDetailType c: TaxDetailType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
