
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Ethnicity.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="Ethnicity"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="White"/&gt;
 *     &lt;enumeration value="American Indian or Alaskan Native"/&gt;
 *     &lt;enumeration value="Black or African American"/&gt;
 *     &lt;enumeration value="Asian"/&gt;
 *     &lt;enumeration value="Hispanic or Latino"/&gt;
 *     &lt;enumeration value="Two or more races"/&gt;
 *     &lt;enumeration value="N/A"/&gt;
 *     &lt;enumeration value="Native Hawaiian or Pacific Islander"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "Ethnicity")
@XmlEnum
public enum Ethnicity {

    @XmlEnumValue("White")
    WHITE("White"),
    @XmlEnumValue("American Indian or Alaskan Native")
    AMERICAN_INDIAN_OR_ALASKAN_NATIVE("American Indian or Alaskan Native"),
    @XmlEnumValue("Black or African American")
    BLACK_OR_AFRICAN_AMERICAN("Black or African American"),
    @XmlEnumValue("Asian")
    ASIAN("Asian"),
    @XmlEnumValue("Hispanic or Latino")
    HISPANIC_OR_LATINO("Hispanic or Latino"),
    @XmlEnumValue("Two or more races")
    TWO_OR_MORE_RACES("Two or more races"),
    @XmlEnumValue("N/A")
    N_A("N/A"),
    @XmlEnumValue("Native Hawaiian or Pacific Islander")
    NATIVE_HAWAIIAN_OR_PACIFIC_ISLANDER("Native Hawaiian or Pacific Islander");
    private final String value;

    Ethnicity(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static Ethnicity fromValue(String v) {
        for (Ethnicity c: Ethnicity.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
