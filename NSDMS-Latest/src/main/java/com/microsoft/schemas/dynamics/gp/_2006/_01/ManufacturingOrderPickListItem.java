
package com.microsoft.schemas.dynamics.gp._2006._01;

import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for ManufacturingOrderPickListItem complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ManufacturingOrderPickListItem"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="CurrentReturnToStockQty" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}Quantity" minOccurs="0"/&gt;
 *         &lt;element name="ManufacturingOrderDocumentKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ManufacturingOrderDocumentKey" minOccurs="0"/&gt;
 *         &lt;element name="Sequence" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="OrderStatus" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ManufacturingOrderStatus"/&gt;
 *         &lt;element name="ParentPartNumber" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ItemKey" minOccurs="0"/&gt;
 *         &lt;element name="ItemKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ItemKey" minOccurs="0"/&gt;
 *         &lt;element name="PositionNumber" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="RoutingName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="RoutingSequence" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="MRPAmount" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="MRPAmount2" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="SuggestedQty" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}Quantity" minOccurs="0"/&gt;
 *         &lt;element name="DateResourcePlanIssued" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="DateAllocated" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="WorkCenter" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="VendorKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}VendorKey" minOccurs="0"/&gt;
 *         &lt;element name="VendorName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="IsBackFlushed" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="IsBackOrdered" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="IsAlternateItem" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="AlternateItemFor" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ItemKey" minOccurs="0"/&gt;
 *         &lt;element name="AlternateItemSequence" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="IsSubAssemblyItem" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="SubAssemblyItemFor" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ItemKey" minOccurs="0"/&gt;
 *         &lt;element name="SubAssemblyItemSequence" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="IsBOMApproved" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="IsBOMSingleLot" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="Location" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="IsAllocated" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="IsIssued" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="IsFloorStock" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="IsResourcePlanCalculated" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="IsPhantomItem" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="LastModifiedDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="AllocatedBy" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="IssuedBy" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="RefillCost" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="ReturnToStockQty" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}Quantity" minOccurs="0"/&gt;
 *         &lt;element name="ScrappedQty" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}Quantity" minOccurs="0"/&gt;
 *         &lt;element name="ActualConsumedQty" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}Quantity" minOccurs="0"/&gt;
 *         &lt;element name="POLine" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}PurchaseTransactionLineKey" minOccurs="0"/&gt;
 *         &lt;element name="AllowedQty" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}Quantity" minOccurs="0"/&gt;
 *         &lt;element name="CurrentConsumedQty" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}Quantity" minOccurs="0"/&gt;
 *         &lt;element name="CurrentScrappedQty" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}Quantity" minOccurs="0"/&gt;
 *         &lt;element name="IsOptionedItem" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="MarkdownAmt" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="BOMCategory" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}BOMCategory"/&gt;
 *         &lt;element name="BOMName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="Promotion" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="IsActualConsumedChecked" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="DateRequired" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="UofM" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="AllocatedQty" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}Quantity" minOccurs="0"/&gt;
 *         &lt;element name="UnitCost" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="NonInventoryCreditIndex" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="BaseUOMQty" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}Quantity" minOccurs="0"/&gt;
 *         &lt;element name="IssuedQty" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}Quantity" minOccurs="0"/&gt;
 *         &lt;element name="BackFlushedQty" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}Quantity" minOccurs="0"/&gt;
 *         &lt;element name="NonInventoryItemDescription" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="LinkToSequence" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="FixedQty" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}Quantity" minOccurs="0"/&gt;
 *         &lt;element name="LeadTimeOffset" type="{http://www.w3.org/2001/XMLSchema}decimal"/&gt;
 *         &lt;element name="OffsetFrom" type="{http://www.w3.org/2001/XMLSchema}decimal"/&gt;
 *         &lt;element name="BOMUserDef1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="BOMUserDef2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="ExplodedQty" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}Quantity" minOccurs="0"/&gt;
 *         &lt;element name="ManufacturingNote" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="BOMSequence" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="PositionNumber2" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ManufacturingOrderPickListItem", propOrder = {
    "currentReturnToStockQty",
    "manufacturingOrderDocumentKey",
    "sequence",
    "orderStatus",
    "parentPartNumber",
    "itemKey",
    "positionNumber",
    "routingName",
    "routingSequence",
    "mrpAmount",
    "mrpAmount2",
    "suggestedQty",
    "dateResourcePlanIssued",
    "dateAllocated",
    "workCenter",
    "vendorKey",
    "vendorName",
    "isBackFlushed",
    "isBackOrdered",
    "isAlternateItem",
    "alternateItemFor",
    "alternateItemSequence",
    "isSubAssemblyItem",
    "subAssemblyItemFor",
    "subAssemblyItemSequence",
    "isBOMApproved",
    "isBOMSingleLot",
    "location",
    "isAllocated",
    "isIssued",
    "isFloorStock",
    "isResourcePlanCalculated",
    "isPhantomItem",
    "lastModifiedDate",
    "allocatedBy",
    "issuedBy",
    "refillCost",
    "returnToStockQty",
    "scrappedQty",
    "actualConsumedQty",
    "poLine",
    "allowedQty",
    "currentConsumedQty",
    "currentScrappedQty",
    "isOptionedItem",
    "markdownAmt",
    "bomCategory",
    "bomName",
    "promotion",
    "isActualConsumedChecked",
    "dateRequired",
    "uofM",
    "allocatedQty",
    "unitCost",
    "nonInventoryCreditIndex",
    "baseUOMQty",
    "issuedQty",
    "backFlushedQty",
    "nonInventoryItemDescription",
    "linkToSequence",
    "fixedQty",
    "leadTimeOffset",
    "offsetFrom",
    "bomUserDef1",
    "bomUserDef2",
    "explodedQty",
    "manufacturingNote",
    "bomSequence",
    "positionNumber2"
})
public class ManufacturingOrderPickListItem {

