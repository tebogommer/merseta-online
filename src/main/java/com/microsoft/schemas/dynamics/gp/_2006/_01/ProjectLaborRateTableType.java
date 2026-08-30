
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ProjectLaborRateTableType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="ProjectLaborRateTableType"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="None"/&gt;
 *     &lt;enumeration value="Employee"/&gt;
 *     &lt;enumeration value="Position"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "ProjectLaborRateTableType")
@XmlEnum
public enum ProjectLaborRateTableType {

    @XmlEnumValue("None")
    NONE("None"),
    @XmlEnumValue("Employee")
    EMPLOYEE("Employee"),
    @XmlEnumValue("Position")
    POSITION("Position");
    private final String value;

    ProjectLaborRateTableType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ProjectLaborRateTableType fromValue(String v) {
        for (ProjectLaborRateTableType c: ProjectLaborRateTableType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
