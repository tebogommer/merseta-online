
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for SalesCommissionTransactionState.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="SalesCommissionTransactionState"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="Open"/&gt;
 *     &lt;enumeration value="History"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "SalesCommissionTransactionState")
@XmlEnum
public enum SalesCommissionTransactionState {

    @XmlEnumValue("Open")
    OPEN("Open"),
    @XmlEnumValue("History")
    HISTORY("History");
    private final String value;

    SalesCommissionTransactionState(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static SalesCommissionTransactionState fromValue(String v) {
        for (SalesCommissionTransactionState c: SalesCommissionTransactionState.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
