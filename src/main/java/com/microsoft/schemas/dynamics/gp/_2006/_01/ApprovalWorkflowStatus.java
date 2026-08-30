
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ApprovalWorkflowStatus.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="ApprovalWorkflowStatus"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="Not Submitted"/&gt;
 *     &lt;enumeration value="Submitted"/&gt;
 *     &lt;enumeration value="No Approval Needed"/&gt;
 *     &lt;enumeration value="Pending Approval"/&gt;
 *     &lt;enumeration value="Pending Changes"/&gt;
 *     &lt;enumeration value="Approved"/&gt;
 *     &lt;enumeration value="Rejected"/&gt;
 *     &lt;enumeration value="Workflow Ended"/&gt;
 *     &lt;enumeration value="Not Enabled"/&gt;
 *     &lt;enumeration value="Disabled"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "ApprovalWorkflowStatus")
@XmlEnum
public enum ApprovalWorkflowStatus {

    @XmlEnumValue("Not Submitted")
    NOT_SUBMITTED("Not Submitted"),
    @XmlEnumValue("Submitted")
    SUBMITTED("Submitted"),
    @XmlEnumValue("No Approval Needed")
    NO_APPROVAL_NEEDED("No Approval Needed"),
    @XmlEnumValue("Pending Approval")
    PENDING_APPROVAL("Pending Approval"),
    @XmlEnumValue("Pending Changes")
    PENDING_CHANGES("Pending Changes"),
    @XmlEnumValue("Approved")
    APPROVED("Approved"),
    @XmlEnumValue("Rejected")
    REJECTED("Rejected"),
    @XmlEnumValue("Workflow Ended")
    WORKFLOW_ENDED("Workflow Ended"),
    @XmlEnumValue("Not Enabled")
    NOT_ENABLED("Not Enabled"),
    @XmlEnumValue("Disabled")
    DISABLED("Disabled");
    private final String value;

    ApprovalWorkflowStatus(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ApprovalWorkflowStatus fromValue(String v) {
        for (ApprovalWorkflowStatus c: ApprovalWorkflowStatus.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
