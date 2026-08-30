
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ReturnMaterialAuthorizationReturnStatus.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="ReturnMaterialAuthorizationReturnStatus"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="New"/&gt;
 *     &lt;enumeration value="Repair"/&gt;
 *     &lt;enumeration value="Credit"/&gt;
 *     &lt;enumeration value="Replace"/&gt;
 *     &lt;enumeration value="Cross-Ship"/&gt;
 *     &lt;enumeration value="None"/&gt;
 *     &lt;enumeration value="Open RTV or WO"/&gt;
 *     &lt;enumeration value="Closed RTV or WO"/&gt;
 *     &lt;enumeration value="Ready To Close"/&gt;
 *     &lt;enumeration value="Closed RMA"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "ReturnMaterialAuthorizationReturnStatus")
@XmlEnum
public enum ReturnMaterialAuthorizationReturnStatus {

    @XmlEnumValue("New")
    NEW("New"),
    @XmlEnumValue("Repair")
    REPAIR("Repair"),
    @XmlEnumValue("Credit")
    CREDIT("Credit"),
    @XmlEnumValue("Replace")
    REPLACE("Replace"),
    @XmlEnumValue("Cross-Ship")
    CROSS_SHIP("Cross-Ship"),
    @XmlEnumValue("None")
    NONE("None"),
    @XmlEnumValue("Open RTV or WO")
    OPEN_RTV_OR_WO("Open RTV or WO"),
    @XmlEnumValue("Closed RTV or WO")
    CLOSED_RTV_OR_WO("Closed RTV or WO"),
    @XmlEnumValue("Ready To Close")
    READY_TO_CLOSE("Ready To Close"),
    @XmlEnumValue("Closed RMA")
    CLOSED_RMA("Closed RMA");
    private final String value;

    ReturnMaterialAuthorizationReturnStatus(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ReturnMaterialAuthorizationReturnStatus fromValue(String v) {
        for (ReturnMaterialAuthorizationReturnStatus c: ReturnMaterialAuthorizationReturnStatus.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
