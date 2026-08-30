
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ManufacturingOrderOutSourced.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="ManufacturingOrderOutSourced"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="Yes"/&gt;
 *     &lt;enumeration value="No"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "ManufacturingOrderOutSourced")
@XmlEnum
public enum ManufacturingOrderOutSourced {

    @XmlEnumValue("Yes")
    YES("Yes"),
    @XmlEnumValue("No")
    NO("No");
    private final String value;

    ManufacturingOrderOutSourced(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ManufacturingOrderOutSourced fromValue(String v) {
        for (ManufacturingOrderOutSourced c: ManufacturingOrderOutSourced.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
