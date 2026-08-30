
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for InventoryTransactionState.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="InventoryTransactionState"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="Work"/&gt;
 *     &lt;enumeration value="History"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "InventoryTransactionState")
@XmlEnum
public enum InventoryTransactionState {

    @XmlEnumValue("Work")
    WORK("Work"),
    @XmlEnumValue("History")
    HISTORY("History");
    private final String value;

    InventoryTransactionState(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static InventoryTransactionState fromValue(String v) {
        for (InventoryTransactionState c: InventoryTransactionState.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
