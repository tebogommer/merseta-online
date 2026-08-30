
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ValuationMethod.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="ValuationMethod"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="FIFO Perpetual"/&gt;
 *     &lt;enumeration value="LIFO Perpetual"/&gt;
 *     &lt;enumeration value="Average Perpetual"/&gt;
 *     &lt;enumeration value="FIFO Periodic"/&gt;
 *     &lt;enumeration value="LIFO Periodic"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "ValuationMethod")
@XmlEnum
public enum ValuationMethod {

    @XmlEnumValue("FIFO Perpetual")
    FIFO_PERPETUAL("FIFO Perpetual"),
    @XmlEnumValue("LIFO Perpetual")
    LIFO_PERPETUAL("LIFO Perpetual"),
    @XmlEnumValue("Average Perpetual")
    AVERAGE_PERPETUAL("Average Perpetual"),
    @XmlEnumValue("FIFO Periodic")
    FIFO_PERIODIC("FIFO Periodic"),
    @XmlEnumValue("LIFO Periodic")
    LIFO_PERIODIC("LIFO Periodic");
    private final String value;

    ValuationMethod(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ValuationMethod fromValue(String v) {
        for (ValuationMethod c: ValuationMethod.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
