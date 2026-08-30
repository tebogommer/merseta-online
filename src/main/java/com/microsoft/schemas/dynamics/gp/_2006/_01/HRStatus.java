
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for HRStatus.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="HRStatus"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="Active"/&gt;
 *     &lt;enumeration value="Family Leave"/&gt;
 *     &lt;enumeration value="Leave of Absence"/&gt;
 *     &lt;enumeration value="Maternity"/&gt;
 *     &lt;enumeration value="Retired"/&gt;
 *     &lt;enumeration value="Separated"/&gt;
 *     &lt;enumeration value="Suspended"/&gt;
 *     &lt;enumeration value="Terminated"/&gt;
 *     &lt;enumeration value="Other"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "HRStatus")
@XmlEnum
public enum HRStatus {

    @XmlEnumValue("Active")
    ACTIVE("Active"),
    @XmlEnumValue("Family Leave")
    FAMILY_LEAVE("Family Leave"),
    @XmlEnumValue("Leave of Absence")
    LEAVE_OF_ABSENCE("Leave of Absence"),
    @XmlEnumValue("Maternity")
    MATERNITY("Maternity"),
    @XmlEnumValue("Retired")
    RETIRED("Retired"),
    @XmlEnumValue("Separated")
    SEPARATED("Separated"),
    @XmlEnumValue("Suspended")
    SUSPENDED("Suspended"),
    @XmlEnumValue("Terminated")
    TERMINATED("Terminated"),
    @XmlEnumValue("Other")
    OTHER("Other");
    private final String value;

    HRStatus(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static HRStatus fromValue(String v) {
        for (HRStatus c: HRStatus.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
