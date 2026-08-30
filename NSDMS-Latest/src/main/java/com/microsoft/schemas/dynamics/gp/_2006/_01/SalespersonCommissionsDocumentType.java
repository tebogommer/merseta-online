
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for SalespersonCommissionsDocumentType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="SalespersonCommissionsDocumentType"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="Sales/Invoice"/&gt;
 *     &lt;enumeration value="Debit Memo"/&gt;
 *     &lt;enumeration value="Service/Repair"/&gt;
 *     &lt;enumeration value="Return"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "SalespersonCommissionsDocumentType")
@XmlEnum
public enum SalespersonCommissionsDocumentType {

    @XmlEnumValue("Sales/Invoice")
    SALES_INVOICE("Sales/Invoice"),
    @XmlEnumValue("Debit Memo")
    DEBIT_MEMO("Debit Memo"),
    @XmlEnumValue("Service/Repair")
    SERVICE_REPAIR("Service/Repair"),
    @XmlEnumValue("Return")
    RETURN("Return");
    private final String value;

    SalespersonCommissionsDocumentType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static SalespersonCommissionsDocumentType fromValue(String v) {
        for (SalespersonCommissionsDocumentType c: SalespersonCommissionsDocumentType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
