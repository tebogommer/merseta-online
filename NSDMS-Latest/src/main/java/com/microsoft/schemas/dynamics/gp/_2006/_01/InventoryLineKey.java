
package com.microsoft.schemas.dynamics.gp._2006._01;

import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for InventoryLineKey complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="InventoryLineKey"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/gp/2006/01}TransactionKey"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="InventoryKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}InventoryKey" minOccurs="0"/&gt;
 *         &lt;element name="SequenceNumber" type="{http://www.w3.org/2001/XMLSchema}decimal"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "InventoryLineKey", propOrder = {
    "inventoryKey",
    "sequenceNumber"
})
public class InventoryLineKey
    extends TransactionKey
{

    @XmlElement(name = "InventoryKey")
    protected InventoryKey inventoryKey;
    @XmlElement(name = "SequenceNumber", required = true)
    protected BigDecimal sequenceNumber;

    /**
     * Gets the value of the inventoryKey property.
     * 
     * @return
     *     possible object is
     *     {@link InventoryKey }
     *     
     */
    public InventoryKey getInventoryKey() {
        return inventoryKey;
    }

    /**
     * Sets the value of the inventoryKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link InventoryKey }
     *     
     */
    public void setInventoryKey(InventoryKey value) {
        this.inventoryKey = value;
    }

    /**
     * Gets the value of the sequenceNumber property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getSequenceNumber() {
        return sequenceNumber;
    }

    /**
     * Sets the value of the sequenceNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setSequenceNumber(BigDecimal value) {
        this.sequenceNumber = value;
    }

}
