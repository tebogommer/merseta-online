
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for SalesItemWarehouse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SalesItemWarehouse"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/gp/2006/01}BusinessObject"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Key" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ItemWarehouseKey" minOccurs="0"/&gt;
 *         &lt;element name="InUseQuantity" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}Quantity" minOccurs="0"/&gt;
 *         &lt;element name="InServiceQuantity" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}Quantity" minOccurs="0"/&gt;
 *         &lt;element name="ReturnedQuantity" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}Quantity" minOccurs="0"/&gt;
 *         &lt;element name="DamagedQuantity" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}Quantity" minOccurs="0"/&gt;
 *         &lt;element name="OnHandQuantity" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}Quantity" minOccurs="0"/&gt;
 *         &lt;element name="AllocatedQuantity" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}Quantity" minOccurs="0"/&gt;
 *         &lt;element name="OnOrderQuantity" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}Quantity" minOccurs="0"/&gt;
 *         &lt;element name="BackOrderedQuantity" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}Quantity" minOccurs="0"/&gt;
 *         &lt;element name="Bins" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ArrayOfSalesItemWarehouseBin" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SalesItemWarehouse", propOrder = {
    "key",
    "inUseQuantity",
    "inServiceQuantity",
    "returnedQuantity",
    "damagedQuantity",
    "onHandQuantity",
    "allocatedQuantity",
    "onOrderQuantity",
    "backOrderedQuantity",
    "bins"
})
public class SalesItemWarehouse
    extends BusinessObject
{

    @XmlElement(name = "Key")
    protected ItemWarehouseKey key;
    @XmlElement(name = "InUseQuantity")
    protected Quantity inUseQuantity;
    @XmlElement(name = "InServiceQuantity")
    protected Quantity inServiceQuantity;
    @XmlElement(name = "ReturnedQuantity")
    protected Quantity returnedQuantity;
    @XmlElement(name = "DamagedQuantity")
    protected Quantity damagedQuantity;
    @XmlElement(name = "OnHandQuantity")
    protected Quantity onHandQuantity;
    @XmlElement(name = "AllocatedQuantity")
    protected Quantity allocatedQuantity;
    @XmlElement(name = "OnOrderQuantity")
    protected Quantity onOrderQuantity;
    @XmlElement(name = "BackOrderedQuantity")
    protected Quantity backOrderedQuantity;
    @XmlElement(name = "Bins")
    protected ArrayOfSalesItemWarehouseBin bins;

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
     * Gets the value of the inUseQuantity property.
     * 
     * @return
     *     possible object is
     *     {@link Quantity }
     *     
     */
    public Quantity getInUseQuantity() {
        return inUseQuantity;
    }

    /**
     * Sets the value of the inUseQuantity property.
     * 
     * @param value
     *     allowed object is
     *     {@link Quantity }
     *     
     */
    public void setInUseQuantity(Quantity value) {
        this.inUseQuantity = value;
    }

    /**
     * Gets the value of the inServiceQuantity property.
     * 
     * @return
     *     possible object is
     *     {@link Quantity }
     *     
     */
    public Quantity getInServiceQuantity() {
        return inServiceQuantity;
    }

    /**
     * Sets the value of the inServiceQuantity property.
     * 
     * @param value
     *     allowed object is
     *     {@link Quantity }
     *     
     */
    public void setInServiceQuantity(Quantity value) {
        this.inServiceQuantity = value;
    }

    /**
     * Gets the value of the returnedQuantity property.
     * 
     * @return
     *     possible object is
     *     {@link Quantity }
     *     
     */
    public Quantity getReturnedQuantity() {
        return returnedQuantity;
    }

    /**
     * Sets the value of the returnedQuantity property.
     * 
     * @param value
     *     allowed object is
     *     {@link Quantity }
     *     
     */
    public void setReturnedQuantity(Quantity value) {
        this.returnedQuantity = value;
    }

    /**
     * Gets the value of the damagedQuantity property.
     * 
     * @return
     *     possible object is
     *     {@link Quantity }
     *     
     */
    public Quantity getDamagedQuantity() {
        return damagedQuantity;
    }

    /**
     * Sets the value of the damagedQuantity property.
     * 
     * @param value
     *     allowed object is
     *     {@link Quantity }
     *     
     */
    public void setDamagedQuantity(Quantity value) {
        this.damagedQuantity = value;
    }

    /**
     * Gets the value of the onHandQuantity property.
     * 
     * @return
     *     possible object is
     *     {@link Quantity }
     *     
     */
    public Quantity getOnHandQuantity() {
        return onHandQuantity;
    }

    /**
     * Sets the value of the onHandQuantity property.
     * 
     * @param value
     *     allowed object is
     *     {@link Quantity }
     *     
     */
    public void setOnHandQuantity(Quantity value) {
        this.onHandQuantity = value;
    }

    /**
     * Gets the value of the allocatedQuantity property.
     * 
     * @return
     *     possible object is
     *     {@link Quantity }
     *     
     */
    public Quantity getAllocatedQuantity() {
        return allocatedQuantity;
    }

    /**
     * Sets the value of the allocatedQuantity property.
     * 
     * @param value
     *     allowed object is
     *     {@link Quantity }
     *     
     */
    public void setAllocatedQuantity(Quantity value) {
        this.allocatedQuantity = value;
    }

    /**
     * Gets the value of the onOrderQuantity property.
     * 
     * @return
     *     possible object is
     *     {@link Quantity }
     *     
     */
    public Quantity getOnOrderQuantity() {
        return onOrderQuantity;
    }

    /**
     * Sets the value of the onOrderQuantity property.
     * 
     * @param value
     *     allowed object is
     *     {@link Quantity }
     *     
     */
    public void setOnOrderQuantity(Quantity value) {
        this.onOrderQuantity = value;
    }

    /**
     * Gets the value of the backOrderedQuantity property.
     * 
     * @return
     *     possible object is
     *     {@link Quantity }
     *     
     */
    public Quantity getBackOrderedQuantity() {
        return backOrderedQuantity;
    }

    /**
     * Sets the value of the backOrderedQuantity property.
     * 
     * @param value
     *     allowed object is
     *     {@link Quantity }
     *     
     */
    public void setBackOrderedQuantity(Quantity value) {
        this.backOrderedQuantity = value;
    }

    /**
     * Gets the value of the bins property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfSalesItemWarehouseBin }
     *     
     */
    public ArrayOfSalesItemWarehouseBin getBins() {
        return bins;
    }

    /**
     * Sets the value of the bins property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfSalesItemWarehouseBin }
     *     
     */
    public void setBins(ArrayOfSalesItemWarehouseBin value) {
        this.bins = value;
    }

}
