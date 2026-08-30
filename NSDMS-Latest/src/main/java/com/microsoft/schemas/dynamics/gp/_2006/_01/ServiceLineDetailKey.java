
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ServiceLineDetailKey complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ServiceLineDetailKey"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/gp/2006/01}TransactionKey"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="ServiceLineKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ServiceLineKey" minOccurs="0"/&gt;
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
@XmlType(name = "ServiceLineDetailKey", propOrder = {
    "serviceLineKey",
    "sequenceNumber"
})
public class ServiceLineDetailKey
    extends TransactionKey
{

    @XmlElement(name = "ServiceLineKey")
    protected ServiceLineKey serviceLineKey;
    @XmlElement(name = "SequenceNumber")
    protected int sequenceNumber;

    /**
     * Gets the value of the serviceLineKey property.
     * 
     * @return
     *     possible object is
     *     {@link ServiceLineKey }
     *     
     */
    public ServiceLineKey getServiceLineKey() {
        return serviceLineKey;
    }

    /**
     * Sets the value of the serviceLineKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link ServiceLineKey }
     *     
     */
    public void setServiceLineKey(ServiceLineKey value) {
        this.serviceLineKey = value;
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
