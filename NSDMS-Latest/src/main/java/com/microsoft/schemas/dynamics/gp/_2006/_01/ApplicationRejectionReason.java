
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ApplicationRejectionReason.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="ApplicationRejectionReason"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="Testing"/&gt;
 *     &lt;enumeration value="Interview"/&gt;
 *     &lt;enumeration value="References"/&gt;
 *     &lt;enumeration value="Experience"/&gt;
 *     &lt;enumeration value="Education"/&gt;
 *     &lt;enumeration value="Other"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "ApplicationRejectionReason")
@XmlEnum
public enum ApplicationRejectionReason {

    @XmlEnumValue("Testing")
    TESTING("Testing"),
    @XmlEnumValue("Interview")
    INTERVIEW("Interview"),
    @XmlEnumValue("References")
    REFERENCES("References"),
    @XmlEnumValue("Experience")
    EXPERIENCE("Experience"),
    @XmlEnumValue("Education")
    EDUCATION("Education"),
    @XmlEnumValue("Other")
    OTHER("Other");
    private final String value;

    ApplicationRejectionReason(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ApplicationRejectionReason fromValue(String v) {
        for (ApplicationRejectionReason c: ApplicationRejectionReason.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
