
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for MaritalStatus.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="MaritalStatus"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="Married"/&gt;
 *     &lt;enumeration value="Single"/&gt;
 *     &lt;enumeration value="Not applicable"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "MaritalStatus")
@XmlEnum
public enum MaritalStatus {

    @XmlEnumValue("Married")
    MARRIED("Married"),
    @XmlEnumValue("Single")
    SINGLE("Single"),
    @XmlEnumValue("Not applicable")
    NOT_APPLICABLE("Not applicable");
    private final String value;

    MaritalStatus(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static MaritalStatus fromValue(String v) {
        for (MaritalStatus c: MaritalStatus.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
