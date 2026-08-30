
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ProjectFrequency.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="ProjectFrequency"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="Scheduled"/&gt;
 *     &lt;enumeration value="Per Invoice"/&gt;
 *     &lt;enumeration value="At Project Completion"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "ProjectFrequency")
@XmlEnum
public enum ProjectFrequency {

    @XmlEnumValue("Scheduled")
    SCHEDULED("Scheduled"),
    @XmlEnumValue("Per Invoice")
    PER_INVOICE("Per Invoice"),
    @XmlEnumValue("At Project Completion")
    AT_PROJECT_COMPLETION("At Project Completion");
    private final String value;

    ProjectFrequency(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ProjectFrequency fromValue(String v) {
        for (ProjectFrequency c: ProjectFrequency.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
