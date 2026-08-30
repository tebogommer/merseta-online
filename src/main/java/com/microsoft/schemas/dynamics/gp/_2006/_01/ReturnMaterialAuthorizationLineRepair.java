
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ReturnMaterialAuthorizationLineRepair complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ReturnMaterialAuthorizationLineRepair"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Cost" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="FlatRatePrice" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="InvoiceItemKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ItemKey" minOccurs="0"/&gt;
 *         &lt;element name="NotToExceedPrice" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="Price" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="DocumentKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}SalesDocumentKey" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ReturnMaterialAuthorizationLineRepair", propOrder = {
    "cost",
    "flatRatePrice",
    "invoiceItemKey",
    "notToExceedPrice",
    "price",
    "documentKey"
})
public class ReturnMaterialAuthorizationLineRepair {

    @XmlElement(name = "Cost")
    protected MoneyAmount cost;
    @XmlElement(name = "FlatRatePrice")
    protected MoneyAmount flatRatePrice;
    @XmlElement(name = "InvoiceItemKey")
    protected ItemKey invoiceItemKey;
    @XmlElement(name = "NotToExceedPrice")
    protected MoneyAmount notToExceedPrice;
    @XmlElement(name = "Price")
    protected MoneyAmount price;
    @XmlElement(name = "DocumentKey")
    protected SalesDocumentKey documentKey;

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

    /**
     * Gets the value of the flatRatePrice property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getFlatRatePrice() {
        return flatRatePrice;
    }

    /**
     * Sets the value of the flatRatePrice property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setFlatRatePrice(MoneyAmount value) {
        this.flatRatePrice = value;
    }

    /**
     * Gets the value of the invoiceItemKey property.
     * 
     * @return
     *     possible object is
     *     {@link ItemKey }
     *     
     */
    public ItemKey getInvoiceItemKey() {
        return invoiceItemKey;
    }

    /**
     * Sets the value of the invoiceItemKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link ItemKey }
     *     
     */
    public void setInvoiceItemKey(ItemKey value) {
        this.invoiceItemKey = value;
    }

    /**
     * Gets the value of the notToExceedPrice property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getNotToExceedPrice() {
        return notToExceedPrice;
    }

    /**
     * Sets the value of the notToExceedPrice property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setNotToExceedPrice(MoneyAmount value) {
        this.notToExceedPrice = value;
    }

    /**
     * Gets the value of the price property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getPrice() {
        return price;
    }

    /**
     * Sets the value of the price property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setPrice(MoneyAmount value) {
        this.price = value;
    }

    /**
     * Gets the value of the documentKey property.
     * 
     * @return
     *     possible object is
     *     {@link SalesDocumentKey }
     *     
     */
    public SalesDocumentKey getDocumentKey() {
        return documentKey;
    }

    /**
     * Sets the value of the documentKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link SalesDocumentKey }
     *     
     */
    public void setDocumentKey(SalesDocumentKey value) {
        this.documentKey = value;
    }

}
