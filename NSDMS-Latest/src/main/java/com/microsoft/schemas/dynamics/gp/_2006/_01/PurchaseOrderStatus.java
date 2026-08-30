
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PurchaseOrderStatus.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="PurchaseOrderStatus"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="New"/&gt;
 *     &lt;enumeration value="Released"/&gt;
 *     &lt;enumeration value="Change Order"/&gt;
 *     &lt;enumeration value="Received"/&gt;
 *     &lt;enumeration value="Closed"/&gt;
 *     &lt;enumeration value="Canceled"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "PurchaseOrderStatus")
@XmlEnum
public enum PurchaseOrderStatus {

    @XmlEnumValue("New")
    NEW("New"),
    @XmlEnumValue("Released")
    RELEASED("Released"),
    @XmlEnumValue("Change Order")
    CHANGE_ORDER("Change Order"),
    @XmlEnumValue("Received")
    RECEIVED("Received"),
    @XmlEnumValue("Closed")
    CLOSED("Closed"),
    @XmlEnumValue("Canceled")
    CANCELED("Canceled");
    private final String value;

    PurchaseOrderStatus(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static PurchaseOrderStatus fromValue(String v) {
        for (PurchaseOrderStatus c: PurchaseOrderStatus.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
