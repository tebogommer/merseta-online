
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ApplicationReferenceSource.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="ApplicationReferenceSource"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="Word of Mouth"/&gt;
 *     &lt;enumeration value="Referred to by an Employee"/&gt;
 *     &lt;enumeration value="Referred to by an Agency"/&gt;
 *     &lt;enumeration value="Newspaper"/&gt;
 *     &lt;enumeration value="Other"/&gt;
 *     &lt;enumeration value="Internet"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "ApplicationReferenceSource")
@XmlEnum
public enum ApplicationReferenceSource {

    @XmlEnumValue("Word of Mouth")
    WORD_OF_MOUTH("Word of Mouth"),
    @XmlEnumValue("Referred to by an Employee")
    REFERRED_TO_BY_AN_EMPLOYEE("Referred to by an Employee"),
    @XmlEnumValue("Referred to by an Agency")
    REFERRED_TO_BY_AN_AGENCY("Referred to by an Agency"),
    @XmlEnumValue("Newspaper")
    NEWSPAPER("Newspaper"),
    @XmlEnumValue("Other")
    OTHER("Other"),
    @XmlEnumValue("Internet")
    INTERNET("Internet");
    private final String value;

    ApplicationReferenceSource(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ApplicationReferenceSource fromValue(String v) {
        for (ApplicationReferenceSource c: ApplicationReferenceSource.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
