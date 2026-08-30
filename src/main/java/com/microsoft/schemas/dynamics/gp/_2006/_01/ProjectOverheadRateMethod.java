
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ProjectOverheadRateMethod.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="ProjectOverheadRateMethod"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="Amount Per Unit"/&gt;
 *     &lt;enumeration value="Percentage Of Actual Cost"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "ProjectOverheadRateMethod")
@XmlEnum
public enum ProjectOverheadRateMethod {

    @XmlEnumValue("Amount Per Unit")
    AMOUNT_PER_UNIT("Amount Per Unit"),
    @XmlEnumValue("Percentage Of Actual Cost")
    PERCENTAGE_OF_ACTUAL_COST("Percentage Of Actual Cost");
    private final String value;

    ProjectOverheadRateMethod(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ProjectOverheadRateMethod fromValue(String v) {
        for (ProjectOverheadRateMethod c: ProjectOverheadRateMethod.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
