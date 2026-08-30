
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PurchaseOrderLineOrigin.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="PurchaseOrderLineOrigin"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="Manual"/&gt;
 *     &lt;enumeration value="eRequisition"/&gt;
 *     &lt;enumeration value="Sales Order Processing"/&gt;
 *     &lt;enumeration value="Manufacturing"/&gt;
 *     &lt;enumeration value="Field Service Call"/&gt;
 *     &lt;enumeration value="Service Returns"/&gt;
 *     &lt;enumeration value="Depot Order"/&gt;
 *     &lt;enumeration value="Undefined"/&gt;
 *     &lt;enumeration value="PO Generator"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "PurchaseOrderLineOrigin")
@XmlEnum
public enum PurchaseOrderLineOrigin {

    @XmlEnumValue("Manual")
    MANUAL("Manual"),
    @XmlEnumValue("eRequisition")
    E_REQUISITION("eRequisition"),
    @XmlEnumValue("Sales Order Processing")
    SALES_ORDER_PROCESSING("Sales Order Processing"),
    @XmlEnumValue("Manufacturing")
    MANUFACTURING("Manufacturing"),
    @XmlEnumValue("Field Service Call")
    FIELD_SERVICE_CALL("Field Service Call"),
    @XmlEnumValue("Service Returns")
    SERVICE_RETURNS("Service Returns"),
    @XmlEnumValue("Depot Order")
    DEPOT_ORDER("Depot Order"),
    @XmlEnumValue("Undefined")
    UNDEFINED("Undefined"),
    @XmlEnumValue("PO Generator")
    PO_GENERATOR("PO Generator");
    private final String value;

    PurchaseOrderLineOrigin(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static PurchaseOrderLineOrigin fromValue(String v) {
        for (PurchaseOrderLineOrigin c: PurchaseOrderLineOrigin.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
