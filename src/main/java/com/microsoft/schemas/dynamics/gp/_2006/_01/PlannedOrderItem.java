
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for PlannedOrderItem complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PlannedOrderItem"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="RequiredByOpenMO" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}Quantity" minOccurs="0"/&gt;
 *         &lt;element name="DueDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="ItemKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ItemKey" minOccurs="0"/&gt;
 *         &lt;element name="LocationKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}WarehouseKey" minOccurs="0"/&gt;
 *         &lt;element name="PlannedOrderKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}PlannedOrderKey" minOccurs="0"/&gt;
 *         &lt;element name="MRPType" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}PlannedOrderMRPType"/&gt;
 *         &lt;element name="MRPParentType" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}PlannedOrderParentMRPType"/&gt;
 *         &lt;element name="SequenceNumber" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="PhantomParent" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="Status" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}PlannedOrderItemStatus"/&gt;
 *         &lt;element name="StatusDescription" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="AllocatedByOpenMO" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}Quantity" minOccurs="0"/&gt;
 *         &lt;element name="AllocatedByReleasedMO" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}Quantity" minOccurs="0"/&gt;
 *         &lt;element name="ConsumedByOpenMO" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}Quantity" minOccurs="0"/&gt;
 *         &lt;element name="ConsumedByReleasedMO" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}Quantity" minOccurs="0"/&gt;
 *         &lt;element name="RequiredByReleasedMO" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}Quantity" minOccurs="0"/&gt;
 *         &lt;element name="MoveIn" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="MoveOut" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="OriginalDueDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="Canceled" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="Exceptions" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}PlannedOrderItemExceptions" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PlannedOrderItem", propOrder = {
    "requiredByOpenMO",
    "dueDate",
    "itemKey",
    "locationKey",
    "plannedOrderKey",
    "mrpType",
    "mrpParentType",
    "sequenceNumber",
    "phantomParent",
    "status",
    "statusDescription",
    "allocatedByOpenMO",
    "allocatedByReleasedMO",
    "consumedByOpenMO",
    "consumedByReleasedMO",
    "requiredByReleasedMO",
    "moveIn",
    "moveOut",
    "originalDueDate",
    "canceled",
    "exceptions"
})
public class PlannedOrderItem {

