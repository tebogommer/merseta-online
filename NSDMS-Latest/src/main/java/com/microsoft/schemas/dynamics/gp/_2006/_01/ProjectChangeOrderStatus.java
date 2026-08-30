
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ProjectChangeOrderStatus.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="ProjectChangeOrderStatus"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="Pending"/&gt;
 *     &lt;enumeration value="Unapproved"/&gt;
 *     &lt;enumeration value="Approved"/&gt;
 *     &lt;enumeration value="Canceled"/&gt;
 *     &lt;enumeration value="Completed"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "ProjectChangeOrderStatus")
@XmlEnum
public enum ProjectChangeOrderStatus {

    @XmlEnumValue("Pending")
    PENDING("Pending"),
    @XmlEnumValue("Unapproved")
    UNAPPROVED("Unapproved"),
    @XmlEnumValue("Approved")
    APPROVED("Approved"),
    @XmlEnumValue("Canceled")
    CANCELED("Canceled"),
    @XmlEnumValue("Completed")
    COMPLETED("Completed");
    private final String value;

    ProjectChangeOrderStatus(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ProjectChangeOrderStatus fromValue(String v) {
        for (ProjectChangeOrderStatus c: ProjectChangeOrderStatus.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