    @XmlElement(name = "CurrentReturnToStockQty")
    protected Quantity currentReturnToStockQty;
    @XmlElement(name = "ManufacturingOrderDocumentKey")
    protected ManufacturingOrderDocumentKey manufacturingOrderDocumentKey;
    @XmlElement(name = "Sequence", required = true, type = Integer.class, nillable = true)
    protected Integer sequence;
    @XmlElement(name = "OrderStatus", required = true, nillable = true)
    @XmlSchemaType(name = "string")
    protected ManufacturingOrderStatus orderStatus;
    @XmlElement(name = "ParentPartNumber")
    protected ItemKey parentPartNumber;
    @XmlElement(name = "ItemKey")
    protected ItemKey itemKey;
    @XmlElement(name = "PositionNumber", required = true, type = Integer.class, nillable = true)
    protected Integer positionNumber;
    @XmlElement(name = "RoutingName")
    protected String routingName;
    @XmlElement(name = "RoutingSequence")
    protected String routingSequence;
    @XmlElement(name = "MRPAmount")
    protected MoneyAmount mrpAmount;
    @XmlElement(name = "MRPAmount2")
    protected MoneyAmount mrpAmount2;
    @XmlElement(name = "SuggestedQty")
    protected Quantity suggestedQty;
    @XmlElement(name = "DateResourcePlanIssued", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dateResourcePlanIssued;
    @XmlElement(name = "DateAllocated", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dateAllocated;
    @XmlElement(name = "WorkCenter")
    protected String workCenter;
    @XmlElement(name = "VendorKey")
    protected VendorKey vendorKey;
    @XmlElement(name = "VendorName")
    protected String vendorName;
    @XmlElement(name = "IsBackFlushed", required = true, type = Boolean.class, nillable = true)
    protected Boolean isBackFlushed;
    @XmlElement(name = "IsBackOrdered", required = true, type = Boolean.class, nillable = true)
    protected Boolean isBackOrdered;
    @XmlElement(name = "IsAlternateItem", required = true, type = Boolean.class, nillable = true)
    protected Boolean isAlternateItem;
    @XmlElement(name = "AlternateItemFor")
    protected ItemKey alternateItemFor;
    @XmlElement(name = "AlternateItemSequence", required = true, type = Integer.class, nillable = true)
    protected Integer alternateItemSequence;
    @XmlElement(name = "IsSubAssemblyItem", required = true, type = Boolean.class, nillable = true)
    protected Boolean isSubAssemblyItem;
    @XmlElement(name = "SubAssemblyItemFor")
    protected ItemKey subAssemblyItemFor;
    @XmlElement(name = "SubAssemblyItemSequence", required = true, type = Integer.class, nillable = true)
    protected Integer subAssemblyItemSequence;
    @XmlElement(name = "IsBOMApproved", required = true, type = Boolean.class, nillable = true)
    protected Boolean isBOMApproved;
    @XmlElement(name = "IsBOMSingleLot", required = true, type = Boolean.class, nillable = true)
    protected Boolean isBOMSingleLot;
    @XmlElement(name = "Location")
    protected String location;
    @XmlElement(name = "IsAllocated", required = true, type = Boolean.class, nillable = true)
    protected Boolean isAllocated;
    @XmlElement(name = "IsIssued", required = true, type = Boolean.class, nillable = true)
    protected Boolean isIssued;
    @XmlElement(name = "IsFloorStock", required = true, type = Boolean.class, nillable = true)
    protected Boolean isFloorStock;
    @XmlElement(name = "IsResourcePlanCalculated", required = true, type = Boolean.class, nillable = true)
    protected Boolean isResourcePlanCalculated;
    @XmlElement(name = "IsPhantomItem", required = true, type = Boolean.class, nillable = true)
    protected Boolean isPhantomItem;
    @XmlElement(name = "LastModifiedDate", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar lastModifiedDate;
    @XmlElement(name = "AllocatedBy")
    protected String allocatedBy;
    @XmlElement(name = "IssuedBy")
    protected String issuedBy;
    @XmlElement(name = "RefillCost")
    protected MoneyAmount refillCost;
    @XmlElement(name = "ReturnToStockQty")
    protected Quantity returnToStockQty;
    @XmlElement(name = "ScrappedQty")
    protected Quantity scrappedQty;
    @XmlElement(name = "ActualConsumedQty")
    protected Quantity actualConsumedQty;
    @XmlElement(name = "POLine")
    protected PurchaseTransactionLineKey poLine;
    @XmlElement(name = "AllowedQty")
    protected Quantity allowedQty;
    @XmlElement(name = "CurrentConsumedQty")
    protected Quantity currentConsumedQty;
    @XmlElement(name = "CurrentScrappedQty")
    protected Quantity currentScrappedQty;
    @XmlElement(name = "IsOptionedItem", required = true, type = Boolean.class, nillable = true)
    protected Boolean isOptionedItem;
    @XmlElement(name = "MarkdownAmt")
    protected MoneyAmount markdownAmt;
    @XmlElement(name = "BOMCategory", required = true, nillable = true)
    @XmlSchemaType(name = "string")
    protected BOMCategory bomCategory;
    @XmlElement(name = "BOMName")
    protected String bomName;
    @XmlElement(name = "Promotion")
    protected String promotion;
    @XmlElement(name = "IsActualConsumedChecked", required = true, type = Boolean.class, nillable = true)
    protected Boolean isActualConsumedChecked;
    @XmlElement(name = "DateRequired", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dateRequired;
    @XmlElement(name = "UofM")
    protected String uofM;
    @XmlElement(name = "AllocatedQty")
    protected Quantity allocatedQty;
    @XmlElement(name = "UnitCost")
    protected MoneyAmount unitCost;
    @XmlElement(name = "NonInventoryCreditIndex", required = true, type = Integer.class, nillable = true)
    protected Integer nonInventoryCreditIndex;
    @XmlElement(name = "BaseUOMQty")
    protected Quantity baseUOMQty;
    @XmlElement(name = "IssuedQty")
    protected Quantity issuedQty;
    @XmlElement(name = "BackFlushedQty")
    protected Quantity backFlushedQty;
    @XmlElement(name = "NonInventoryItemDescription")
    protected String nonInventoryItemDescription;
    @XmlElement(name = "LinkToSequence", required = true, type = Integer.class, nillable = true)
    protected Integer linkToSequence;
    @XmlElement(name = "FixedQty")
    protected Quantity fixedQty;
    @XmlElement(name = "LeadTimeOffset", required = true, nillable = true)
    protected BigDecimal leadTimeOffset;
    @XmlElement(name = "OffsetFrom", required = true, nillable = true)
    protected BigDecimal offsetFrom;
    @XmlElement(name = "BOMUserDef1")
    protected String bomUserDef1;
    @XmlElement(name = "BOMUserDef2")
    protected String bomUserDef2;
    @XmlElement(name = "ExplodedQty")
    protected Quantity explodedQty;
    @XmlElement(name = "ManufacturingNote")
    protected String manufacturingNote;
    @XmlElement(name = "BOMSequence", required = true, type = Integer.class, nillable = true)
    protected Integer bomSequence;
    @XmlElement(name = "PositionNumber2", required = true, type = Integer.class, nillable = true)
    protected Integer positionNumber2;

    /**
     * Gets the value of the currentReturnToStockQty property.
     * 
     * @return
     *     possible object is
     *     {@link Quantity }
     *     
     */
    public Quantity getCurrentReturnToStockQty() {
        return currentReturnToStockQty;
    }

    /**
     * Sets the value of the currentReturnToStockQty property.
     * 
     * @param value
     *     allowed object is
     *     {@link Quantity }
     *     
     */
    public void setCurrentReturnToStockQty(Quantity value) {
        this.currentReturnToStockQty = value;
    }

    /**
     * Gets the value of the manufacturingOrderDocumentKey property.
     * 
     * @return
     *     possible object is
     *     {@link ManufacturingOrderDocumentKey }
     *     
     */
    public ManufacturingOrderDocumentKey getManufacturingOrderDocumentKey() {
        return manufacturingOrderDocumentKey;
    }

    /**
     * Sets the value of the manufacturingOrderDocumentKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link ManufacturingOrderDocumentKey }
     *     
     */
    public void setManufacturingOrderDocumentKey(ManufacturingOrderDocumentKey value) {
        this.manufacturingOrderDocumentKey = value;
    }

    /**
     * Gets the value of the sequence property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getSequence() {
        return sequence;
    }

    /**
     * Sets the value of the sequence property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setSequence(Integer value) {
        this.sequence = value;
    }

    /**
     * Gets the value of the orderStatus property.
     * 
     * @return
     *     possible object is
     *     {@link ManufacturingOrderStatus }
     *     
     */
    public ManufacturingOrderStatus getOrderStatus() {
        return orderStatus;
    }

    /**
     * Sets the value of the orderStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link ManufacturingOrderStatus }
     *     
     */
    public void setOrderStatus(ManufacturingOrderStatus value) {
        this.orderStatus = value;
    }

    /**
     * Gets the value of the parentPartNumber property.
     * 
     * @return
     *     possible object is
     *     {@link ItemKey }
     *     
     */
    public ItemKey getParentPartNumber() {
        return parentPartNumber;
    }

    /**
     * Sets the value of the parentPartNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link ItemKey }
     *     
     */
    public void setParentPartNumber(ItemKey value) {
        this.parentPartNumber = value;
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
     * Gets the value of the positionNumber property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getPositionNumber() {
        return positionNumber;
    }

    /**
     * Sets the value of the positionNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setPositionNumber(Integer value) {
        this.positionNumber = value;
    }

    /**
     * Gets the value of the routingName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRoutingName() {
        return routingName;
    }

    /**
     * Sets the value of the routingName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRoutingName(String value) {
        this.routingName = value;
    }

    /**
     * Gets the value of the routingSequence property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRoutingSequence() {
        return routingSequence;
    }

    /**
     * Sets the value of the routingSequence property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRoutingSequence(String value) {
        this.routingSequence = value;
    }

    /**
     * Gets the value of the mrpAmount property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getMRPAmount() {
        return mrpAmount;
    }

    /**
     * Sets the value of the mrpAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setMRPAmount(MoneyAmount value) {
        this.mrpAmount = value;
    }

    /**
     * Gets the value of the mrpAmount2 property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getMRPAmount2() {
        return mrpAmount2;
    }

    /**
     * Sets the value of the mrpAmount2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setMRPAmount2(MoneyAmount value) {
        this.mrpAmount2 = value;
    }

    /**
     * Gets the value of the suggestedQty property.
     * 
     * @return
     *     possible object is
     *     {@link Quantity }
     *     
     */
    public Quantity getSuggestedQty() {
        return suggestedQty;
    }

    /**
     * Sets the value of the suggestedQty property.
     * 
     * @param value
     *     allowed object is
     *     {@link Quantity }
     *     
     */
    public void setSuggestedQty(Quantity value) {
        this.suggestedQty = value;
    }

    /**
     * Gets the value of the dateResourcePlanIssued property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDateResourcePlanIssued() {
        return dateResourcePlanIssued;
    }

    /**
     * Sets the value of the dateResourcePlanIssued property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDateResourcePlanIssued(XMLGregorianCalendar value) {
        this.dateResourcePlanIssued = value;
    }

    /**
     * Gets the value of the dateAllocated property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDateAllocated() {
        return dateAllocated;
    }

    /**
     * Sets the value of the dateAllocated property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDateAllocated(XMLGregorianCalendar value) {
        this.dateAllocated = value;
    }

    /**
     * Gets the value of the workCenter property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWorkCenter() {
        return workCenter;
    }

    /**
     * Sets the value of the workCenter property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWorkCenter(String value) {
        this.workCenter = value;
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

    /**
     * Gets the value of the vendorName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVendorName() {
        return vendorName;
    }

    /**
     * Sets the value of the vendorName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVendorName(String value) {
        this.vendorName = value;
    }

    /**
     * Gets the value of the isBackFlushed property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsBackFlushed() {
        return isBackFlushed;
    }

    /**
     * Sets the value of the isBackFlushed property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsBackFlushed(Boolean value) {
        this.isBackFlushed = value;
    }

    /**
     * Gets the value of the isBackOrdered property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsBackOrdered() {
        return isBackOrdered;
    }

    /**
     * Sets the value of the isBackOrdered property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsBackOrdered(Boolean value) {
        this.isBackOrdered = value;
    }

    /**
     * Gets the value of the isAlternateItem property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsAlternateItem() {
        return isAlternateItem;
    }

    /**
     * Sets the value of the isAlternateItem property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsAlternateItem(Boolean value) {
        this.isAlternateItem = value;
    }

    /**
     * Gets the value of the alternateItemFor property.
     * 
     * @return
     *     possible object is
     *     {@link ItemKey }
     *     
     */
    public ItemKey getAlternateItemFor() {
        return alternateItemFor;
    }

    /**
     * Sets the value of the alternateItemFor property.
     * 
     * @param value
     *     allowed object is
     *     {@link ItemKey }
     *     
     */
    public void setAlternateItemFor(ItemKey value) {
        this.alternateItemFor = value;
    }

    /**
     * Gets the value of the alternateItemSequence property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getAlternateItemSequence() {
        return alternateItemSequence;
    }

    /**
     * Sets the value of the alternateItemSequence property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setAlternateItemSequence(Integer value) {
        this.alternateItemSequence = value;
    }

    /**
     * Gets the value of the isSubAssemblyItem property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsSubAssemblyItem() {
        return isSubAssemblyItem;
    }

    /**
     * Sets the value of the isSubAssemblyItem property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsSubAssemblyItem(Boolean value) {
        this.isSubAssemblyItem = value;
    }

    /**
     * Gets the value of the subAssemblyItemFor property.
     * 
     * @return
     *     possible object is
     *     {@link ItemKey }
     *     
     */
    public ItemKey getSubAssemblyItemFor() {
        return subAssemblyItemFor;
    }

    /**
     * Sets the value of the subAssemblyItemFor property.
     * 
     * @param value
     *     allowed object is
     *     {@link ItemKey }
     *     
     */
    public void setSubAssemblyItemFor(ItemKey value) {
        this.subAssemblyItemFor = value;
    }

    /**
     * Gets the value of the subAssemblyItemSequence property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getSubAssemblyItemSequence() {
        return subAssemblyItemSequence;
    }

    /**
     * Sets the value of the subAssemblyItemSequence property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setSubAssemblyItemSequence(Integer value) {
        this.subAssemblyItemSequence = value;
    }

    /**
     * Gets the value of the isBOMApproved property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsBOMApproved() {
        return isBOMApproved;
    }

    /**
     * Sets the value of the isBOMApproved property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsBOMApproved(Boolean value) {
        this.isBOMApproved = value;
    }

    /**
     * Gets the value of the isBOMSingleLot property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsBOMSingleLot() {
        return isBOMSingleLot;
    }

    /**
     * Sets the value of the isBOMSingleLot property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsBOMSingleLot(Boolean value) {
        this.isBOMSingleLot = value;
    }

    /**
     * Gets the value of the location property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLocation() {
        return location;
    }

    /**
     * Sets the value of the location property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLocation(String value) {
        this.location = value;
    }

    /**
     * Gets the value of the isAllocated property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsAllocated() {
        return isAllocated;
    }

    /**
     * Sets the value of the isAllocated property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsAllocated(Boolean value) {
        this.isAllocated = value;
    }

    /**
     * Gets the value of the isIssued property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsIssued() {
        return isIssued;
    }

    /**
     * Sets the value of the isIssued property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsIssued(Boolean value) {
        this.isIssued = value;
    }

    /**
     * Gets the value of the isFloorStock property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsFloorStock() {
        return isFloorStock;
    }

    /**
     * Sets the value of the isFloorStock property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsFloorStock(Boolean value) {
        this.isFloorStock = value;
    }

    /**
     * Gets the value of the isResourcePlanCalculated property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsResourcePlanCalculated() {
        return isResourcePlanCalculated;
    }

    /**
     * Sets the value of the isResourcePlanCalculated property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsResourcePlanCalculated(Boolean value) {
        this.isResourcePlanCalculated = value;
    }

    /**
     * Gets the value of the isPhantomItem property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsPhantomItem() {
        return isPhantomItem;
    }

    /**
     * Sets the value of the isPhantomItem property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsPhantomItem(Boolean value) {
        this.isPhantomItem = value;
    }

    /**
     * Gets the value of the lastModifiedDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getLastModifiedDate() {
        return lastModifiedDate;
    }

    /**
     * Sets the value of the lastModifiedDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setLastModifiedDate(XMLGregorianCalendar value) {
        this.lastModifiedDate = value;
    }

    /**
     * Gets the value of the allocatedBy property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAllocatedBy() {
        return allocatedBy;
    }

    /**
     * Sets the value of the allocatedBy property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAllocatedBy(String value) {
        this.allocatedBy = value;
    }

    /**
     * Gets the value of the issuedBy property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIssuedBy() {
        return issuedBy;
    }

    /**
     * Sets the value of the issuedBy property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIssuedBy(String value) {
        this.issuedBy = value;
    }

    /**
     * Gets the value of the refillCost property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getRefillCost() {
        return refillCost;
    }

    /**
     * Sets the value of the refillCost property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setRefillCost(MoneyAmount value) {
        this.refillCost = value;
    }

    /**
     * Gets the value of the returnToStockQty property.
     * 
     * @return
     *     possible object is
     *     {@link Quantity }
     *     
     */
    public Quantity getReturnToStockQty() {
        return returnToStockQty;
    }

    /**
     * Sets the value of the returnToStockQty property.
     * 
     * @param value
     *     allowed object is
     *     {@link Quantity }
     *     
     */
    public void setReturnToStockQty(Quantity value) {
        this.returnToStockQty = value;
    }

    /**
     * Gets the value of the scrappedQty property.
     * 
     * @return
     *     possible object is
     *     {@link Quantity }
     *     
     */
    public Quantity getScrappedQty() {
        return scrappedQty;
    }

    /**
     * Sets the value of the scrappedQty property.
     * 
     * @param value
     *     allowed object is
     *     {@link Quantity }
     *     
     */
    public void setScrappedQty(Quantity value) {
        this.scrappedQty = value;
    }

    /**
     * Gets the value of the actualConsumedQty property.
     * 
     * @return
     *     possible object is
     *     {@link Quantity }
     *     
     */
    public Quantity getActualConsumedQty() {
        return actualConsumedQty;
    }

    /**
     * Sets the value of the actualConsumedQty property.
     * 
     * @param value
     *     allowed object is
     *     {@link Quantity }
     *     
     */
    public void setActualConsumedQty(Quantity value) {
        this.actualConsumedQty = value;
    }

    /**
     * Gets the value of the poLine property.
     * 
     * @return
     *     possible object is
     *     {@link PurchaseTransactionLineKey }
     *     
     */
    public PurchaseTransactionLineKey getPOLine() {
        return poLine;
    }

    /**
     * Sets the value of the poLine property.
     * 
     * @param value
     *     allowed object is
     *     {@link PurchaseTransactionLineKey }
     *     
     */
    public void setPOLine(PurchaseTransactionLineKey value) {
        this.poLine = value;
    }

    /**
     * Gets the value of the allowedQty property.
     * 
     * @return
     *     possible object is
     *     {@link Quantity }
     *     
     */
    public Quantity getAllowedQty() {
        return allowedQty;
    }

    /**
     * Sets the value of the allowedQty property.
     * 
     * @param value
     *     allowed object is
     *     {@link Quantity }
     *     
     */
    public void setAllowedQty(Quantity value) {
        this.allowedQty = value;
    }

    /**
     * Gets the value of the currentConsumedQty property.
     * 
     * @return
     *     possible object is
     *     {@link Quantity }
     *     
     */
    public Quantity getCurrentConsumedQty() {
        return currentConsumedQty;
    }

    /**
     * Sets the value of the currentConsumedQty property.
     * 
     * @param value
     *     allowed object is
     *     {@link Quantity }
     *     
     */
    public void setCurrentConsumedQty(Quantity value) {
        this.currentConsumedQty = value;
    }

    /**
     * Gets the value of the currentScrappedQty property.
     * 
     * @return
     *     possible object is
     *     {@link Quantity }
     *     
     */
    public Quantity getCurrentScrappedQty() {
        return currentScrappedQty;
    }

    /**
     * Sets the value of the currentScrappedQty property.
     * 
     * @param value
     *     allowed object is
     *     {@link Quantity }
     *     
     */
    public void setCurrentScrappedQty(Quantity value) {
        this.currentScrappedQty = value;
    }

    /**
     * Gets the value of the isOptionedItem property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsOptionedItem() {
        return isOptionedItem;
    }

    /**
     * Sets the value of the isOptionedItem property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsOptionedItem(Boolean value) {
        this.isOptionedItem = value;
    }

    /**
     * Gets the value of the markdownAmt property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getMarkdownAmt() {
        return markdownAmt;
    }

    /**
     * Sets the value of the markdownAmt property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setMarkdownAmt(MoneyAmount value) {
        this.markdownAmt = value;
    }

    /**
     * Gets the value of the bomCategory property.
     * 
     * @return
     *     possible object is
     *     {@link BOMCategory }
     *     
     */
    public BOMCategory getBOMCategory() {
        return bomCategory;
    }

    /**
     * Sets the value of the bomCategory property.
     * 
     * @param value
     *     allowed object is
     *     {@link BOMCategory }
     *     
     */
    public void setBOMCategory(BOMCategory value) {
        this.bomCategory = value;
    }

    /**
     * Gets the value of the bomName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBOMName() {
        return bomName;
    }

    /**
     * Sets the value of the bomName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBOMName(String value) {
        this.bomName = value;
    }

    /**
     * Gets the value of the promotion property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPromotion() {
        return promotion;
    }

    /**
     * Sets the value of the promotion property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPromotion(String value) {
        this.promotion = value;
    }

    /**
     * Gets the value of the isActualConsumedChecked property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsActualConsumedChecked() {
        return isActualConsumedChecked;
    }

    /**
     * Sets the value of the isActualConsumedChecked property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsActualConsumedChecked(Boolean value) {
        this.isActualConsumedChecked = value;
    }

    /**
     * Gets the value of the dateRequired property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDateRequired() {
        return dateRequired;
    }

    /**
     * Sets the value of the dateRequired property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDateRequired(XMLGregorianCalendar value) {
        this.dateRequired = value;
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
     * Gets the value of the allocatedQty property.
     * 
     * @return
     *     possible object is
     *     {@link Quantity }
     *     
     */
    public Quantity getAllocatedQty() {
        return allocatedQty;
    }

    /**
     * Sets the value of the allocatedQty property.
     * 
     * @param value
     *     allowed object is
     *     {@link Quantity }
     *     
     */
    public void setAllocatedQty(Quantity value) {
        this.allocatedQty = value;
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
     * Gets the value of the nonInventoryCreditIndex property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getNonInventoryCreditIndex() {
        return nonInventoryCreditIndex;
    }

    /**
     * Sets the value of the nonInventoryCreditIndex property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setNonInventoryCreditIndex(Integer value) {
        this.nonInventoryCreditIndex = value;
    }

    /**
     * Gets the value of the baseUOMQty property.
     * 
     * @return
     *     possible object is
     *     {@link Quantity }
     *     
     */
    public Quantity getBaseUOMQty() {
        return baseUOMQty;
    }

    /**
     * Sets the value of the baseUOMQty property.
     * 
     * @param value
     *     allowed object is
     *     {@link Quantity }
     *     
     */
    public void setBaseUOMQty(Quantity value) {
        this.baseUOMQty = value;
    }

    /**
     * Gets the value of the issuedQty property.
     * 
     * @return
     *     possible object is
     *     {@link Quantity }
     *     
     */
    public Quantity getIssuedQty() {
        return issuedQty;
    }

    /**
     * Sets the value of the issuedQty property.
     * 
     * @param value
     *     allowed object is
     *     {@link Quantity }
     *     
     */
    public void setIssuedQty(Quantity value) {
        this.issuedQty = value;
    }

    /**
     * Gets the value of the backFlushedQty property.
     * 
     * @return
     *     possible object is
     *     {@link Quantity }
     *     
     */
    public Quantity getBackFlushedQty() {
        return backFlushedQty;
    }

    /**
     * Sets the value of the backFlushedQty property.
     * 
     * @param value
     *     allowed object is
     *     {@link Quantity }
     *     
     */
    public void setBackFlushedQty(Quantity value) {
        this.backFlushedQty = value;
    }

    /**
     * Gets the value of the nonInventoryItemDescription property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNonInventoryItemDescription() {
        return nonInventoryItemDescription;
    }

    /**
     * Sets the value of the nonInventoryItemDescription property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNonInventoryItemDescription(String value) {
        this.nonInventoryItemDescription = value;
    }

    /**
     * Gets the value of the linkToSequence property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getLinkToSequence() {
        return linkToSequence;
    }

    /**
     * Sets the value of the linkToSequence property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setLinkToSequence(Integer value) {
        this.linkToSequence = value;
    }

    /**
     * Gets the value of the fixedQty property.
     * 
     * @return
     *     possible object is
     *     {@link Quantity }
     *     
     */
    public Quantity getFixedQty() {
        return fixedQty;
    }

    /**
     * Sets the value of the fixedQty property.
     * 
     * @param value
     *     allowed object is
     *     {@link Quantity }
     *     
     */
    public void setFixedQty(Quantity value) {
        this.fixedQty = value;
    }

    /**
     * Gets the value of the leadTimeOffset property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getLeadTimeOffset() {
        return leadTimeOffset;
    }

    /**
     * Sets the value of the leadTimeOffset property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setLeadTimeOffset(BigDecimal value) {
        this.leadTimeOffset = value;
    }

    /**
     * Gets the value of the offsetFrom property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getOffsetFrom() {
        return offsetFrom;
    }

    /**
     * Sets the value of the offsetFrom property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setOffsetFrom(BigDecimal value) {
        this.offsetFrom = value;
    }

    /**
     * Gets the value of the bomUserDef1 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBOMUserDef1() {
        return bomUserDef1;
    }

    /**
     * Sets the value of the bomUserDef1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBOMUserDef1(String value) {
        this.bomUserDef1 = value;
    }

    /**
     * Gets the value of the bomUserDef2 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBOMUserDef2() {
        return bomUserDef2;
    }

    /**
     * Sets the value of the bomUserDef2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBOMUserDef2(String value) {
        this.bomUserDef2 = value;
    }

    /**
     * Gets the value of the explodedQty property.
     * 
     * @return
     *     possible object is
     *     {@link Quantity }
     *     
     */
    public Quantity getExplodedQty() {
        return explodedQty;
    }

    /**
     * Sets the value of the explodedQty property.
     * 
     * @param value
     *     allowed object is
     *     {@link Quantity }
     *     
     */
    public void setExplodedQty(Quantity value) {
        this.explodedQty = value;
    }

    /**
     * Gets the value of the manufacturingNote property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getManufacturingNote() {
        return manufacturingNote;
    }

    /**
     * Sets the value of the manufacturingNote property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setManufacturingNote(String value) {
        this.manufacturingNote = value;
    }

    /**
     * Gets the value of the bomSequence property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getBOMSequence() {
        return bomSequence;
    }

    /**
     * Sets the value of the bomSequence property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setBOMSequence(Integer value) {
        this.bomSequence = value;
    }

    /**
     * Gets the value of the positionNumber2 property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getPositionNumber2() {
        return positionNumber2;
    }

    /**
     * Sets the value of the positionNumber2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setPositionNumber2(Integer value) {
        this.positionNumber2 = value;
    }

}
