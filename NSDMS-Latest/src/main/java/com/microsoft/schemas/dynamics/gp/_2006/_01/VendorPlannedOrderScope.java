
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for VendorPlannedOrderScope.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="VendorPlannedOrderScope"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="Return All"/&gt;
 *     &lt;enumeration value="Return Based on Vendor Id"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "VendorPlannedOrderScope")
@XmlEnum
public enum VendorPlannedOrderScope {

    @XmlEnumValue("Return All")
    RETURN_ALL("Return All"),
    @XmlEnumValue("Return Based on Vendor Id")
    RETURN_BASED_ON_VENDOR_ID("Return Based on Vendor Id");
    private final String value;

    VendorPlannedOrderScope(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static VendorPlannedOrderScope fromValue(String v) {
        for (VendorPlannedOrderScope c: VendorPlannedOrderScope.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
