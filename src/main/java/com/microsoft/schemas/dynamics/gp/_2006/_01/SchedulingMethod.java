
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for SchedulingMethod.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="SchedulingMethod"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="Forward Infinite"/&gt;
 *     &lt;enumeration value="Backward Infinite"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "SchedulingMethod")
@XmlEnum
public enum SchedulingMethod {

    @XmlEnumValue("Forward Infinite")
    FORWARD_INFINITE("Forward Infinite"),
    @XmlEnumValue("Backward Infinite")
    BACKWARD_INFINITE("Backward Infinite");
    private final String value;

    SchedulingMethod(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static SchedulingMethod fromValue(String v) {
        for (SchedulingMethod c: SchedulingMethod.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
