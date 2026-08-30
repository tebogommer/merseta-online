
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for SeriesType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="SeriesType"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="Financial"/&gt;
 *     &lt;enumeration value="Sales"/&gt;
 *     &lt;enumeration value="Purchasing"/&gt;
 *     &lt;enumeration value="Inventory"/&gt;
 *     &lt;enumeration value="Payroll"/&gt;
 *     &lt;enumeration value="Project"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "SeriesType")
@XmlEnum
public enum SeriesType {

    @XmlEnumValue("Financial")
    FINANCIAL("Financial"),
    @XmlEnumValue("Sales")
    SALES("Sales"),
    @XmlEnumValue("Purchasing")
    PURCHASING("Purchasing"),
    @XmlEnumValue("Inventory")
    INVENTORY("Inventory"),
    @XmlEnumValue("Payroll")
    PAYROLL("Payroll"),
    @XmlEnumValue("Project")
    PROJECT("Project");
    private final String value;

    SeriesType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static SeriesType fromValue(String v) {
        for (SeriesType c: SeriesType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
