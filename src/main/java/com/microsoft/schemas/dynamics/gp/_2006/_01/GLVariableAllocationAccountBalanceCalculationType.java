
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for GLVariableAllocationAccountBalanceCalculationType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="GLVariableAllocationAccountBalanceCalculationType"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="Year To Date"/&gt;
 *     &lt;enumeration value="Transaction Period"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "GLVariableAllocationAccountBalanceCalculationType")
@XmlEnum
public enum GLVariableAllocationAccountBalanceCalculationType {

    @XmlEnumValue("Year To Date")
    YEAR_TO_DATE("Year To Date"),
    @XmlEnumValue("Transaction Period")
    TRANSACTION_PERIOD("Transaction Period");
    private final String value;

    GLVariableAllocationAccountBalanceCalculationType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static GLVariableAllocationAccountBalanceCalculationType fromValue(String v) {
        for (GLVariableAllocationAccountBalanceCalculationType c: GLVariableAllocationAccountBalanceCalculationType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
