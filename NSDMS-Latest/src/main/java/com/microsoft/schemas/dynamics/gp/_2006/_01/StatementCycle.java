
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for StatementCycle.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="StatementCycle"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="No Statement"/&gt;
 *     &lt;enumeration value="Weekly"/&gt;
 *     &lt;enumeration value="Bi-Weekly"/&gt;
 *     &lt;enumeration value="Semi-Monthly"/&gt;
 *     &lt;enumeration value="Monthly"/&gt;
 *     &lt;enumeration value="Bi-Monthly"/&gt;
 *     &lt;enumeration value="Quarterly"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "StatementCycle")
@XmlEnum
public enum StatementCycle {

    @XmlEnumValue("No Statement")
    NO_STATEMENT("No Statement"),
    @XmlEnumValue("Weekly")
    WEEKLY("Weekly"),
    @XmlEnumValue("Bi-Weekly")
    BI_WEEKLY("Bi-Weekly"),
    @XmlEnumValue("Semi-Monthly")
    SEMI_MONTHLY("Semi-Monthly"),
    @XmlEnumValue("Monthly")
    MONTHLY("Monthly"),
    @XmlEnumValue("Bi-Monthly")
    BI_MONTHLY("Bi-Monthly"),
    @XmlEnumValue("Quarterly")
    QUARTERLY("Quarterly");
    private final String value;

    StatementCycle(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static StatementCycle fromValue(String v) {
        for (StatementCycle c: StatementCycle.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
