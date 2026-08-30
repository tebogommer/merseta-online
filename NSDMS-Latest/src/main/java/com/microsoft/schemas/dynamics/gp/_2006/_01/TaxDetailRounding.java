
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for TaxDetailRounding.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="TaxDetailRounding"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="Up to the Next Currency Decimal Digit"/&gt;
 *     &lt;enumeration value="To the Nearest Currency Decimal Digit"/&gt;
 *     &lt;enumeration value="Down to the Previous Currency Decimal Digit"/&gt;
 *     &lt;enumeration value="Up to the Next Whole Digit"/&gt;
 *     &lt;enumeration value="To the Nearest Whole Digit"/&gt;
 *     &lt;enumeration value="Down to the Previous Whole Digit"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "TaxDetailRounding")
@XmlEnum
public enum TaxDetailRounding {

    @XmlEnumValue("Up to the Next Currency Decimal Digit")
    UP_TO_THE_NEXT_CURRENCY_DECIMAL_DIGIT("Up to the Next Currency Decimal Digit"),
    @XmlEnumValue("To the Nearest Currency Decimal Digit")
    TO_THE_NEAREST_CURRENCY_DECIMAL_DIGIT("To the Nearest Currency Decimal Digit"),
    @XmlEnumValue("Down to the Previous Currency Decimal Digit")
    DOWN_TO_THE_PREVIOUS_CURRENCY_DECIMAL_DIGIT("Down to the Previous Currency Decimal Digit"),
    @XmlEnumValue("Up to the Next Whole Digit")
    UP_TO_THE_NEXT_WHOLE_DIGIT("Up to the Next Whole Digit"),
    @XmlEnumValue("To the Nearest Whole Digit")
    TO_THE_NEAREST_WHOLE_DIGIT("To the Nearest Whole Digit"),
    @XmlEnumValue("Down to the Previous Whole Digit")
    DOWN_TO_THE_PREVIOUS_WHOLE_DIGIT("Down to the Previous Whole Digit");
    private final String value;

    TaxDetailRounding(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static TaxDetailRounding fromValue(String v) {
        for (TaxDetailRounding c: TaxDetailRounding.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
