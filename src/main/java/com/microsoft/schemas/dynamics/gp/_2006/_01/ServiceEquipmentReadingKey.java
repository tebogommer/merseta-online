
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ServiceEquipmentReadingKey complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ServiceEquipmentReadingKey"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/gp/2006/01}ReferenceKey"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="ServiceEquipmentKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ServiceEquipmentKey" minOccurs="0"/&gt;
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
@XmlType(name = "ServiceEquipmentReadingKey", propOrder = {
    "serviceEquipmentKey",
    "sequenceNumber"
})
public class ServiceEquipmentReadingKey
    extends ReferenceKey
{

    @XmlElement(name = "ServiceEquipmentKey")
    protected ServiceEquipmentKey serviceEquipmentKey;
    @XmlElement(name = "SequenceNumber")
    protected int sequenceNumber;

    /**
     * Gets the value of the serviceEquipmentKey property.
     * 
     * @return
     *     possible object is
     *     {@link ServiceEquipmentKey }
     *     
     */
    public ServiceEquipmentKey getServiceEquipmentKey() {
        return serviceEquipmentKey;
    }

    /**
     * Sets the value of the serviceEquipmentKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link ServiceEquipmentKey }
     *     
     */
    public void setServiceEquipmentKey(ServiceEquipmentKey value) {
        this.serviceEquipmentKey = value;
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
