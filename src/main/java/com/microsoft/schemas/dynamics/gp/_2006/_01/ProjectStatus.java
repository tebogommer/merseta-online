
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ProjectStatus.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="ProjectStatus"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="Open"/&gt;
 *     &lt;enumeration value="On Hold"/&gt;
 *     &lt;enumeration value="Closed"/&gt;
 *     &lt;enumeration value="Estimate"/&gt;
 *     &lt;enumeration value="Completed"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "ProjectStatus")
@XmlEnum
public enum ProjectStatus {

    @XmlEnumValue("Open")
    OPEN("Open"),
    @XmlEnumValue("On Hold")
    ON_HOLD("On Hold"),
    @XmlEnumValue("Closed")
    CLOSED("Closed"),
    @XmlEnumValue("Estimate")
    ESTIMATE("Estimate"),
    @XmlEnumValue("Completed")
    COMPLETED("Completed");
    private final String value;

    ProjectStatus(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ProjectStatus fromValue(String v) {
        for (ProjectStatus c: ProjectStatus.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
