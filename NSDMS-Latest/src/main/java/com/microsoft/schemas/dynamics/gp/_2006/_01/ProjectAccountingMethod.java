
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ProjectAccountingMethod.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="ProjectAccountingMethod"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="When Performed"/&gt;
 *     &lt;enumeration value="When Billed"/&gt;
 *     &lt;enumeration value="Cost To Cost"/&gt;
 *     &lt;enumeration value="Effort Expended"/&gt;
 *     &lt;enumeration value="Completed"/&gt;
 *     &lt;enumeration value="Labor Only"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "ProjectAccountingMethod")
@XmlEnum
public enum ProjectAccountingMethod {

    @XmlEnumValue("When Performed")
    WHEN_PERFORMED("When Performed"),
    @XmlEnumValue("When Billed")
    WHEN_BILLED("When Billed"),
    @XmlEnumValue("Cost To Cost")
    COST_TO_COST("Cost To Cost"),
    @XmlEnumValue("Effort Expended")
    EFFORT_EXPENDED("Effort Expended"),
    @XmlEnumValue("Completed")
    COMPLETED("Completed"),
    @XmlEnumValue("Labor Only")
    LABOR_ONLY("Labor Only");
    private final String value;

    ProjectAccountingMethod(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ProjectAccountingMethod fromValue(String v) {
        for (ProjectAccountingMethod c: ProjectAccountingMethod.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
