
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for SalesDocumentType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="SalesDocumentType"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="Quote"/&gt;
 *     &lt;enumeration value="Order"/&gt;
 *     &lt;enumeration value="Invoice"/&gt;
 *     &lt;enumeration value="Return"/&gt;
 *     &lt;enumeration value="Backorder"/&gt;
 *     &lt;enumeration value="Fulfillment Order"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "SalesDocumentType")
@XmlEnum
public enum SalesDocumentType {

    @XmlEnumValue("Quote")
    QUOTE("Quote"),
    @XmlEnumValue("Order")
    ORDER("Order"),
    @XmlEnumValue("Invoice")
    INVOICE("Invoice"),
    @XmlEnumValue("Return")
    RETURN("Return"),
    @XmlEnumValue("Backorder")
    BACKORDER("Backorder"),
    @XmlEnumValue("Fulfillment Order")
    FULFILLMENT_ORDER("Fulfillment Order");
    private final String value;

    SalesDocumentType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static SalesDocumentType fromValue(String v) {
        for (SalesDocumentType c: SalesDocumentType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
