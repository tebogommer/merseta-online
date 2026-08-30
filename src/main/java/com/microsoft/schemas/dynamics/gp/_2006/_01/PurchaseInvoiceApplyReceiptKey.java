
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PurchaseInvoiceApplyReceiptKey complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PurchaseInvoiceApplyReceiptKey"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/gp/2006/01}TransactionKey"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="PurchaseTransactionKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}PurchaseTransactionKey" minOccurs="0"/&gt;
 *         &lt;element name="ReceiptKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}PurchaseTransactionKey" minOccurs="0"/&gt;
 *         &lt;element name="ReceiptLineSequenceNumber" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
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
@XmlType(name = "PurchaseInvoiceApplyReceiptKey", propOrder = {
    "purchaseTransactionKey",
    "receiptKey",
    "receiptLineSequenceNumber",
    "lineSequenceNumber"
})
public class PurchaseInvoiceApplyReceiptKey
    extends TransactionKey
{

    @XmlElement(name = "PurchaseTransactionKey")
    protected PurchaseTransactionKey purchaseTransactionKey;
    @XmlElement(name = "ReceiptKey")
    protected PurchaseTransactionKey receiptKey;
    @XmlElement(name = "ReceiptLineSequenceNumber")
    protected int receiptLineSequenceNumber;
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
     * Gets the value of the receiptKey property.
     * 
     * @return
     *     possible object is
     *     {@link PurchaseTransactionKey }
     *     
     */
    public PurchaseTransactionKey getReceiptKey() {
        return receiptKey;
    }

    /**
     * Sets the value of the receiptKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link PurchaseTransactionKey }
     *     
     */
    public void setReceiptKey(PurchaseTransactionKey value) {
        this.receiptKey = value;
    }

    /**
     * Gets the value of the receiptLineSequenceNumber property.
     * 
     */
    public int getReceiptLineSequenceNumber() {
        return receiptLineSequenceNumber;
    }

    /**
     * Sets the value of the receiptLineSequenceNumber property.
     * 
     */
    public void setReceiptLineSequenceNumber(int value) {
        this.receiptLineSequenceNumber = value;
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
