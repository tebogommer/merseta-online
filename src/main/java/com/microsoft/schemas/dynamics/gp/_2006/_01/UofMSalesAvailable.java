
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for UofMSalesAvailable.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="UofMSalesAvailable"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="Unit Of Measure Not Available For Sales Purposes"/&gt;
 *     &lt;enumeration value="Unit Of Measure Must Be Sold In Whole Quantities"/&gt;
 *     &lt;enumeration value="Unit Of Measure Must Be Sold In Whole And Fractional Quantities"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "UofMSalesAvailable")
@XmlEnum
public enum UofMSalesAvailable {

    @XmlEnumValue("Unit Of Measure Not Available For Sales Purposes")
    UNIT_OF_MEASURE_NOT_AVAILABLE_FOR_SALES_PURPOSES("Unit Of Measure Not Available For Sales Purposes"),
    @XmlEnumValue("Unit Of Measure Must Be Sold In Whole Quantities")
    UNIT_OF_MEASURE_MUST_BE_SOLD_IN_WHOLE_QUANTITIES("Unit Of Measure Must Be Sold In Whole Quantities"),
    @XmlEnumValue("Unit Of Measure Must Be Sold In Whole And Fractional Quantities")
    UNIT_OF_MEASURE_MUST_BE_SOLD_IN_WHOLE_AND_FRACTIONAL_QUANTITIES("Unit Of Measure Must Be Sold In Whole And Fractional Quantities");
    private final String value;

    UofMSalesAvailable(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static UofMSalesAvailable fromValue(String v) {
        for (UofMSalesAvailable c: UofMSalesAvailable.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
