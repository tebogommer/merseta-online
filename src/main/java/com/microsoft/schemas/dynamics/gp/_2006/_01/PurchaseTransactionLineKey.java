
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PurchaseTransactionLineKey complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PurchaseTransactionLineKey"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/gp/2006/01}TransactionKey"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="PurchaseTransactionKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}PurchaseTransactionKey" minOccurs="0"/&gt;
 *         &lt;element name="LineSequenceNumber" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PurchaseTransactionLineKey", propOrder = {
    "purchaseTransactionKey",
    "lineSequenceNumber"
})
public class PurchaseTransactionLineKey
    extends TransactionKey
{

    @XmlElement(name = "PurchaseTransactionKey")
    protected PurchaseTransactionKey purchaseTransactionKey;
    @XmlElement(name = "LineSequenceNumber")
    protected int lineSequenceNumber;

    /**
     * Gets the value of the purchaseTransactionKey property.
     * 
     * @return
     *     possible object is
     *     {@link PurchaseTransactionKey }
     *     
     */
    public PurchaseTransactionKey getPurchaseTransactionKey() {
        return purchaseTransactionKey;
    }

    /**
     * Sets the value of the purchaseTransactionKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link PurchaseTransactionKey }
     *     
     */
    public void setPurchaseTransactionKey(PurchaseTransactionKey value) {
        this.purchaseTransactionKey = value;
    }

    /**
     * Gets the value of the lineSequenceNumber property.
     * 
     */
    public int getLineSequenceNumber() {
        return lineSequenceNumber;
    }

    /**
     * Sets the value of the lineSequenceNumber property.
     * 
     */
    public void setLineSequenceNumber(int value) {
        this.lineSequenceNumber = value;
    }

}
