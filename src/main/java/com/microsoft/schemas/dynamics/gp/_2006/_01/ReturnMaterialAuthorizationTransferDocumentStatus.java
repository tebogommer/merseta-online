
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ReturnMaterialAuthorizationTransferDocumentStatus.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="ReturnMaterialAuthorizationTransferDocumentStatus"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="Open"/&gt;
 *     &lt;enumeration value="Picked"/&gt;
 *     &lt;enumeration value="Packed"/&gt;
 *     &lt;enumeration value="Partial Shipped"/&gt;
 *     &lt;enumeration value="Shipped"/&gt;
 *     &lt;enumeration value="Partial Received"/&gt;
 *     &lt;enumeration value="Received"/&gt;
 *     &lt;enumeration value="Void Through RTV"/&gt;
 *     &lt;enumeration value="RMA Returned to Stock"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "ReturnMaterialAuthorizationTransferDocumentStatus")
@XmlEnum
public enum ReturnMaterialAuthorizationTransferDocumentStatus {

    @XmlEnumValue("Open")
    OPEN("Open"),
    @XmlEnumValue("Picked")
    PICKED("Picked"),
    @XmlEnumValue("Packed")
    PACKED("Packed"),
    @XmlEnumValue("Partial Shipped")
    PARTIAL_SHIPPED("Partial Shipped"),
    @XmlEnumValue("Shipped")
    SHIPPED("Shipped"),
    @XmlEnumValue("Partial Received")
    PARTIAL_RECEIVED("Partial Received"),
    @XmlEnumValue("Received")
    RECEIVED("Received"),
    @XmlEnumValue("Void Through RTV")
    VOID_THROUGH_RTV("Void Through RTV"),
    @XmlEnumValue("RMA Returned to Stock")
    RMA_RETURNED_TO_STOCK("RMA Returned to Stock");
    private final String value;

    ReturnMaterialAuthorizationTransferDocumentStatus(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ReturnMaterialAuthorizationTransferDocumentStatus fromValue(String v) {
        for (ReturnMaterialAuthorizationTransferDocumentStatus c: ReturnMaterialAuthorizationTransferDocumentStatus.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
