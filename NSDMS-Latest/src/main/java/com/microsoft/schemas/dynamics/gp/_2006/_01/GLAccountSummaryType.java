
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for GLAccountSummaryType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="GLAccountSummaryType"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="Posting"/&gt;
 *     &lt;enumeration value="Unit"/&gt;
 *     &lt;enumeration value="Fixed Allocation"/&gt;
 *     &lt;enumeration value="Variable Allocation"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "GLAccountSummaryType")
@XmlEnum
public enum GLAccountSummaryType {

    @XmlEnumValue("Posting")
    POSTING("Posting"),
    @XmlEnumValue("Unit")
    UNIT("Unit"),
    @XmlEnumValue("Fixed Allocation")
    FIXED_ALLOCATION("Fixed Allocation"),
    @XmlEnumValue("Variable Allocation")
    VARIABLE_ALLOCATION("Variable Allocation");
    private final String value;

    GLAccountSummaryType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static GLAccountSummaryType fromValue(String v) {
        for (GLAccountSummaryType c: GLAccountSummaryType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
