
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for EmployeeBaseStepIncreasedOn.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="EmployeeBaseStepIncreasedOn"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="Hire Date"/&gt;
 *     &lt;enumeration value="Adjusted Hire Date"/&gt;
 *     &lt;enumeration value="Seniority Date"/&gt;
 *     &lt;enumeration value="Manual"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "EmployeeBaseStepIncreasedOn")
@XmlEnum
public enum EmployeeBaseStepIncreasedOn {

    @XmlEnumValue("Hire Date")
    HIRE_DATE("Hire Date"),
    @XmlEnumValue("Adjusted Hire Date")
    ADJUSTED_HIRE_DATE("Adjusted Hire Date"),
    @XmlEnumValue("Seniority Date")
    SENIORITY_DATE("Seniority Date"),
    @XmlEnumValue("Manual")
    MANUAL("Manual");
    private final String value;

    EmployeeBaseStepIncreasedOn(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static EmployeeBaseStepIncreasedOn fromValue(String v) {
        for (EmployeeBaseStepIncreasedOn c: EmployeeBaseStepIncreasedOn.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
