
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CommissionBasedOn.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="CommissionBasedOn"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="Sales"/&gt;
 *     &lt;enumeration value="Document Total"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "CommissionBasedOn")
@XmlEnum
public enum CommissionBasedOn {

    @XmlEnumValue("Sales")
    SALES("Sales"),
    @XmlEnumValue("Document Total")
    DOCUMENT_TOTAL("Document Total");
    private final String value;

    CommissionBasedOn(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static CommissionBasedOn fromValue(String v) {
        for (CommissionBasedOn c: CommissionBasedOn.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
