
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ProjectChangeOrderType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="ProjectChangeOrderType"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="Internal"/&gt;
 *     &lt;enumeration value="Company"/&gt;
 *     &lt;enumeration value="Customer"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "ProjectChangeOrderType")
@XmlEnum
public enum ProjectChangeOrderType {

    @XmlEnumValue("Internal")
    INTERNAL("Internal"),
    @XmlEnumValue("Company")
    COMPANY("Company"),
    @XmlEnumValue("Customer")
    CUSTOMER("Customer");
    private final String value;

    ProjectChangeOrderType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ProjectChangeOrderType fromValue(String v) {
        for (ProjectChangeOrderType c: ProjectChangeOrderType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
