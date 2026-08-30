
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PostingType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="PostingType"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="BalanceSheet"/&gt;
 *     &lt;enumeration value="ProfitLoss"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "PostingType")
@XmlEnum
public enum PostingType {

    @XmlEnumValue("BalanceSheet")
    BALANCE_SHEET("BalanceSheet"),
    @XmlEnumValue("ProfitLoss")
    PROFIT_LOSS("ProfitLoss");
    private final String value;

    PostingType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static PostingType fromValue(String v) {
        for (PostingType c: PostingType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
