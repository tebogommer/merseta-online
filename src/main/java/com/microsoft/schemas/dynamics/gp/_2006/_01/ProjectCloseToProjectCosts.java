
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ProjectCloseToProjectCosts.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="ProjectCloseToProjectCosts"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="Allow"/&gt;
 *     &lt;enumeration value="Disallow"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "ProjectCloseToProjectCosts")
@XmlEnum
public enum ProjectCloseToProjectCosts {

    @XmlEnumValue("Allow")
    ALLOW("Allow"),
    @XmlEnumValue("Disallow")
    DISALLOW("Disallow");
    private final String value;

    ProjectCloseToProjectCosts(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ProjectCloseToProjectCosts fromValue(String v) {
        for (ProjectCloseToProjectCosts c: ProjectCloseToProjectCosts.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
