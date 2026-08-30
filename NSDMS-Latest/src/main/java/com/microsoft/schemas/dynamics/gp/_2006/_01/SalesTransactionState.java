
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for SalesTransactionState.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="SalesTransactionState"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="Work"/&gt;
 *     &lt;enumeration value="History"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "SalesTransactionState")
@XmlEnum
public enum SalesTransactionState {

    @XmlEnumValue("Work")
    WORK("Work"),
    @XmlEnumValue("History")
    HISTORY("History");
    private final String value;

    SalesTransactionState(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static SalesTransactionState fromValue(String v) {
        for (SalesTransactionState c: SalesTransactionState.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
