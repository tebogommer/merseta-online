
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ProjectTransactionType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="ProjectTransactionType"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="Standard"/&gt;
 *     &lt;enumeration value="Referenced"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "ProjectTransactionType")
@XmlEnum
public enum ProjectTransactionType {

    @XmlEnumValue("Standard")
    STANDARD("Standard"),
    @XmlEnumValue("Referenced")
    REFERENCED("Referenced");
    private final String value;

    ProjectTransactionType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ProjectTransactionType fromValue(String v) {
        for (ProjectTransactionType c: ProjectTransactionType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
