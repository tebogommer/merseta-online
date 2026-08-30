
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CostOfGoodsSoldAccountSource.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="CostOfGoodsSoldAccountSource"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="From Component Item"/&gt;
 *     &lt;enumeration value="From Kit Item"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "CostOfGoodsSoldAccountSource")
@XmlEnum
public enum CostOfGoodsSoldAccountSource {

    @XmlEnumValue("From Component Item")
    FROM_COMPONENT_ITEM("From Component Item"),
    @XmlEnumValue("From Kit Item")
    FROM_KIT_ITEM("From Kit Item");
    private final String value;

    CostOfGoodsSoldAccountSource(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static CostOfGoodsSoldAccountSource fromValue(String v) {
        for (CostOfGoodsSoldAccountSource c: CostOfGoodsSoldAccountSource.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
