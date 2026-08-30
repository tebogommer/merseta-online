
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ProjectFeeType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="ProjectFeeType"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="Project Fee"/&gt;
 *     &lt;enumeration value="Retainer"/&gt;
 *     &lt;enumeration value="Retentions"/&gt;
 *     &lt;enumeration value="Service"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "ProjectFeeType")
@XmlEnum
public enum ProjectFeeType {

    @XmlEnumValue("Project Fee")
    PROJECT_FEE("Project Fee"),
    @XmlEnumValue("Retainer")
    RETAINER("Retainer"),
    @XmlEnumValue("Retentions")
    RETENTIONS("Retentions"),
    @XmlEnumValue("Service")
    SERVICE("Service");
    private final String value;

    ProjectFeeType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ProjectFeeType fromValue(String v) {
        for (ProjectFeeType c: ProjectFeeType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
