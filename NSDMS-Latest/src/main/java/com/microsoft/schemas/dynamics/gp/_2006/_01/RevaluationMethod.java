
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RevaluationMethod.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="RevaluationMethod"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="Net Change"/&gt;
 *     &lt;enumeration value="Period Balances"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "RevaluationMethod")
@XmlEnum
public enum RevaluationMethod {

    @XmlEnumValue("Net Change")
    NET_CHANGE("Net Change"),
    @XmlEnumValue("Period Balances")
    PERIOD_BALANCES("Period Balances");
    private final String value;

    RevaluationMethod(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static RevaluationMethod fromValue(String v) {
        for (RevaluationMethod c: RevaluationMethod.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
