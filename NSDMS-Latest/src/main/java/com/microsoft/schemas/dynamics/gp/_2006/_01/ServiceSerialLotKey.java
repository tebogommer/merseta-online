
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ServiceSerialLotKey complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ServiceSerialLotKey"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/gp/2006/01}TransactionKey"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="ServiceLineKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ServiceLineKey" minOccurs="0"/&gt;
 *         &lt;element name="QuantityType" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}QuantityType"/&gt;
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
@XmlType(name = "ServiceSerialLotKey", propOrder = {
    "serviceLineKey",
    "quantityType",
    "sequenceNumber"
})
public class ServiceSerialLotKey
    extends TransactionKey
{

    @XmlElement(name = "ServiceLineKey")
    protected ServiceLineKey serviceLineKey;
    @XmlElement(name = "QuantityType", required = true)
    @XmlSchemaType(name = "string")
    protected QuantityType quantityType;
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
     * Gets the value of the quantityType property.
     * 
     * @return
     *     possible object is
     *     {@link QuantityType }
     *     
     */
    public QuantityType getQuantityType() {
        return quantityType;
    }

    /**
     * Sets the value of the quantityType property.
     * 
     * @param value
     *     allowed object is
     *     {@link QuantityType }
     *     
     */
    public void setQuantityType(QuantityType value) {
        this.quantityType = value;
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
