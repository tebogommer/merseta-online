
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ProjectType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="ProjectType"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="Time And Materials"/&gt;
 *     &lt;enumeration value="Cost Plus"/&gt;
 *     &lt;enumeration value="Fixed Price"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "ProjectType")
@XmlEnum
public enum ProjectType {

    @XmlEnumValue("Time And Materials")
    TIME_AND_MATERIALS("Time And Materials"),
    @XmlEnumValue("Cost Plus")
    COST_PLUS("Cost Plus"),
    @XmlEnumValue("Fixed Price")
    FIXED_PRICE("Fixed Price");
    private final String value;

    ProjectType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ProjectType fromValue(String v) {
        for (ProjectType c: ProjectType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
