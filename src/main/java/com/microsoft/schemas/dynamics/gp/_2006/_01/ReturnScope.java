
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ReturnScope.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="ReturnScope"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="Return All"/&gt;
 *     &lt;enumeration value="Return Based on Customer Id"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "ReturnScope")
@XmlEnum
public enum ReturnScope {

    @XmlEnumValue("Return All")
    RETURN_ALL("Return All"),
    @XmlEnumValue("Return Based on Customer Id")
    RETURN_BASED_ON_CUSTOMER_ID("Return Based on Customer Id");
    private final String value;

    ReturnScope(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ReturnScope fromValue(String v) {
        for (ReturnScope c: ReturnScope.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
