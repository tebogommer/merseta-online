
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for EmployeeScope.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="EmployeeScope"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="Return All"/&gt;
 *     &lt;enumeration value="Return Based on Employee Id"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "EmployeeScope")
@XmlEnum
public enum EmployeeScope {

    @XmlEnumValue("Return All")
    RETURN_ALL("Return All"),
    @XmlEnumValue("Return Based on Employee Id")
    RETURN_BASED_ON_EMPLOYEE_ID("Return Based on Employee Id");
    private final String value;

    EmployeeScope(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static EmployeeScope fromValue(String v) {
        for (EmployeeScope c: EmployeeScope.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
