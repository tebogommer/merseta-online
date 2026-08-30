
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ProjectSourceFile.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="ProjectSourceFile"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="Contract"/&gt;
 *     &lt;enumeration value="Project"/&gt;
 *     &lt;enumeration value="Project Fee"/&gt;
 *     &lt;enumeration value="Contract Fee"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "ProjectSourceFile")
@XmlEnum
public enum ProjectSourceFile {

    @XmlEnumValue("Contract")
    CONTRACT("Contract"),
    @XmlEnumValue("Project")
    PROJECT("Project"),
    @XmlEnumValue("Project Fee")
    PROJECT_FEE("Project Fee"),
    @XmlEnumValue("Contract Fee")
    CONTRACT_FEE("Contract Fee");
    private final String value;

    ProjectSourceFile(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ProjectSourceFile fromValue(String v) {
        for (ProjectSourceFile c: ProjectSourceFile.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
