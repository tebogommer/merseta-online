
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ItemWarehouseSummary complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ItemWarehouseSummary"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Key" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ItemWarehouseKey" minOccurs="0"/&gt;
 *         &lt;element name="PrimaryVendorKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}VendorKey" minOccurs="0"/&gt;
 *         &lt;element name="BuyerKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}BuyerKey" minOccurs="0"/&gt;
 *         &lt;element name="PlannerKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}PlannerKey" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ItemWarehouseSummary", propOrder = {
    "key",
    "primaryVendorKey",
    "buyerKey",
    "plannerKey"
})
public class ItemWarehouseSummary {

    @XmlElement(name = "Key")
    protected ItemWarehouseKey key;
    @XmlElement(name = "PrimaryVendorKey")
    protected VendorKey primaryVendorKey;
    @XmlElement(name = "BuyerKey")
    protected BuyerKey buyerKey;
    @XmlElement(name = "PlannerKey")
    protected PlannerKey plannerKey;

    /**
     * Gets the value of the key property.
     * 
     * @return
     *     possible object is
     *     {@link ItemWarehouseKey }
     *     
     */
    public ItemWarehouseKey getKey() {
        return key;
    }

    /**
     * Sets the value of the key property.
     * 
     * @param value
     *     allowed object is
     *     {@link ItemWarehouseKey }
     *     
     */
    public void setKey(ItemWarehouseKey value) {
        this.key = value;
    }

    /**
     * Gets the value of the primaryVendorKey property.
     * 
     * @return
     *     possible object is
     *     {@link VendorKey }
     *     
     */
    public VendorKey getPrimaryVendorKey() {
        return primaryVendorKey;
    }

    /**
     * Sets the value of the primaryVendorKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link VendorKey }
     *     
     */
    public void setPrimaryVendorKey(VendorKey value) {
        this.primaryVendorKey = value;
    }

    /**
     * Gets the value of the buyerKey property.
     * 
     * @return
     *     possible object is
     *     {@link BuyerKey }
     *     
     */
    public BuyerKey getBuyerKey() {
        return buyerKey;
    }

    /**
     * Sets the value of the buyerKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link BuyerKey }
     *     
     */
    public void setBuyerKey(BuyerKey value) {
        this.buyerKey = value;
    }

    /**
     * Gets the value of the plannerKey property.
     * 
     * @return
     *     possible object is
     *     {@link PlannerKey }
     *     
     */
    public PlannerKey getPlannerKey() {
        return plannerKey;
    }

    /**
     * Sets the value of the plannerKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link PlannerKey }
     *     
     */
    public void setPlannerKey(PlannerKey value) {
        this.plannerKey = value;
    }

}
