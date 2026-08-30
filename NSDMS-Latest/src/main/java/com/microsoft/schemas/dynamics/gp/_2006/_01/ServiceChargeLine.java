
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ServiceChargeLine complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ServiceChargeLine"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/gp/2006/01}ServiceLine"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="BillableMiscellaneousPercent" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}Percent" minOccurs="0"/&gt;
 *         &lt;element name="QuantitySold" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}Quantity" minOccurs="0"/&gt;
 *         &lt;element name="PurchaseOrder" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ServiceLinePurchaseOrderCreation" minOccurs="0"/&gt;
 *         &lt;element name="PurchaseOrderLineKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}PurchaseTransactionLineKey" minOccurs="0"/&gt;
 *         &lt;element name="TotalCost" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ServiceChargeLine", propOrder = {
    "billableMiscellaneousPercent",
    "quantitySold",
    "purchaseOrder",
    "purchaseOrderLineKey",
    "totalCost"
})
@XmlSeeAlso({
    ServiceQuoteExpenseLine.class,
    ServiceQuoteAdditionalChargeLine.class,
    ServiceCallExpenseLine.class,
    ServiceCallAdditionalChargeLine.class
})
public abstract class ServiceChargeLine
    extends ServiceLine
{

    @XmlElement(name = "BillableMiscellaneousPercent")
    protected Percent billableMiscellaneousPercent;
    @XmlElement(name = "QuantitySold")
    protected Quantity quantitySold;
    @XmlElement(name = "PurchaseOrder")
    protected ServiceLinePurchaseOrderCreation purchaseOrder;
    @XmlElement(name = "PurchaseOrderLineKey")
    protected PurchaseTransactionLineKey purchaseOrderLineKey;
    @XmlElement(name = "TotalCost")
    protected MoneyAmount totalCost;

    /**
     * Gets the value of the billableMiscellaneousPercent property.
     * 
     * @return
     *     possible object is
     *     {@link Percent }
     *     
     */
    public Percent getBillableMiscellaneousPercent() {
        return billableMiscellaneousPercent;
    }

    /**
     * Sets the value of the billableMiscellaneousPercent property.
     * 
     * @param value
     *     allowed object is
     *     {@link Percent }
     *     
     */
    public void setBillableMiscellaneousPercent(Percent value) {
        this.billableMiscellaneousPercent = value;
    }

    /**
     * Gets the value of the quantitySold property.
     * 
     * @return
     *     possible object is
     *     {@link Quantity }
     *     
     */
    public Quantity getQuantitySold() {
        return quantitySold;
    }

    /**
     * Sets the value of the quantitySold property.
     * 
     * @param value
     *     allowed object is
     *     {@link Quantity }
     *     
     */
    public void setQuantitySold(Quantity value) {
        this.quantitySold = value;
    }

    /**
     * Gets the value of the purchaseOrder property.
     * 
     * @return
     *     possible object is
     *     {@link ServiceLinePurchaseOrderCreation }
     *     
     */
    public ServiceLinePurchaseOrderCreation getPurchaseOrder() {
        return purchaseOrder;
    }

    /**
     * Sets the value of the purchaseOrder property.
     * 
     * @param value
     *     allowed object is
     *     {@link ServiceLinePurchaseOrderCreation }
     *     
     */
    public void setPurchaseOrder(ServiceLinePurchaseOrderCreation value) {
        this.purchaseOrder = value;
    }

    /**
     * Gets the value of the purchaseOrderLineKey property.
     * 
     * @return
     *     possible object is
     *     {@link PurchaseTransactionLineKey }
     *     
     */
    public PurchaseTransactionLineKey getPurchaseOrderLineKey() {
        return purchaseOrderLineKey;
    }

    /**
     * Sets the value of the purchaseOrderLineKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link PurchaseTransactionLineKey }
     *     
     */
    public void setPurchaseOrderLineKey(PurchaseTransactionLineKey value) {
        this.purchaseOrderLineKey = value;
    }

    /**
     * Gets the value of the totalCost property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getTotalCost() {
        return totalCost;
    }

    /**
     * Sets the value of the totalCost property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setTotalCost(MoneyAmount value) {
        this.totalCost = value;
    }

}