    @XmlElement(name = "RequiredByOpenMO")
    protected Quantity requiredByOpenMO;
    @XmlElement(name = "DueDate", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dueDate;
    @XmlElement(name = "ItemKey")
    protected ItemKey itemKey;
    @XmlElement(name = "LocationKey")
    protected WarehouseKey locationKey;
    @XmlElement(name = "PlannedOrderKey")
    protected PlannedOrderKey plannedOrderKey;
    @XmlElement(name = "MRPType", required = true, nillable = true)
    @XmlSchemaType(name = "string")
    protected PlannedOrderMRPType mrpType;
    @XmlElement(name = "MRPParentType", required = true, nillable = true)
    @XmlSchemaType(name = "string")
    protected PlannedOrderParentMRPType mrpParentType;
    @XmlElement(name = "SequenceNumber", required = true, type = Integer.class, nillable = true)
    protected Integer sequenceNumber;
    @XmlElement(name = "PhantomParent")
    protected String phantomParent;
    @XmlElement(name = "Status", required = true, nillable = true)
    @XmlSchemaType(name = "string")
    protected PlannedOrderItemStatus status;
    @XmlElement(name = "StatusDescription")
    protected String statusDescription;
    @XmlElement(name = "AllocatedByOpenMO")
    protected Quantity allocatedByOpenMO;
    @XmlElement(name = "AllocatedByReleasedMO")
    protected Quantity allocatedByReleasedMO;
    @XmlElement(name = "ConsumedByOpenMO")
    protected Quantity consumedByOpenMO;
    @XmlElement(name = "ConsumedByReleasedMO")
    protected Quantity consumedByReleasedMO;
    @XmlElement(name = "RequiredByReleasedMO")
    protected Quantity requiredByReleasedMO;
    @XmlElement(name = "MoveIn", required = true, type = Boolean.class, nillable = true)
    protected Boolean moveIn;
    @XmlElement(name = "MoveOut", required = true, type = Boolean.class, nillable = true)
    protected Boolean moveOut;
    @XmlElement(name = "OriginalDueDate", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar originalDueDate;
    @XmlElement(name = "Canceled", required = true, type = Boolean.class, nillable = true)
    protected Boolean canceled;
    @XmlElement(name = "Exceptions")
    protected PlannedOrderItemExceptions exceptions;

    /**
     * Gets the value of the requiredByOpenMO property.
     * 
     * @return
     *     possible object is
     *     {@link Quantity }
     *     
     */
    public Quantity getRequiredByOpenMO() {
        return requiredByOpenMO;
    }

    /**
     * Sets the value of the requiredByOpenMO property.
     * 
     * @param value
     *     allowed object is
     *     {@link Quantity }
     *     
     */
    public void setRequiredByOpenMO(Quantity value) {
        this.requiredByOpenMO = value;
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
     * Gets the value of the mrpType property.
     * 
     * @return
     *     possible object is
     *     {@link PlannedOrderMRPType }
     *     
     */
    public PlannedOrderMRPType getMRPType() {
        return mrpType;
    }

    /**
     * Sets the value of the mrpType property.
     * 
     * @param value
     *     allowed object is
     *     {@link PlannedOrderMRPType }
     *     
     */
    public void setMRPType(PlannedOrderMRPType value) {
        this.mrpType = value;
    }

    /**
     * Gets the value of the mrpParentType property.
     * 
     * @return
     *     possible object is
     *     {@link PlannedOrderParentMRPType }
     *     
     */
    public PlannedOrderParentMRPType getMRPParentType() {
        return mrpParentType;
    }

    /**
     * Sets the value of the mrpParentType property.
     * 
     * @param value
     *     allowed object is
     *     {@link PlannedOrderParentMRPType }
     *     
     */
    public void setMRPParentType(PlannedOrderParentMRPType value) {
        this.mrpParentType = value;
    }

    /**
     * Gets the value of the sequenceNumber property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getSequenceNumber() {
        return sequenceNumber;
    }

    /**
     * Sets the value of the sequenceNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setSequenceNumber(Integer value) {
        this.sequenceNumber = value;
    }

    /**
     * Gets the value of the phantomParent property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPhantomParent() {
        return phantomParent;
    }

    /**
     * Sets the value of the phantomParent property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPhantomParent(String value) {
        this.phantomParent = value;
    }

    /**
     * Gets the value of the status property.
     * 
     * @return
     *     possible object is
     *     {@link PlannedOrderItemStatus }
     *     
     */
    public PlannedOrderItemStatus getStatus() {
        return status;
    }

    /**
     * Sets the value of the status property.
     * 
     * @param value
     *     allowed object is
     *     {@link PlannedOrderItemStatus }
     *     
     */
    public void setStatus(PlannedOrderItemStatus value) {
        this.status = value;
    }

    /**
     * Gets the value of the statusDescription property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatusDescription() {
        return statusDescription;
    }

    /**
     * Sets the value of the statusDescription property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatusDescription(String value) {
        this.statusDescription = value;
    }

    /**
     * Gets the value of the allocatedByOpenMO property.
     * 
     * @return
     *     possible object is
     *     {@link Quantity }
     *     
     */
    public Quantity getAllocatedByOpenMO() {
        return allocatedByOpenMO;
    }

    /**
     * Sets the value of the allocatedByOpenMO property.
     * 
     * @param value
     *     allowed object is
     *     {@link Quantity }
     *     
     */
    public void setAllocatedByOpenMO(Quantity value) {
        this.allocatedByOpenMO = value;
    }

    /**
     * Gets the value of the allocatedByReleasedMO property.
     * 
     * @return
     *     possible object is
     *     {@link Quantity }
     *     
     */
    public Quantity getAllocatedByReleasedMO() {
        return allocatedByReleasedMO;
    }

    /**
     * Sets the value of the allocatedByReleasedMO property.
     * 
     * @param value
     *     allowed object is
     *     {@link Quantity }
     *     
     */
    public void setAllocatedByReleasedMO(Quantity value) {
        this.allocatedByReleasedMO = value;
    }

    /**
     * Gets the value of the consumedByOpenMO property.
     * 
     * @return
     *     possible object is
     *     {@link Quantity }
     *     
     */
    public Quantity getConsumedByOpenMO() {
        return consumedByOpenMO;
    }

    /**
     * Sets the value of the consumedByOpenMO property.
     * 
     * @param value
     *     allowed object is
     *     {@link Quantity }
     *     
     */
    public void setConsumedByOpenMO(Quantity value) {
        this.consumedByOpenMO = value;
    }

    /**
     * Gets the value of the consumedByReleasedMO property.
     * 
     * @return
     *     possible object is
     *     {@link Quantity }
     *     
     */
    public Quantity getConsumedByReleasedMO() {
        return consumedByReleasedMO;
    }

    /**
     * Sets the value of the consumedByReleasedMO property.
     * 
     * @param value
     *     allowed object is
     *     {@link Quantity }
     *     
     */
    public void setConsumedByReleasedMO(Quantity value) {
        this.consumedByReleasedMO = value;
    }

    /**
     * Gets the value of the requiredByReleasedMO property.
     * 
     * @return
     *     possible object is
     *     {@link Quantity }
     *     
     */
    public Quantity getRequiredByReleasedMO() {
        return requiredByReleasedMO;
    }

    /**
     * Sets the value of the requiredByReleasedMO property.
     * 
     * @param value
     *     allowed object is
     *     {@link Quantity }
     *     
     */
    public void setRequiredByReleasedMO(Quantity value) {
        this.requiredByReleasedMO = value;
    }

    /**
     * Gets the value of the moveIn property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isMoveIn() {
        return moveIn;
    }

    /**
     * Sets the value of the moveIn property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setMoveIn(Boolean value) {
        this.moveIn = value;
    }

    /**
     * Gets the value of the moveOut property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isMoveOut() {
        return moveOut;
    }

    /**
     * Sets the value of the moveOut property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setMoveOut(Boolean value) {
        this.moveOut = value;
    }

    /**
     * Gets the value of the originalDueDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getOriginalDueDate() {
        return originalDueDate;
    }

    /**
     * Sets the value of the originalDueDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setOriginalDueDate(XMLGregorianCalendar value) {
        this.originalDueDate = value;
    }

    /**
     * Gets the value of the canceled property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isCanceled() {
        return canceled;
    }

    /**
     * Sets the value of the canceled property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setCanceled(Boolean value) {
        this.canceled = value;
    }

    /**
     * Gets the value of the exceptions property.
     * 
     * @return
     *     possible object is
     *     {@link PlannedOrderItemExceptions }
     *     
     */
    public PlannedOrderItemExceptions getExceptions() {
        return exceptions;
    }

    /**
     * Sets the value of the exceptions property.
     * 
     * @param value
     *     allowed object is
     *     {@link PlannedOrderItemExceptions }
     *     
     */
    public void setExceptions(PlannedOrderItemExceptions value) {
        this.exceptions = value;
    }

}
