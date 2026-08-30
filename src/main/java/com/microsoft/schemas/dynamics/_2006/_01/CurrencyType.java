
package com.microsoft.schemas.dynamics._2006._01;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CurrencyType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="CurrencyType"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="Transactional"/&gt;
 *     &lt;enumeration value="Local"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "CurrencyType")
@XmlEnum
public enum CurrencyType {

    @XmlEnumValue("Transactional")
    TRANSACTIONAL("Transactional"),
    @XmlEnumValue("Local")
    LOCAL("Local");
    private final String value;

    CurrencyType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static CurrencyType fromValue(String v) {
        for (CurrencyType c: CurrencyType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
