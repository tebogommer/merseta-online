
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ShippingProcessStatus.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="ShippingProcessStatus"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="None"/&gt;
 *     &lt;enumeration value="New"/&gt;
 *     &lt;enumeration value="Ready To Pick"/&gt;
 *     &lt;enumeration value="Unconfirmed Pick"/&gt;
 *     &lt;enumeration value="Ready To Pack"/&gt;
 *     &lt;enumeration value="Unconfirmed Pack"/&gt;
 *     &lt;enumeration value="Shipped"/&gt;
 *     &lt;enumeration value="Ready To Post"/&gt;
 *     &lt;enumeration value="In Process"/&gt;
 *     &lt;enumeration value="Complete"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "ShippingProcessStatus")
@XmlEnum
public enum ShippingProcessStatus {

    @XmlEnumValue("None")
    NONE("None"),
    @XmlEnumValue("New")
    NEW("New"),
    @XmlEnumValue("Ready To Pick")
    READY_TO_PICK("Ready To Pick"),
    @XmlEnumValue("Unconfirmed Pick")
    UNCONFIRMED_PICK("Unconfirmed Pick"),
    @XmlEnumValue("Ready To Pack")
    READY_TO_PACK("Ready To Pack"),
    @XmlEnumValue("Unconfirmed Pack")
    UNCONFIRMED_PACK("Unconfirmed Pack"),
    @XmlEnumValue("Shipped")
    SHIPPED("Shipped"),
    @XmlEnumValue("Ready To Post")
    READY_TO_POST("Ready To Post"),
    @XmlEnumValue("In Process")
    IN_PROCESS("In Process"),
    @XmlEnumValue("Complete")
    COMPLETE("Complete");
    private final String value;

    ShippingProcessStatus(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ShippingProcessStatus fromValue(String v) {
        for (ShippingProcessStatus c: ShippingProcessStatus.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
