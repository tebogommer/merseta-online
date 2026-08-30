
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PurchaseInvoiceApplyReceipt complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PurchaseInvoiceApplyReceipt"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/gp/2006/01}BusinessObject"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Key" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}PurchaseInvoiceApplyReceiptKey" minOccurs="0"/&gt;
 *         &lt;element name="QuantityInvoiced" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}Quantity" minOccurs="0"/&gt;
 *         &lt;element name="DoesRevalueInventory" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="VarianceGLAccountKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}GLAccountNumberKey" minOccurs="0"/&gt;
 *         &lt;element name="UnitCost" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="Cost" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PurchaseInvoiceApplyReceipt", propOrder = {
    "key",
    "quantityInvoiced",
    "doesRevalueInventory",
    "varianceGLAccountKey",
    "unitCost",
    "cost"
})
public class PurchaseInvoiceApplyReceipt
    extends BusinessObject
{

    @XmlElement(name = "Key")
    protected PurchaseInvoiceApplyReceiptKey key;
    @XmlElement(name = "QuantityInvoiced")
    protected Quantity quantityInvoiced;
    @XmlElement(name = "DoesRevalueInventory", required = true, type = Boolean.class, nillable = true)
    protected Boolean doesRevalueInventory;
    @XmlElement(name = "VarianceGLAccountKey")
    protected GLAccountNumberKey varianceGLAccountKey;
    @XmlElement(name = "UnitCost")
    protected MoneyAmount unitCost;
    @XmlElement(name = "Cost")
    protected MoneyAmount cost;

    /**
     * Gets the value of the key property.
     * 
     * @return
     *     possible object is
     *     {@link PurchaseInvoiceApplyReceiptKey }
     *     
     */
    public PurchaseInvoiceApplyReceiptKey getKey() {
        return key;
    }

    /**
     * Sets the value of the key property.
     * 
     * @param value
     *     allowed object is
     *     {@link PurchaseInvoiceApplyReceiptKey }
     *     
     */
    public void setKey(PurchaseInvoiceApplyReceiptKey value) {
        this.key = value;
    }

    /**
     * Gets the value of the quantityInvoiced property.
     * 
     * @return
     *     possible object is
     *     {@link Quantity }
     *     
     */
    public Quantity getQuantityInvoiced() {
        return quantityInvoiced;
    }

    /**
     * Sets the value of the quantityInvoiced property.
     * 
     * @param value
     *     allowed object is
     *     {@link Quantity }
     *     
     */
    public void setQuantityInvoiced(Quantity value) {
        this.quantityInvoiced = value;
    }

    /**
     * Gets the value of the doesRevalueInventory property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isDoesRevalueInventory() {
        return doesRevalueInventory;
    }

    /**
     * Sets the value of the doesRevalueInventory property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setDoesRevalueInventory(Boolean value) {
        this.doesRevalueInventory = value;
    }

    /**
     * Gets the value of the varianceGLAccountKey property.
     * 
     * @return
     *     possible object is
     *     {@link GLAccountNumberKey }
     *     
     */
    public GLAccountNumberKey getVarianceGLAccountKey() {
        return varianceGLAccountKey;
    }

    /**
     * Sets the value of the varianceGLAccountKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link GLAccountNumberKey }
     *     
     */
    public void setVarianceGLAccountKey(GLAccountNumberKey value) {
        this.varianceGLAccountKey = value;
    }

    /**
     * Gets the value of the unitCost property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getUnitCost() {
        return unitCost;
    }

    /**
     * Sets the value of the unitCost property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setUnitCost(MoneyAmount value) {
        this.unitCost = value;
    }

    /**
     * Gets the value of the cost property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getCost() {
        return cost;
    }

    /**
     * Sets the value of the cost property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setCost(MoneyAmount value) {
        this.cost = value;
    }

}
