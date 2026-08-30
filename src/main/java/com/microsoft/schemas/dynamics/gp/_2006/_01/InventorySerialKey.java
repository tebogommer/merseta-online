
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for InventorySerialKey complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="InventorySerialKey"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/gp/2006/01}TransactionKey"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="InventoryLineKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}InventoryLineKey" minOccurs="0"/&gt;
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
@XmlType(name = "InventorySerialKey", propOrder = {
    "inventoryLineKey",
    "sequenceNumber"
})
public class InventorySerialKey
    extends TransactionKey
{

    @XmlElement(name = "InventoryLineKey")
    protected InventoryLineKey inventoryLineKey;
    @XmlElement(name = "SequenceNumber")
    protected int sequenceNumber;

    /**
     * Gets the value of the inventoryLineKey property.
     * 
     * @return
     *     possible object is
     *     {@link InventoryLineKey }
     *     
     */
    public InventoryLineKey getInventoryLineKey() {
        return inventoryLineKey;
    }

    /**
     * Sets the value of the inventoryLineKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link InventoryLineKey }
     *     
     */
    public void setInventoryLineKey(InventoryLineKey value) {
        this.inventoryLineKey = value;
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
