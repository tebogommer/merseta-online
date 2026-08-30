
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for AgeCode.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="AgeCode"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="Eighteen to Twenty-five"/&gt;
 *     &lt;enumeration value="Twenty-six to Thirty-five"/&gt;
 *     &lt;enumeration value="Thirty-six to Forty-five"/&gt;
 *     &lt;enumeration value="Forty-six to Fifty-five"/&gt;
 *     &lt;enumeration value="Over Fifty-five"/&gt;
 *     &lt;enumeration value="Unknown"/&gt;
 *     &lt;enumeration value="Under Seventeen"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "AgeCode")
@XmlEnum
public enum AgeCode {

    @XmlEnumValue("Eighteen to Twenty-five")
    EIGHTEEN_TO_TWENTY_FIVE("Eighteen to Twenty-five"),
    @XmlEnumValue("Twenty-six to Thirty-five")
    TWENTY_SIX_TO_THIRTY_FIVE("Twenty-six to Thirty-five"),
    @XmlEnumValue("Thirty-six to Forty-five")
    THIRTY_SIX_TO_FORTY_FIVE("Thirty-six to Forty-five"),
    @XmlEnumValue("Forty-six to Fifty-five")
    FORTY_SIX_TO_FIFTY_FIVE("Forty-six to Fifty-five"),
    @XmlEnumValue("Over Fifty-five")
    OVER_FIFTY_FIVE("Over Fifty-five"),
    @XmlEnumValue("Unknown")
    UNKNOWN("Unknown"),
    @XmlEnumValue("Under Seventeen")
    UNDER_SEVENTEEN("Under Seventeen");
    private final String value;

    AgeCode(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static AgeCode fromValue(String v) {
        for (AgeCode c: AgeCode.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
