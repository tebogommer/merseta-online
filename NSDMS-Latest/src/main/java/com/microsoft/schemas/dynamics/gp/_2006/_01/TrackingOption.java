
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for TrackingOption.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="TrackingOption"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="None"/&gt;
 *     &lt;enumeration value="Serial Numbers"/&gt;
 *     &lt;enumeration value="Lot Numbers"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "TrackingOption")
@XmlEnum
public enum TrackingOption {

    @XmlEnumValue("None")
    NONE("None"),
    @XmlEnumValue("Serial Numbers")
    SERIAL_NUMBERS("Serial Numbers"),
    @XmlEnumValue("Lot Numbers")
    LOT_NUMBERS("Lot Numbers");
    private final String value;

    TrackingOption(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static TrackingOption fromValue(String v) {
        for (TrackingOption c: TrackingOption.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
