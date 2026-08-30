
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PurchaseOrderType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="PurchaseOrderType"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="Standard"/&gt;
 *     &lt;enumeration value="Drop Ship"/&gt;
 *     &lt;enumeration value="Blanket"/&gt;
 *     &lt;enumeration value="Drop Ship Blanket"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "PurchaseOrderType")
@XmlEnum
public enum PurchaseOrderType {

    @XmlEnumValue("Standard")
    STANDARD("Standard"),
    @XmlEnumValue("Drop Ship")
    DROP_SHIP("Drop Ship"),
    @XmlEnumValue("Blanket")
    BLANKET("Blanket"),
    @XmlEnumValue("Drop Ship Blanket")
    DROP_SHIP_BLANKET("Drop Ship Blanket");
    private final String value;

    PurchaseOrderType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static PurchaseOrderType fromValue(String v) {
        for (PurchaseOrderType c: PurchaseOrderType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
