
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for MaximumWriteoffSpecialAmount.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="MaximumWriteoffSpecialAmount"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="Not Allowed"/&gt;
 *     &lt;enumeration value="Unlimited"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "MaximumWriteoffSpecialAmount")
@XmlEnum
public enum MaximumWriteoffSpecialAmount {

    @XmlEnumValue("Not Allowed")
    NOT_ALLOWED("Not Allowed"),
    @XmlEnumValue("Unlimited")
    UNLIMITED("Unlimited");
    private final String value;

    MaximumWriteoffSpecialAmount(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static MaximumWriteoffSpecialAmount fromValue(String v) {
        for (MaximumWriteoffSpecialAmount c: MaximumWriteoffSpecialAmount.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
