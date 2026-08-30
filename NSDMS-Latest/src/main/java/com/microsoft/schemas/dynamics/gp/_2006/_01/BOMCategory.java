
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for BOMCategory.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="BOMCategory"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="MFG BOM"/&gt;
 *     &lt;enumeration value="ENG BOM"/&gt;
 *     &lt;enumeration value="ARCH BOM"/&gt;
 *     &lt;enumeration value="CONFIG BOM"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "BOMCategory")
@XmlEnum
public enum BOMCategory {

    @XmlEnumValue("MFG BOM")
    MFG_BOM("MFG BOM"),
    @XmlEnumValue("ENG BOM")
    ENG_BOM("ENG BOM"),
    @XmlEnumValue("ARCH BOM")
    ARCH_BOM("ARCH BOM"),
    @XmlEnumValue("CONFIG BOM")
    CONFIG_BOM("CONFIG BOM");
    private final String value;

    BOMCategory(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static BOMCategory fromValue(String v) {
        for (BOMCategory c: BOMCategory.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
