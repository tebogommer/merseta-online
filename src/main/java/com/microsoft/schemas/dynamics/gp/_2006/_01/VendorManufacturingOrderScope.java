
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for VendorManufacturingOrderScope.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="VendorManufacturingOrderScope"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="Return All"/&gt;
 *     &lt;enumeration value="Return Based on Vendor Id"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "VendorManufacturingOrderScope")
@XmlEnum
public enum VendorManufacturingOrderScope {

    @XmlEnumValue("Return All")
    RETURN_ALL("Return All"),
    @XmlEnumValue("Return Based on Vendor Id")
    RETURN_BASED_ON_VENDOR_ID("Return Based on Vendor Id");
    private final String value;

    VendorManufacturingOrderScope(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static VendorManufacturingOrderScope fromValue(String v) {
        for (VendorManufacturingOrderScope c: VendorManufacturingOrderScope.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
