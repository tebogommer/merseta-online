
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for PlannedOrderSummary complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PlannedOrderSummary"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/gp/2006/01}BusinessObjectSummary"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="PlannedOrderKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}PlannedOrderKey" minOccurs="0"/&gt;
 *         &lt;element name="ItemKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ItemKey" minOccurs="0"/&gt;
 *         &lt;element name="LocationKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}WarehouseKey" minOccurs="0"/&gt;
 *         &lt;element name="DueDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="RunNumber" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="ReleaseDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="QuantityToOrder" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}Quantity" minOccurs="0"/&gt;
 *         &lt;element name="VendorKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}VendorKey" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PlannedOrderSummary", propOrder = {
    "plannedOrderKey",
    "itemKey",
    "locationKey",
    "dueDate",
    "runNumber",
    "releaseDate",
    "quantityToOrder",
    "vendorKey"
})
@XmlSeeAlso({
    VendorPlannedOrderSummary.class
})
public class PlannedOrderSummary
    extends BusinessObjectSummary
{

    @XmlElement(name = "PlannedOrderKey")
    protected PlannedOrderKey plannedOrderKey;
    @XmlElement(name = "ItemKey")
    protected ItemKey itemKey;
    @XmlElement(name = "LocationKey")
    protected WarehouseKey locationKey;
    @XmlElement(name = "DueDate", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dueDate;
    @XmlElement(name = "RunNumber", required = true, type = Integer.class, nillable = true)
    protected Integer runNumber;
    @XmlElement(name = "ReleaseDate", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar releaseDate;
    @XmlElement(name = "QuantityToOrder")
    protected Quantity quantityToOrder;
    @XmlElement(name = "VendorKey")
    protected VendorKey vendorKey;

    /**
     * Gets the value of the plannedOrderKey property.
     * 
     * @return
     *     possible object is
     *     {@link PlannedOrderKey }
     *     
     */
    public PlannedOrderKey getPlannedOrderKey() {
        return plannedOrderKey;
    }

    /**
     * Sets the value of the plannedOrderKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link PlannedOrderKey }
     *     
     */
    public void setPlannedOrderKey(PlannedOrderKey value) {
        this.plannedOrderKey = value;
    }

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
     * Gets the value of the locationKey property.
     * 
     * @return
     *     possible object is
     *     {@link WarehouseKey }
     *     
     */
    public WarehouseKey getLocationKey() {
        return locationKey;
    }

    /**
     * Sets the value of the locationKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link WarehouseKey }
     *     
     */
    public void setLocationKey(WarehouseKey value) {
        this.locationKey = value;
    }

    /**
     * Gets the value of the dueDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDueDate() {
        return dueDate;
    }

    /**
     * Sets the value of the dueDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDueDate(XMLGregorianCalendar value) {
        this.dueDate = value;
    }

    /**
     * Gets the value of the runNumber property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getRunNumber() {
        return runNumber;
    }

    /**
     * Sets the value of the runNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setRunNumber(Integer value) {
        this.runNumber = value;
    }

    /**
     * Gets the value of the releaseDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getReleaseDate() {
        return releaseDate;
    }

    /**
     * Sets the value of the releaseDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setReleaseDate(XMLGregorianCalendar value) {
        this.releaseDate = value;
    }

    /**
     * Gets the value of the quantityToOrder property.
     * 
     * @return
     *     possible object is
     *     {@link Quantity }
     *     
     */
    public Quantity getQuantityToOrder() {
        return quantityToOrder;
    }

    /**
     * Sets the value of the quantityToOrder property.
     * 
     * @param value
     *     allowed object is
     *     {@link Quantity }
     *     
     */
    public void setQuantityToOrder(Quantity value) {
        this.quantityToOrder = value;
    }

    /**
     * Gets the value of the vendorKey property.
     * 
     * @return
     *     possible object is
     *     {@link VendorKey }
     *     
     */
    public VendorKey getVendorKey() {
        return vendorKey;
    }

    /**
     * Sets the value of the vendorKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link VendorKey }
     *     
     */
    public void setVendorKey(VendorKey value) {
        this.vendorKey = value;
    }

}
