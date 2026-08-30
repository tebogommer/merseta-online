
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ServiceTransactionState.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="ServiceTransactionState"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="Work"/&gt;
 *     &lt;enumeration value="History"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "ServiceTransactionState")
@XmlEnum
public enum ServiceTransactionState {

    @XmlEnumValue("Work")
    WORK("Work"),
    @XmlEnumValue("History")
    HISTORY("History");
    private final String value;

    ServiceTransactionState(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ServiceTransactionState fromValue(String v) {
        for (ServiceTransactionState c: ServiceTransactionState.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
