
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ProjectTransactionState.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="ProjectTransactionState"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="Work"/&gt;
 *     &lt;enumeration value="Open"/&gt;
 *     &lt;enumeration value="History"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "ProjectTransactionState")
@XmlEnum
public enum ProjectTransactionState {

    @XmlEnumValue("Work")
    WORK("Work"),
    @XmlEnumValue("Open")
    OPEN("Open"),
    @XmlEnumValue("History")
    HISTORY("History");
    private final String value;

    ProjectTransactionState(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ProjectTransactionState fromValue(String v) {
        for (ProjectTransactionState c: ProjectTransactionState.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
