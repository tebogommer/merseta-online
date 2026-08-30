
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PurchaseOrderFormat.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="PurchaseOrderFormat"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="A"/&gt;
 *     &lt;enumeration value="B"/&gt;
 *     &lt;enumeration value="C"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "PurchaseOrderFormat")
@XmlEnum
public enum PurchaseOrderFormat {

    A,
    B,
    C;

    public String value() {
        return name();
    }

    public static PurchaseOrderFormat fromValue(String v) {
        return valueOf(v);
    }

}
