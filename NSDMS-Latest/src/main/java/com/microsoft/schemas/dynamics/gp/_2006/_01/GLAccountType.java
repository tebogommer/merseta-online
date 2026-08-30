
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for GLAccountType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="GLAccountType"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="Posting"/&gt;
 *     &lt;enumeration value="Unit"/&gt;
 *     &lt;enumeration value="Posting Allocation"/&gt;
 *     &lt;enumeration value="Unit Allocation"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "GLAccountType")
@XmlEnum
public enum GLAccountType {

    @XmlEnumValue("Posting")
    POSTING("Posting"),
    @XmlEnumValue("Unit")
    UNIT("Unit"),
    @XmlEnumValue("Posting Allocation")
    POSTING_ALLOCATION("Posting Allocation"),
    @XmlEnumValue("Unit Allocation")
    UNIT_ALLOCATION("Unit Allocation");
    private final String value;

    GLAccountType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static GLAccountType fromValue(String v) {
        for (GLAccountType c: GLAccountType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
