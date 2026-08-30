
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for WorkflowPriority.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="WorkflowPriority"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="Low"/&gt;
 *     &lt;enumeration value="Normal"/&gt;
 *     &lt;enumeration value="High"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "WorkflowPriority")
@XmlEnum
public enum WorkflowPriority {

    @XmlEnumValue("Low")
    LOW("Low"),
    @XmlEnumValue("Normal")
    NORMAL("Normal"),
    @XmlEnumValue("High")
    HIGH("High");
    private final String value;

    WorkflowPriority(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static WorkflowPriority fromValue(String v) {
        for (WorkflowPriority c: WorkflowPriority.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
