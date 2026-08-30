
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for GLLedgerType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="GLLedgerType"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="Base"/&gt;
 *     &lt;enumeration value="IFRS"/&gt;
 *     &lt;enumeration value="Local"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "GLLedgerType")
@XmlEnum
public enum GLLedgerType {

    @XmlEnumValue("Base")
    BASE("Base"),
    IFRS("IFRS"),
    @XmlEnumValue("Local")
    LOCAL("Local");
    private final String value;

    GLLedgerType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static GLLedgerType fromValue(String v) {
        for (GLLedgerType c: GLLedgerType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
