
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ExpenseType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="ExpenseType"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="Reimbursable"/&gt;
 *     &lt;enumeration value="Personal Expense"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "ExpenseType")
@XmlEnum
public enum ExpenseType {

    @XmlEnumValue("Reimbursable")
    REIMBURSABLE("Reimbursable"),
    @XmlEnumValue("Personal Expense")
    PERSONAL_EXPENSE("Personal Expense");
    private final String value;

    ExpenseType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ExpenseType fromValue(String v) {
        for (ExpenseType c: ExpenseType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
