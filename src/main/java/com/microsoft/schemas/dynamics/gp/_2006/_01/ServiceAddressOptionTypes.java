
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ServiceAddressOptionTypes.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="ServiceAddressOptionTypes"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="Labor, Additional Charge or Expense"/&gt;
 *     &lt;enumeration value="Customer Address ID"/&gt;
 *     &lt;enumeration value="Technician Site"/&gt;
 *     &lt;enumeration value="Miscellaneous Address Code"/&gt;
 *     &lt;enumeration value="Transfer Header Modified"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "ServiceAddressOptionTypes")
@XmlEnum
public enum ServiceAddressOptionTypes {

    @XmlEnumValue("Labor, Additional Charge or Expense")
    LABOR_ADDITIONAL_CHARGE_OR_EXPENSE("Labor, Additional Charge or Expense"),
    @XmlEnumValue("Customer Address ID")
    CUSTOMER_ADDRESS_ID("Customer Address ID"),
    @XmlEnumValue("Technician Site")
    TECHNICIAN_SITE("Technician Site"),
    @XmlEnumValue("Miscellaneous Address Code")
    MISCELLANEOUS_ADDRESS_CODE("Miscellaneous Address Code"),
    @XmlEnumValue("Transfer Header Modified")
    TRANSFER_HEADER_MODIFIED("Transfer Header Modified");
    private final String value;

    ServiceAddressOptionTypes(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ServiceAddressOptionTypes fromValue(String v) {
        for (ServiceAddressOptionTypes c: ServiceAddressOptionTypes.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
