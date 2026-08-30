
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for TaxDetailRangeType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="TaxDetailRangeType"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="Full Amount"/&gt;
 *     &lt;enumeration value="Amount within Range"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "TaxDetailRangeType")
@XmlEnum
public enum TaxDetailRangeType {

    @XmlEnumValue("Full Amount")
    FULL_AMOUNT("Full Amount"),
    @XmlEnumValue("Amount within Range")
    AMOUNT_WITHIN_RANGE("Amount within Range");
    private final String value;

    TaxDetailRangeType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static TaxDetailRangeType fromValue(String v) {
        for (TaxDetailRangeType c: TaxDetailRangeType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
