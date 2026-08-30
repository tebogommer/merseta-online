
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ItemWarehouse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ItemWarehouse"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/gp/2006/01}BusinessObject"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Key" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ItemWarehouseKey" minOccurs="0"/&gt;
 *         &lt;element name="Bin" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="PrimaryVendorKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}VendorKey" minOccurs="0"/&gt;
 *         &lt;element name="LandedCostGroupKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}LandedCostGroupKey" minOccurs="0"/&gt;
 *         &lt;element name="RequisitionedQuantity" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}Quantity" minOccurs="0"/&gt;
 *         &lt;element name="BuyerKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}BuyerKey" minOccurs="0"/&gt;
 *         &lt;element name="PlannerKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}PlannerKey" minOccurs="0"/&gt;
 *         &lt;element name="Planning" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}Planning" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ItemWarehouse", propOrder = {
    "key",
    "bin",
    "primaryVendorKey",
    "landedCostGroupKey",
    "requisitionedQuantity",
    "buyerKey",
    "plannerKey",
    "planning"
})
public class ItemWarehouse
    extends BusinessObject
{

    @XmlElement(name = "Key")
    protected ItemWarehouseKey key;
    @XmlElement(name = "Bin")
    protected String bin;
    @XmlElement(name = "PrimaryVendorKey")
    protected VendorKey primaryVendorKey;
    @XmlElement(name = "LandedCostGroupKey")
    protected LandedCostGroupKey landedCostGroupKey;
    @XmlElement(name = "RequisitionedQuantity")
    protected Quantity requisitionedQuantity;
    @XmlElement(name = "BuyerKey")
    protected BuyerKey buyerKey;
    @XmlElement(name = "PlannerKey")
    protected PlannerKey plannerKey;
    @XmlElement(name = "Planning")
    protected Planning planning;

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
     * Gets the value of the bin property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBin() {
        return bin;
    }

    /**
     * Sets the value of the bin property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBin(String value) {
        this.bin = value;
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
     * Gets the value of the landedCostGroupKey property.
     * 
     * @return
     *     possible object is
     *     {@link LandedCostGroupKey }
     *     
     */
    public LandedCostGroupKey getLandedCostGroupKey() {
        return landedCostGroupKey;
    }

    /**
     * Sets the value of the landedCostGroupKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link LandedCostGroupKey }
     *     
     */
    public void setLandedCostGroupKey(LandedCostGroupKey value) {
        this.landedCostGroupKey = value;
    }

    /**
     * Gets the value of the requisitionedQuantity property.
     * 
     * @return
     *     possible object is
     *     {@link Quantity }
     *     
     */
    public Quantity getRequisitionedQuantity() {
        return requisitionedQuantity;
    }

    /**
     * Sets the value of the requisitionedQuantity property.
     * 
     * @param value
     *     allowed object is
     *     {@link Quantity }
     *     
     */
    public void setRequisitionedQuantity(Quantity value) {
        this.requisitionedQuantity = value;
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

    /**
     * Gets the value of the planning property.
     * 
     * @return
     *     possible object is
     *     {@link Planning }
     *     
     */
    public Planning getPlanning() {
        return planning;
    }

    /**
     * Sets the value of the planning property.
     * 
     * @param value
     *     allowed object is
     *     {@link Planning }
     *     
     */
    public void setPlanning(Planning value) {
        this.planning = value;
    }

}
