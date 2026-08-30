
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ReturnMaterialAuthorizationOriginatingDocumentType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="ReturnMaterialAuthorizationOriginatingDocumentType"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="None"/&gt;
 *     &lt;enumeration value="Service Call"/&gt;
 *     &lt;enumeration value="Sales Invoice"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "ReturnMaterialAuthorizationOriginatingDocumentType")
@XmlEnum
public enum ReturnMaterialAuthorizationOriginatingDocumentType {

    @XmlEnumValue("None")
    NONE("None"),
    @XmlEnumValue("Service Call")
    SERVICE_CALL("Service Call"),
    @XmlEnumValue("Sales Invoice")
    SALES_INVOICE("Sales Invoice");
    private final String value;

    ReturnMaterialAuthorizationOriginatingDocumentType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ReturnMaterialAuthorizationOriginatingDocumentType fromValue(String v) {
        for (ReturnMaterialAuthorizationOriginatingDocumentType c: ReturnMaterialAuthorizationOriginatingDocumentType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
