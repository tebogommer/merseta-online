
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for BusinessExceptionType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="BusinessExceptionType"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="ValidationException"/&gt;
 *     &lt;enumeration value="SystemException"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "BusinessExceptionType")
@XmlEnum
public enum BusinessExceptionType {

    @XmlEnumValue("ValidationException")
    VALIDATION_EXCEPTION("ValidationException"),
    @XmlEnumValue("SystemException")
    SYSTEM_EXCEPTION("SystemException");
    private final String value;

    BusinessExceptionType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static BusinessExceptionType fromValue(String v) {
        for (BusinessExceptionType c: BusinessExceptionType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
