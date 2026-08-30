
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ReturnMaterialAuthorizationLineReplacement complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ReturnMaterialAuthorizationLineReplacement"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="ItemKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ItemKey" minOccurs="0"/&gt;
 *         &lt;element name="PriceLevelKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}PriceLevelKey" minOccurs="0"/&gt;
 *         &lt;element name="UofM" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="WarehouseKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}WarehouseKey" minOccurs="0"/&gt;
 *         &lt;element name="Quantity" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}Quantity" minOccurs="0"/&gt;
 *         &lt;element name="UnitCost" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="UnitPrice" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ReturnMaterialAuthorizationLineReplacement", propOrder = {
    "itemKey",
    "priceLevelKey",
    "uofM",
    "warehouseKey",
    "quantity",
    "unitCost",
    "unitPrice"
})
public class ReturnMaterialAuthorizationLineReplacement {

    @XmlElement(name = "ItemKey")
    protected ItemKey itemKey;
    @XmlElement(name = "PriceLevelKey")
    protected PriceLevelKey priceLevelKey;
    @XmlElement(name = "UofM")
    protected String uofM;
    @XmlElement(name = "WarehouseKey")
    protected WarehouseKey warehouseKey;
    @XmlElement(name = "Quantity")
    protected Quantity quantity;
    @XmlElement(name = "UnitCost")
    protected MoneyAmount unitCost;
    @XmlElement(name = "UnitPrice")
    protected MoneyAmount unitPrice;

    /**
     * Gets the value of the itemKey property.
     * 
     * @return
     *     possible object is
     *     {@link ItemKey }
     *     
     */
    public ItemKey getItemKey() {
        return itemKey;
    }

    /**
     * Sets the value of the itemKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link ItemKey }
     *     
     */
    public void setItemKey(ItemKey value) {
        this.itemKey = value;
    }

    /**
     * Gets the value of the priceLevelKey property.
     * 
     * @return
     *     possible object is
     *     {@link PriceLevelKey }
     *     
     */
    public PriceLevelKey getPriceLevelKey() {
        return priceLevelKey;
    }

    /**
     * Sets the value of the priceLevelKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link PriceLevelKey }
     *     
     */
    public void setPriceLevelKey(PriceLevelKey value) {
        this.priceLevelKey = value;
    }

    /**
     * Gets the value of the uofM property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUofM() {
        return uofM;
    }

    /**
     * Sets the value of the uofM property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUofM(String value) {
        this.uofM = value;
    }

    /**
     * Gets the value of the warehouseKey property.
     * 
     * @return
     *     possible object is
     *     {@link WarehouseKey }
     *     
     */
    public WarehouseKey getWarehouseKey() {
        return warehouseKey;
    }

    /**
     * Sets the value of the warehouseKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link WarehouseKey }
     *     
     */
    public void setWarehouseKey(WarehouseKey value) {
        this.warehouseKey = value;
    }

    /**
     * Gets the value of the quantity property.
     * 
     * @return
     *     possible object is
     *     {@link Quantity }
     *     
     */
    public Quantity getQuantity() {
        return quantity;
    }

    /**
     * Sets the value of the quantity property.
     * 
     * @param value
     *     allowed object is
     *     {@link Quantity }
     *     
     */
    public void setQuantity(Quantity value) {
        this.quantity = value;
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
     * Gets the value of the unitPrice property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getUnitPrice() {
        return unitPrice;
    }

    /**
     * Sets the value of the unitPrice property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setUnitPrice(MoneyAmount value) {
        this.unitPrice = value;
    }

}
