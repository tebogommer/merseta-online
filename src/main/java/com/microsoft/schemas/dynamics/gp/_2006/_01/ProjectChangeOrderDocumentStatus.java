
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ProjectChangeOrderDocumentStatus.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="ProjectChangeOrderDocumentStatus"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="Processed when pending"/&gt;
 *     &lt;enumeration value="Unprocessed when pending or unapproved"/&gt;
 *     &lt;enumeration value="Processed when unapproved"/&gt;
 *     &lt;enumeration value="Unprocessed when approved"/&gt;
 *     &lt;enumeration value="Processed when approved"/&gt;
 *     &lt;enumeration value="Record saved again after it has already been approved and processed"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "ProjectChangeOrderDocumentStatus")
@XmlEnum
public enum ProjectChangeOrderDocumentStatus {

    @XmlEnumValue("Processed when pending")
    PROCESSED_WHEN_PENDING("Processed when pending"),
    @XmlEnumValue("Unprocessed when pending or unapproved")
    UNPROCESSED_WHEN_PENDING_OR_UNAPPROVED("Unprocessed when pending or unapproved"),
    @XmlEnumValue("Processed when unapproved")
    PROCESSED_WHEN_UNAPPROVED("Processed when unapproved"),
    @XmlEnumValue("Unprocessed when approved")
    UNPROCESSED_WHEN_APPROVED("Unprocessed when approved"),
    @XmlEnumValue("Processed when approved")
    PROCESSED_WHEN_APPROVED("Processed when approved"),
    @XmlEnumValue("Record saved again after it has already been approved and processed")
    RECORD_SAVED_AGAIN_AFTER_IT_HAS_ALREADY_BEEN_APPROVED_AND_PROCESSED("Record saved again after it has already been approved and processed");
    private final String value;

    ProjectChangeOrderDocumentStatus(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ProjectChangeOrderDocumentStatus fromValue(String v) {
        for (ProjectChangeOrderDocumentStatus c: ProjectChangeOrderDocumentStatus.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
