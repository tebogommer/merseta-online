
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ProjectChangeOrderTrackChangesToType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="ProjectChangeOrderTrackChangesToType"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="Baseline"/&gt;
 *     &lt;enumeration value="Forecast"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "ProjectChangeOrderTrackChangesToType")
@XmlEnum
public enum ProjectChangeOrderTrackChangesToType {

    @XmlEnumValue("Baseline")
    BASELINE("Baseline"),
    @XmlEnumValue("Forecast")
    FORECAST("Forecast");
    private final String value;

    ProjectChangeOrderTrackChangesToType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ProjectChangeOrderTrackChangesToType fromValue(String v) {
        for (ProjectChangeOrderTrackChangesToType c: ProjectChangeOrderTrackChangesToType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
