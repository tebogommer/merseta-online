
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PriceMethod.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="PriceMethod"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="Currency Amount"/&gt;
 *     &lt;enumeration value="Percent Of List Price"/&gt;
 *     &lt;enumeration value="Percent Markup Of Current Cost"/&gt;
 *     &lt;enumeration value="Percent Markup Of Standard Cost"/&gt;
 *     &lt;enumeration value="Percent Margin Of Current Cost"/&gt;
 *     &lt;enumeration value="Percent Margin Of Standard Cost"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "PriceMethod")
@XmlEnum
public enum PriceMethod {

    @XmlEnumValue("Currency Amount")
    CURRENCY_AMOUNT("Currency Amount"),
    @XmlEnumValue("Percent Of List Price")
    PERCENT_OF_LIST_PRICE("Percent Of List Price"),
    @XmlEnumValue("Percent Markup Of Current Cost")
    PERCENT_MARKUP_OF_CURRENT_COST("Percent Markup Of Current Cost"),
    @XmlEnumValue("Percent Markup Of Standard Cost")
    PERCENT_MARKUP_OF_STANDARD_COST("Percent Markup Of Standard Cost"),
    @XmlEnumValue("Percent Margin Of Current Cost")
    PERCENT_MARGIN_OF_CURRENT_COST("Percent Margin Of Current Cost"),
    @XmlEnumValue("Percent Margin Of Standard Cost")
    PERCENT_MARGIN_OF_STANDARD_COST("Percent Margin Of Standard Cost");
    private final String value;

    PriceMethod(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static PriceMethod fromValue(String v) {
        for (PriceMethod c: PriceMethod.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
