
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PostingLevel.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="PostingLevel"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="Detail"/&gt;
 *     &lt;enumeration value="Summary"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "PostingLevel")
@XmlEnum
public enum PostingLevel {

    @XmlEnumValue("Detail")
    DETAIL("Detail"),
    @XmlEnumValue("Summary")
    SUMMARY("Summary");
    private final String value;

    PostingLevel(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static PostingLevel fromValue(String v) {
        for (PostingLevel c: PostingLevel.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
