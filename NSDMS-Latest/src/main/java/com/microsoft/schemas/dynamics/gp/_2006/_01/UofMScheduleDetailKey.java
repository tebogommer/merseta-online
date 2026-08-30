
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for UofMScheduleDetailKey complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="UofMScheduleDetailKey"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/gp/2006/01}ReferenceKey"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="UofMScheduleKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}UofMScheduleKey" minOccurs="0"/&gt;
 *         &lt;element name="SequenceNumber" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "UofMScheduleDetailKey", propOrder = {
    "uofMScheduleKey",
    "sequenceNumber"
})
public class UofMScheduleDetailKey
    extends ReferenceKey
{

    @XmlElement(name = "UofMScheduleKey")
    protected UofMScheduleKey uofMScheduleKey;
    @XmlElement(name = "SequenceNumber")
    protected int sequenceNumber;

    /**
     * Gets the value of the uofMScheduleKey property.
     * 
     * @return
     *     possible object is
     *     {@link UofMScheduleKey }
     *     
     */
    public UofMScheduleKey getUofMScheduleKey() {
        return uofMScheduleKey;
    }

    /**
     * Sets the value of the uofMScheduleKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link UofMScheduleKey }
     *     
     */
    public void setUofMScheduleKey(UofMScheduleKey value) {
        this.uofMScheduleKey = value;
    }

    /**
     * Gets the value of the sequenceNumber property.
     * 
     */
    public int getSequenceNumber() {
        return sequenceNumber;
    }

    /**
     * Sets the value of the sequenceNumber property.
     * 
     */
    public void setSequenceNumber(int value) {
        this.sequenceNumber = value;
    }

}
