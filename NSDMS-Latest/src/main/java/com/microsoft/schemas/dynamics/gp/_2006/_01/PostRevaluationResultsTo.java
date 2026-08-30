
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PostRevaluationResultsTo.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="PostRevaluationResultsTo"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="Account"/&gt;
 *     &lt;enumeration value="Offset Account"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "PostRevaluationResultsTo")
@XmlEnum
public enum PostRevaluationResultsTo {

    @XmlEnumValue("Account")
    ACCOUNT("Account"),
    @XmlEnumValue("Offset Account")
    OFFSET_ACCOUNT("Offset Account");
    private final String value;

    PostRevaluationResultsTo(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static PostRevaluationResultsTo fromValue(String v) {
        for (PostRevaluationResultsTo c: PostRevaluationResultsTo.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
