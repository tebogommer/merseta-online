
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ServicePartLine complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ServicePartLine"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/gp/2006/01}ServiceLine"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="BillablePartsPercent" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}Percent" minOccurs="0"/&gt;
 *         &lt;element name="Quantity" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}Quantity" minOccurs="0"/&gt;
 *         &lt;element name="QuantityBackordered" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}Quantity" minOccurs="0"/&gt;
 *         &lt;element name="ShipToAddressOptionType" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ServiceAddressOptionTypes"/&gt;
 *         &lt;element name="StatusCodeKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ServiceCallStatusCodeKey" minOccurs="0"/&gt;
 *         &lt;element name="WarehouseKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}WarehouseKey" minOccurs="0"/&gt;
 *         &lt;element name="PurchaseOrder" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ServiceLinePurchaseOrderCreation" minOccurs="0"/&gt;
 *         &lt;element name="DoNotCreateReturnLine" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="UseType" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ServicePartUseType"/&gt;
 *         &lt;element name="MiscellaneousAddressKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}AddressKey" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ServicePartLine", propOrder = {
    "billablePartsPercent",
    "quantity",
    "quantityBackordered",
    "shipToAddressOptionType",
    "statusCodeKey",
    "warehouseKey",
    "purchaseOrder",
    "doNotCreateReturnLine",
    "useType",
    "miscellaneousAddressKey"
})
@XmlSeeAlso({
    ServiceCallPartLine.class,
    ServiceQuotePartLine.class
})
public abstract class ServicePartLine
    extends ServiceLine
{

    @XmlElement(name = "BillablePartsPercent")
    protected Percent billablePartsPercent;
    @XmlElement(name = "Quantity")
    protected Quantity quantity;
    @XmlElement(name = "QuantityBackordered")
    protected Quantity quantityBackordered;
    @XmlElement(name = "ShipToAddressOptionType", required = true, nillable = true)
    @XmlSchemaType(name = "string")
    protected ServiceAddressOptionTypes shipToAddressOptionType;
    @XmlElement(name = "StatusCodeKey")
    protected ServiceCallStatusCodeKey statusCodeKey;
    @XmlElement(name = "WarehouseKey")
    protected WarehouseKey warehouseKey;
    @XmlElement(name = "PurchaseOrder")
    protected ServiceLinePurchaseOrderCreation purchaseOrder;
    @XmlElement(name = "DoNotCreateReturnLine", required = true, type = Boolean.class, nillable = true)
    protected Boolean doNotCreateReturnLine;
    @XmlElement(name = "UseType", required = true, nillable = true)
    @XmlSchemaType(name = "string")
    protected ServicePartUseType useType;
    @XmlElement(name = "MiscellaneousAddressKey")
    protected AddressKey miscellaneousAddressKey;

    /**
     * Gets the value of the billablePartsPercent property.
     * 
     * @return
     *     possible object is
     *     {@link Percent }
     *     
     */
    public Percent getBillablePartsPercent() {
        return billablePartsPercent;
    }

    /**
     * Sets the value of the billablePartsPercent property.
     * 
     * @param value
     *     allowed object is
     *     {@link Percent }
     *     
     */
    public void setBillablePartsPercent(Percent value) {
        this.billablePartsPercent = value;
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
     * Gets the value of the quantityBackordered property.
     * 
     * @return
     *     possible object is
     *     {@link Quantity }
     *     
     */
    public Quantity getQuantityBackordered() {
        return quantityBackordered;
    }

    /**
     * Sets the value of the quantityBackordered property.
     * 
     * @param value
     *     allowed object is
     *     {@link Quantity }
     *     
     */
    public void setQuantityBackordered(Quantity value) {
        this.quantityBackordered = value;
    }

    /**
     * Gets the value of the shipToAddressOptionType property.
     * 
     * @return
     *     possible object is
     *     {@link ServiceAddressOptionTypes }
     *     
     */
    public ServiceAddressOptionTypes getShipToAddressOptionType() {
        return shipToAddressOptionType;
    }

    /**
     * Sets the value of the shipToAddressOptionType property.
     * 
     * @param value
     *     allowed object is
     *     {@link ServiceAddressOptionTypes }
     *     
     */
    public void setShipToAddressOptionType(ServiceAddressOptionTypes value) {
        this.shipToAddressOptionType = value;
    }

    /**
     * Gets the value of the statusCodeKey property.
     * 
     * @return
     *     possible object is
     *     {@link ServiceCallStatusCodeKey }
     *     
     */
    public ServiceCallStatusCodeKey getStatusCodeKey() {
        return statusCodeKey;
    }

    /**
     * Sets the value of the statusCodeKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link ServiceCallStatusCodeKey }
     *     
     */
    public void setStatusCodeKey(ServiceCallStatusCodeKey value) {
        this.statusCodeKey = value;
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
     * Gets the value of the doNotCreateReturnLine property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isDoNotCreateReturnLine() {
        return doNotCreateReturnLine;
    }

    /**
     * Sets the value of the doNotCreateReturnLine property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setDoNotCreateReturnLine(Boolean value) {
        this.doNotCreateReturnLine = value;
    }

    /**
     * Gets the value of the useType property.
     * 
     * @return
     *     possible object is
     *     {@link ServicePartUseType }
     *     
     */
    public ServicePartUseType getUseType() {
        return useType;
    }

    /**
     * Sets the value of the useType property.
     * 
     * @param value
     *     allowed object is
     *     {@link ServicePartUseType }
     *     
     */
    public void setUseType(ServicePartUseType value) {
        this.useType = value;
    }

    /**
     * Gets the value of the miscellaneousAddressKey property.
     * 
     * @return
     *     possible object is
     *     {@link AddressKey }
     *     
     */
    public AddressKey getMiscellaneousAddressKey() {
        return miscellaneousAddressKey;
    }

    /**
     * Sets the value of the miscellaneousAddressKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link AddressKey }
     *     
     */
    public void setMiscellaneousAddressKey(AddressKey value) {
        this.miscellaneousAddressKey = value;
    }

}
