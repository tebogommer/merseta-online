
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for EmploymentType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="EmploymentType"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="Full Time Regular"/&gt;
 *     &lt;enumeration value="Full Time Temporary"/&gt;
 *     &lt;enumeration value="Part Time Regular"/&gt;
 *     &lt;enumeration value="Part Time Temporary"/&gt;
 *     &lt;enumeration value="Intern"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "EmploymentType")
@XmlEnum
public enum EmploymentType {

    @XmlEnumValue("Full Time Regular")
    FULL_TIME_REGULAR("Full Time Regular"),
    @XmlEnumValue("Full Time Temporary")
    FULL_TIME_TEMPORARY("Full Time Temporary"),
    @XmlEnumValue("Part Time Regular")
    PART_TIME_REGULAR("Part Time Regular"),
    @XmlEnumValue("Part Time Temporary")
    PART_TIME_TEMPORARY("Part Time Temporary"),
    @XmlEnumValue("Intern")
    INTERN("Intern");
    private final String value;

    EmploymentType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static EmploymentType fromValue(String v) {
        for (EmploymentType c: EmploymentType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
