
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for ManufacturingOrderDocument complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ManufacturingOrderDocument"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/gp/2006/01}BusinessObject"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="ActualDemand" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}Quantity" minOccurs="0"/&gt;
 *         &lt;element name="ManufacturingOrderDocumentKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ManufacturingOrderDocumentKey" minOccurs="0"/&gt;
 *         &lt;element name="Description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="OrderStatus" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ManufacturingOrderStatus"/&gt;
 *         &lt;element name="ItemKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ItemKey" minOccurs="0"/&gt;
 *         &lt;element name="IsArchivedMO" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="BOMCategory" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}BOMCategory"/&gt;
 *         &lt;element name="RoutingName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="EndingQty" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}Quantity" minOccurs="0"/&gt;
 *         &lt;element name="StartingQty" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}Quantity" minOccurs="0"/&gt;
 *         &lt;element name="StartDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="EndDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="DrawFromSite" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="LastModifiedDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="ScheduleMethod" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}SchedulingMethod"/&gt;
 *         &lt;element name="PostToSite" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="LotNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="SchedulingPreference" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="PlanName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="Priority" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ManufacturingOrderPriority"/&gt;
 *         &lt;element name="Notes" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="OutSourced" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ManufacturingOrderOutSourced"/&gt;
 *         &lt;element name="ComponentCalculation" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ManufacturingOrderComponentCalculation"/&gt;
 *         &lt;element name="DateCompleted" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="PickNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="IsQuickMO" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="RoutingRevisionLevel" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="BOMRevisionLevel" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ManufacturingOrderDocument", propOrder = {
    "actualDemand",
    "manufacturingOrderDocumentKey",
    "description",
    "orderStatus",
    "itemKey",
    "isArchivedMO",
    "bomCategory",
    "routingName",
    "endingQty",
    "startingQty",
    "startDate",
    "endDate",
    "drawFromSite",
    "lastModifiedDate",
    "scheduleMethod",
    "postToSite",
    "lotNumber",
    "schedulingPreference",
    "planName",
    "priority",
    "notes",
    "outSourced",
    "componentCalculation",
    "dateCompleted",
    "pickNumber",
    "isQuickMO",
    "routingRevisionLevel",
    "bomRevisionLevel"
})
@XmlSeeAlso({
    ManufacturingOrder.class,
    VendorManufacturingOrder.class
})
public class ManufacturingOrderDocument
    extends BusinessObject
{

    @XmlElement(name = "ActualDemand")
    protected Quantity actualDemand;
    @XmlElement(name = "ManufacturingOrderDocumentKey")
    protected ManufacturingOrderDocumentKey manufacturingOrderDocumentKey;
    @XmlElement(name = "Description")
    protected String description;
    @XmlElement(name = "OrderStatus", required = true, nillable = true)
    @XmlSchemaType(name = "string")
    protected ManufacturingOrderStatus orderStatus;
    @XmlElement(name = "ItemKey")
    protected ItemKey itemKey;
    @XmlElement(name = "IsArchivedMO", required = true, type = Boolean.class, nillable = true)
    protected Boolean isArchivedMO;
    @XmlElement(name = "BOMCategory", required = true, nillable = true)
    @XmlSchemaType(name = "string")
    protected BOMCategory bomCategory;
    @XmlElement(name = "RoutingName")
    protected String routingName;
    @XmlElement(name = "EndingQty")
    protected Quantity endingQty;
    @XmlElement(name = "StartingQty")
    protected Quantity startingQty;
    @XmlElement(name = "StartDate", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar startDate;
    @XmlElement(name = "EndDate", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar endDate;
    @XmlElement(name = "DrawFromSite")
    protected String drawFromSite;
    @XmlElement(name = "LastModifiedDate", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar lastModifiedDate;
    @XmlElement(name = "ScheduleMethod", required = true, nillable = true)
    @XmlSchemaType(name = "string")
    protected SchedulingMethod scheduleMethod;
    @XmlElement(name = "PostToSite")
    protected String postToSite;
    @XmlElement(name = "LotNumber")
    protected String lotNumber;
    @XmlElement(name = "SchedulingPreference")
    protected String schedulingPreference;
    @XmlElement(name = "PlanName")
    protected String planName;
    @XmlElement(name = "Priority", required = true, nillable = true)
    @XmlSchemaType(name = "string")
    protected ManufacturingOrderPriority priority;
    @XmlElement(name = "Notes")
    protected String notes;
    @XmlElement(name = "OutSourced", required = true, nillable = true)
    @XmlSchemaType(name = "string")
    protected ManufacturingOrderOutSourced outSourced;
    @XmlElement(name = "ComponentCalculation", required = true, nillable = true)
    @XmlSchemaType(name = "string")
    protected ManufacturingOrderComponentCalculation componentCalculation;
    @XmlElement(name = "DateCompleted", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dateCompleted;
    @XmlElement(name = "PickNumber")
    protected String pickNumber;
    @XmlElement(name = "IsQuickMO", required = true, type = Boolean.class, nillable = true)
    protected Boolean isQuickMO;
    @XmlElement(name = "RoutingRevisionLevel")
    protected String routingRevisionLevel;
    @XmlElement(name = "BOMRevisionLevel")
    protected String bomRevisionLevel;

    /**
     * Gets the value of the actualDemand property.
     * 
     * @return
     *     possible object is
     *     {@link Quantity }
     *     
     */
    public Quantity getActualDemand() {
        return actualDemand;
    }

    /**
     * Sets the value of the actualDemand property.
     * 
     * @param value
     *     allowed object is
     *     {@link Quantity }
     *     
     */
    public void setActualDemand(Quantity value) {
        this.actualDemand = value;
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
     * Gets the value of the description property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the value of the description property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescription(String value) {
        this.description = value;
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
     * Gets the value of the isArchivedMO property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsArchivedMO() {
        return isArchivedMO;
    }

    /**
     * Sets the value of the isArchivedMO property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsArchivedMO(Boolean value) {
        this.isArchivedMO = value;
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
     * Gets the value of the endingQty property.
     * 
     * @return
     *     possible object is
     *     {@link Quantity }
     *     
     */
    public Quantity getEndingQty() {
        return endingQty;
    }

    /**
     * Sets the value of the endingQty property.
     * 
     * @param value
     *     allowed object is
     *     {@link Quantity }
     *     
     */
    public void setEndingQty(Quantity value) {
        this.endingQty = value;
    }

    /**
     * Gets the value of the startingQty property.
     * 
     * @return
     *     possible object is
     *     {@link Quantity }
     *     
     */
    public Quantity getStartingQty() {
        return startingQty;
    }

    /**
     * Sets the value of the startingQty property.
     * 
     * @param value
     *     allowed object is
     *     {@link Quantity }
     *     
     */
    public void setStartingQty(Quantity value) {
        this.startingQty = value;
    }

    /**
     * Gets the value of the startDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getStartDate() {
        return startDate;
    }

    /**
     * Sets the value of the startDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setStartDate(XMLGregorianCalendar value) {
        this.startDate = value;
    }

    /**
     * Gets the value of the endDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getEndDate() {
        return endDate;
    }

    /**
     * Sets the value of the endDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setEndDate(XMLGregorianCalendar value) {
        this.endDate = value;
    }

    /**
     * Gets the value of the drawFromSite property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDrawFromSite() {
        return drawFromSite;
    }

    /**
     * Sets the value of the drawFromSite property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDrawFromSite(String value) {
        this.drawFromSite = value;
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
     * Gets the value of the scheduleMethod property.
     * 
     * @return
     *     possible object is
     *     {@link SchedulingMethod }
     *     
     */
    public SchedulingMethod getScheduleMethod() {
        return scheduleMethod;
    }

    /**
     * Sets the value of the scheduleMethod property.
     * 
     * @param value
     *     allowed object is
     *     {@link SchedulingMethod }
     *     
     */
    public void setScheduleMethod(SchedulingMethod value) {
        this.scheduleMethod = value;
    }

    /**
     * Gets the value of the postToSite property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPostToSite() {
        return postToSite;
    }

    /**
     * Sets the value of the postToSite property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPostToSite(String value) {
        this.postToSite = value;
    }

    /**
     * Gets the value of the lotNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLotNumber() {
        return lotNumber;
    }

    /**
     * Sets the value of the lotNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLotNumber(String value) {
        this.lotNumber = value;
    }

    /**
     * Gets the value of the schedulingPreference property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSchedulingPreference() {
        return schedulingPreference;
    }

    /**
     * Sets the value of the schedulingPreference property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSchedulingPreference(String value) {
        this.schedulingPreference = value;
    }

    /**
     * Gets the value of the planName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPlanName() {
        return planName;
    }

    /**
     * Sets the value of the planName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPlanName(String value) {
        this.planName = value;
    }

    /**
     * Gets the value of the priority property.
     * 
     * @return
     *     possible object is
     *     {@link ManufacturingOrderPriority }
     *     
     */
    public ManufacturingOrderPriority getPriority() {
        return priority;
    }

    /**
     * Sets the value of the priority property.
     * 
     * @param value
     *     allowed object is
     *     {@link ManufacturingOrderPriority }
     *     
     */
    public void setPriority(ManufacturingOrderPriority value) {
        this.priority = value;
    }

    /**
     * Gets the value of the notes property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNotes() {
        return notes;
    }

    /**
     * Sets the value of the notes property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNotes(String value) {
        this.notes = value;
    }

    /**
     * Gets the value of the outSourced property.
     * 
     * @return
     *     possible object is
     *     {@link ManufacturingOrderOutSourced }
     *     
     */
    public ManufacturingOrderOutSourced getOutSourced() {
        return outSourced;
    }

    /**
     * Sets the value of the outSourced property.
     * 
     * @param value
     *     allowed object is
     *     {@link ManufacturingOrderOutSourced }
     *     
     */
    public void setOutSourced(ManufacturingOrderOutSourced value) {
        this.outSourced = value;
    }

    /**
     * Gets the value of the componentCalculation property.
     * 
     * @return
     *     possible object is
     *     {@link ManufacturingOrderComponentCalculation }
     *     
     */
    public ManufacturingOrderComponentCalculation getComponentCalculation() {
        return componentCalculation;
    }

    /**
     * Sets the value of the componentCalculation property.
     * 
     * @param value
     *     allowed object is
     *     {@link ManufacturingOrderComponentCalculation }
     *     
     */
    public void setComponentCalculation(ManufacturingOrderComponentCalculation value) {
        this.componentCalculation = value;
    }

    /**
     * Gets the value of the dateCompleted property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDateCompleted() {
        return dateCompleted;
    }

    /**
     * Sets the value of the dateCompleted property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDateCompleted(XMLGregorianCalendar value) {
        this.dateCompleted = value;
    }

    /**
     * Gets the value of the pickNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPickNumber() {
        return pickNumber;
    }

    /**
     * Sets the value of the pickNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPickNumber(String value) {
        this.pickNumber = value;
    }

    /**
     * Gets the value of the isQuickMO property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsQuickMO() {
        return isQuickMO;
    }

    /**
     * Sets the value of the isQuickMO property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsQuickMO(Boolean value) {
        this.isQuickMO = value;
    }

    /**
     * Gets the value of the routingRevisionLevel property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRoutingRevisionLevel() {
        return routingRevisionLevel;
    }

    /**
     * Sets the value of the routingRevisionLevel property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRoutingRevisionLevel(String value) {
        this.routingRevisionLevel = value;
    }

    /**
     * Gets the value of the bomRevisionLevel property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBOMRevisionLevel() {
        return bomRevisionLevel;
    }

    /**
     * Sets the value of the bomRevisionLevel property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBOMRevisionLevel(String value) {
        this.bomRevisionLevel = value;
    }

}
