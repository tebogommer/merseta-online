
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for EmployeePayTypes.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="EmployeePayTypes"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="Hourly"/&gt;
 *     &lt;enumeration value="Salary"/&gt;
 *     &lt;enumeration value="Piecework"/&gt;
 *     &lt;enumeration value="Commission"/&gt;
 *     &lt;enumeration value="Business Expense"/&gt;
 *     &lt;enumeration value="Overtime"/&gt;
 *     &lt;enumeration value="Double Time"/&gt;
 *     &lt;enumeration value="Vacation"/&gt;
 *     &lt;enumeration value="Sick"/&gt;
 *     &lt;enumeration value="Holiday"/&gt;
 *     &lt;enumeration value="Pension"/&gt;
 *     &lt;enumeration value="Other"/&gt;
 *     &lt;enumeration value="Earned Income Credit"/&gt;
 *     &lt;enumeration value="Charged Tips"/&gt;
 *     &lt;enumeration value="Reported Tips"/&gt;
 *     &lt;enumeration value="Minimum Wage Balance"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "EmployeePayTypes")
@XmlEnum
public enum EmployeePayTypes {

    @XmlEnumValue("Hourly")
    HOURLY("Hourly"),
    @XmlEnumValue("Salary")
    SALARY("Salary"),
    @XmlEnumValue("Piecework")
    PIECEWORK("Piecework"),
    @XmlEnumValue("Commission")
    COMMISSION("Commission"),
    @XmlEnumValue("Business Expense")
    BUSINESS_EXPENSE("Business Expense"),
    @XmlEnumValue("Overtime")
    OVERTIME("Overtime"),
    @XmlEnumValue("Double Time")
    DOUBLE_TIME("Double Time"),
    @XmlEnumValue("Vacation")
    VACATION("Vacation"),
    @XmlEnumValue("Sick")
    SICK("Sick"),
    @XmlEnumValue("Holiday")
    HOLIDAY("Holiday"),
    @XmlEnumValue("Pension")
    PENSION("Pension"),
    @XmlEnumValue("Other")
    OTHER("Other"),
    @XmlEnumValue("Earned Income Credit")
    EARNED_INCOME_CREDIT("Earned Income Credit"),
    @XmlEnumValue("Charged Tips")
    CHARGED_TIPS("Charged Tips"),
    @XmlEnumValue("Reported Tips")
    REPORTED_TIPS("Reported Tips"),
    @XmlEnumValue("Minimum Wage Balance")
    MINIMUM_WAGE_BALANCE("Minimum Wage Balance");
    private final String value;

    EmployeePayTypes(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static EmployeePayTypes fromValue(String v) {
        for (EmployeePayTypes c: EmployeePayTypes.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
