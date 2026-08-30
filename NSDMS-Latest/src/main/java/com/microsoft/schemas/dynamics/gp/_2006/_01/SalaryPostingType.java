
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for SalaryPostingType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="SalaryPostingType"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="No Post"/&gt;
 *     &lt;enumeration value="Reallocate Amount"/&gt;
 *     &lt;enumeration value="Reallocate Hours"/&gt;
 *     &lt;enumeration value="Additional Amount"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "SalaryPostingType")
@XmlEnum
public enum SalaryPostingType {

    @XmlEnumValue("No Post")
    NO_POST("No Post"),
    @XmlEnumValue("Reallocate Amount")
    REALLOCATE_AMOUNT("Reallocate Amount"),
    @XmlEnumValue("Reallocate Hours")
    REALLOCATE_HOURS("Reallocate Hours"),
    @XmlEnumValue("Additional Amount")
    ADDITIONAL_AMOUNT("Additional Amount");
    private final String value;

    SalaryPostingType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static SalaryPostingType fromValue(String v) {
        for (SalaryPostingType c: SalaryPostingType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
