
package com.microsoft.schemas.dynamics.gp._2006._01;

import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for ManufacturingOrderRouteStep complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ManufacturingOrderRouteStep"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="ManufacturingOrderDocumentKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ManufacturingOrderDocumentKey" minOccurs="0"/&gt;
 *         &lt;element name="RouteSequenceNum" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="RouteSequenceType" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ManufacturingOrderRouteSequenceType"/&gt;
 *         &lt;element name="ClosedBy" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="DateClosed" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="RouteSeqDescription" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="ActualFinishDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="ActualStartDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="ScheduledFinishDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="ScheduledStartDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="WorkCenter" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="VendorKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}VendorKey" minOccurs="0"/&gt;
 *         &lt;element name="VendorName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="OperationCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="RouteWorkCenter" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="RouteMachineID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="MachineId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="SetupTime" type="{http://www.w3.org/2001/XMLSchema}decimal"/&gt;
 *         &lt;element name="MachineTime" type="{http://www.w3.org/2001/XMLSchema}decimal"/&gt;
 *         &lt;element name="RunTime" type="{http://www.w3.org/2001/XMLSchema}decimal"/&gt;
 *         &lt;element name="LaborTime" type="{http://www.w3.org/2001/XMLSchema}decimal"/&gt;
 *         &lt;element name="QueueTime" type="{http://www.w3.org/2001/XMLSchema}decimal"/&gt;
 *         &lt;element name="CycleTime" type="{http://www.w3.org/2001/XMLSchema}decimal"/&gt;
 *         &lt;element name="MoveTime" type="{http://www.w3.org/2001/XMLSchema}decimal"/&gt;
 *         &lt;element name="MRPAmount" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="LaborCode_1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="LaborCode_2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="NextRouteNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="RoutePartNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="PercentageComplete" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="Quantity" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}Quantity" minOccurs="0"/&gt;
 *         &lt;element name="DrawingNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="WaitHours" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="IsMultiEmployeeOperation" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="NumberOfEmployees" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="NumberOfCrews" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="IsLastSequenceofTheDay" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="UserDef_1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="UserDef_2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="DateCreated" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="LastModifiedDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="IsCapacityRequirementsPlanned" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="Notes" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="ManufacturingNote" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="Pieces" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}Quantity" minOccurs="0"/&gt;
 *         &lt;element name="Rejects" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}Quantity" minOccurs="0"/&gt;
 *         &lt;element name="IsAutoBackFlushLabor" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="IsAutoBackFlushMachine" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="NumberOfMachines" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="IsQualityAssuranceNeeded" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="POOffsetDays" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="WIPOutputPerMOStartQty" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}Quantity" minOccurs="0"/&gt;
 *         &lt;element name="IsPhantomItem" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="ServiceItem" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ManufacturingOrderServiceItem" minOccurs="0"/&gt;
 *         &lt;element name="IsDone" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ManufacturingOrderRouteStep", propOrder = {
    "manufacturingOrderDocumentKey",
    "routeSequenceNum",
    "routeSequenceType",
    "closedBy",
    "dateClosed",
    "routeSeqDescription",
    "actualFinishDate",
    "actualStartDate",
    "scheduledFinishDate",
    "scheduledStartDate",
    "workCenter",
    "vendorKey",
    "vendorName",
    "operationCode",
    "routeWorkCenter",
    "routeMachineID",
    "machineId",
    "setupTime",
    "machineTime",
    "runTime",
    "laborTime",
    "queueTime",
    "cycleTime",
    "moveTime",
    "mrpAmount",
    "laborCode1",
    "laborCode2",
    "nextRouteNumber",
    "routePartNumber",
    "percentageComplete",
    "quantity",
    "drawingNumber",
    "waitHours",
    "isMultiEmployeeOperation",
    "numberOfEmployees",
    "numberOfCrews",
    "isLastSequenceofTheDay",
    "userDef1",
    "userDef2",
    "dateCreated",
    "lastModifiedDate",
    "isCapacityRequirementsPlanned",
    "notes",
    "manufacturingNote",
    "pieces",
    "rejects",
    "isAutoBackFlushLabor",
    "isAutoBackFlushMachine",
    "numberOfMachines",
    "isQualityAssuranceNeeded",
    "poOffsetDays",
    "wipOutputPerMOStartQty",
    "isPhantomItem",
    "serviceItem",
    "isDone"
})
public class ManufacturingOrderRouteStep {

    @XmlElement(name = "ManufacturingOrderDocumentKey")
    protected ManufacturingOrderDocumentKey manufacturingOrderDocumentKey;
    @XmlElement(name = "RouteSequenceNum", required = true, type = Integer.class, nillable = true)
    protected Integer routeSequenceNum;
    @XmlElement(name = "RouteSequenceType", required = true, nillable = true)
    @XmlSchemaType(name = "string")
    protected ManufacturingOrderRouteSequenceType routeSequenceType;
    @XmlElement(name = "ClosedBy")
    protected String closedBy;
    @XmlElement(name = "DateClosed", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dateClosed;
    @XmlElement(name = "RouteSeqDescription")
    protected String routeSeqDescription;
    @XmlElement(name = "ActualFinishDate", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar actualFinishDate;
    @XmlElement(name = "ActualStartDate", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar actualStartDate;
    @XmlElement(name = "ScheduledFinishDate", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar scheduledFinishDate;
    @XmlElement(name = "ScheduledStartDate", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar scheduledStartDate;
    @XmlElement(name = "WorkCenter")
    protected String workCenter;
    @XmlElement(name = "VendorKey")
    protected VendorKey vendorKey;
    @XmlElement(name = "VendorName")
    protected String vendorName;
    @XmlElement(name = "OperationCode")
    protected String operationCode;
    @XmlElement(name = "RouteWorkCenter")
    protected String routeWorkCenter;
    @XmlElement(name = "RouteMachineID")
    protected String routeMachineID;
    @XmlElement(name = "MachineId")
    protected String machineId;
    @XmlElement(name = "SetupTime", required = true, nillable = true)
    protected BigDecimal setupTime;
    @XmlElement(name = "MachineTime", required = true, nillable = true)
    protected BigDecimal machineTime;
    @XmlElement(name = "RunTime", required = true, nillable = true)
    protected BigDecimal runTime;
    @XmlElement(name = "LaborTime", required = true, nillable = true)
    protected BigDecimal laborTime;
    @XmlElement(name = "QueueTime", required = true, nillable = true)
    protected BigDecimal queueTime;
    @XmlElement(name = "CycleTime", required = true, nillable = true)
    protected BigDecimal cycleTime;
    @XmlElement(name = "MoveTime", required = true, nillable = true)
    protected BigDecimal moveTime;
    @XmlElement(name = "MRPAmount")
    protected MoneyAmount mrpAmount;
    @XmlElement(name = "LaborCode_1")
    protected String laborCode1;
    @XmlElement(name = "LaborCode_2")
    protected String laborCode2;
    @XmlElement(name = "NextRouteNumber")
    protected String nextRouteNumber;
    @XmlElement(name = "RoutePartNumber")
    protected String routePartNumber;
    @XmlElement(name = "PercentageComplete", required = true, type = Integer.class, nillable = true)
    protected Integer percentageComplete;
    @XmlElement(name = "Quantity")
    protected Quantity quantity;
    @XmlElement(name = "DrawingNumber")
    protected String drawingNumber;
    @XmlElement(name = "WaitHours", required = true, type = Integer.class, nillable = true)
    protected Integer waitHours;
    @XmlElement(name = "IsMultiEmployeeOperation", required = true, type = Boolean.class, nillable = true)
    protected Boolean isMultiEmployeeOperation;
    @XmlElement(name = "NumberOfEmployees", required = true, type = Integer.class, nillable = true)
    protected Integer numberOfEmployees;
    @XmlElement(name = "NumberOfCrews", required = true, type = Integer.class, nillable = true)
    protected Integer numberOfCrews;
    @XmlElement(name = "IsLastSequenceofTheDay", required = true, type = Boolean.class, nillable = true)
    protected Boolean isLastSequenceofTheDay;
    @XmlElement(name = "UserDef_1")
    protected String userDef1;
    @XmlElement(name = "UserDef_2")
    protected String userDef2;
    @XmlElement(name = "DateCreated", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dateCreated;
    @XmlElement(name = "LastModifiedDate", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar lastModifiedDate;
    @XmlElement(name = "IsCapacityRequirementsPlanned", required = true, type = Boolean.class, nillable = true)
    protected Boolean isCapacityRequirementsPlanned;
    @XmlElement(name = "Notes")
    protected String notes;
    @XmlElement(name = "ManufacturingNote")
    protected String manufacturingNote;
    @XmlElement(name = "Pieces")
    protected Quantity pieces;
    @XmlElement(name = "Rejects")
    protected Quantity rejects;
    @XmlElement(name = "IsAutoBackFlushLabor", required = true, type = Boolean.class, nillable = true)
    protected Boolean isAutoBackFlushLabor;
    @XmlElement(name = "IsAutoBackFlushMachine", required = true, type = Boolean.class, nillable = true)
    protected Boolean isAutoBackFlushMachine;
    @XmlElement(name = "NumberOfMachines", required = true, type = Integer.class, nillable = true)
    protected Integer numberOfMachines;
    @XmlElement(name = "IsQualityAssuranceNeeded", required = true, type = Boolean.class, nillable = true)
    protected Boolean isQualityAssuranceNeeded;
    @XmlElement(name = "POOffsetDays", required = true, type = Integer.class, nillable = true)
    protected Integer poOffsetDays;
    @XmlElement(name = "WIPOutputPerMOStartQty")
    protected Quantity wipOutputPerMOStartQty;
    @XmlElement(name = "IsPhantomItem", required = true, type = Boolean.class, nillable = true)
    protected Boolean isPhantomItem;
    @XmlElement(name = "ServiceItem")
    protected ManufacturingOrderServiceItem serviceItem;
    @XmlElement(name = "IsDone", required = true, type = Boolean.class, nillable = true)
    protected Boolean isDone;

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
     * Gets the value of the routeSequenceNum property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getRouteSequenceNum() {
        return routeSequenceNum;
    }

    /**
     * Sets the value of the routeSequenceNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setRouteSequenceNum(Integer value) {
        this.routeSequenceNum = value;
    }

    /**
     * Gets the value of the routeSequenceType property.
     * 
     * @return
     *     possible object is
     *     {@link ManufacturingOrderRouteSequenceType }
     *     
     */
    public ManufacturingOrderRouteSequenceType getRouteSequenceType() {
        return routeSequenceType;
    }

    /**
     * Sets the value of the routeSequenceType property.
     * 
     * @param value
     *     allowed object is
     *     {@link ManufacturingOrderRouteSequenceType }
     *     
     */
    public void setRouteSequenceType(ManufacturingOrderRouteSequenceType value) {
        this.routeSequenceType = value;
    }

    /**
     * Gets the value of the closedBy property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getClosedBy() {
        return closedBy;
    }

    /**
     * Sets the value of the closedBy property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setClosedBy(String value) {
        this.closedBy = value;
    }

    /**
     * Gets the value of the dateClosed property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDateClosed() {
        return dateClosed;
    }

    /**
     * Sets the value of the dateClosed property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDateClosed(XMLGregorianCalendar value) {
        this.dateClosed = value;
    }

    /**
     * Gets the value of the routeSeqDescription property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRouteSeqDescription() {
        return routeSeqDescription;
    }

    /**
     * Sets the value of the routeSeqDescription property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRouteSeqDescription(String value) {
        this.routeSeqDescription = value;
    }

    /**
     * Gets the value of the actualFinishDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getActualFinishDate() {
        return actualFinishDate;
    }

    /**
     * Sets the value of the actualFinishDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setActualFinishDate(XMLGregorianCalendar value) {
        this.actualFinishDate = value;
    }

    /**
     * Gets the value of the actualStartDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getActualStartDate() {
        return actualStartDate;
    }

    /**
     * Sets the value of the actualStartDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setActualStartDate(XMLGregorianCalendar value) {
        this.actualStartDate = value;
    }

    /**
     * Gets the value of the scheduledFinishDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getScheduledFinishDate() {
        return scheduledFinishDate;
    }

    /**
     * Sets the value of the scheduledFinishDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setScheduledFinishDate(XMLGregorianCalendar value) {
        this.scheduledFinishDate = value;
    }

    /**
     * Gets the value of the scheduledStartDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getScheduledStartDate() {
        return scheduledStartDate;
    }

    /**
     * Sets the value of the scheduledStartDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setScheduledStartDate(XMLGregorianCalendar value) {
        this.scheduledStartDate = value;
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
     * Gets the value of the operationCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOperationCode() {
        return operationCode;
    }

    /**
     * Sets the value of the operationCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOperationCode(String value) {
        this.operationCode = value;
    }

    /**
     * Gets the value of the routeWorkCenter property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRouteWorkCenter() {
        return routeWorkCenter;
    }

    /**
     * Sets the value of the routeWorkCenter property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRouteWorkCenter(String value) {
        this.routeWorkCenter = value;
    }

    /**
     * Gets the value of the routeMachineID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRouteMachineID() {
        return routeMachineID;
    }

    /**
     * Sets the value of the routeMachineID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRouteMachineID(String value) {
        this.routeMachineID = value;
    }

    /**
     * Gets the value of the machineId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMachineId() {
        return machineId;
    }

    /**
     * Sets the value of the machineId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMachineId(String value) {
        this.machineId = value;
    }

    /**
     * Gets the value of the setupTime property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getSetupTime() {
        return setupTime;
    }

    /**
     * Sets the value of the setupTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setSetupTime(BigDecimal value) {
        this.setupTime = value;
    }

    /**
     * Gets the value of the machineTime property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getMachineTime() {
        return machineTime;
    }

    /**
     * Sets the value of the machineTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setMachineTime(BigDecimal value) {
        this.machineTime = value;
    }

    /**
     * Gets the value of the runTime property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getRunTime() {
        return runTime;
    }

    /**
     * Sets the value of the runTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setRunTime(BigDecimal value) {
        this.runTime = value;
    }

    /**
     * Gets the value of the laborTime property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getLaborTime() {
        return laborTime;
    }

    /**
     * Sets the value of the laborTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setLaborTime(BigDecimal value) {
        this.laborTime = value;
    }

    /**
     * Gets the value of the queueTime property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getQueueTime() {
        return queueTime;
    }

    /**
     * Sets the value of the queueTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setQueueTime(BigDecimal value) {
        this.queueTime = value;
    }

    /**
     * Gets the value of the cycleTime property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getCycleTime() {
        return cycleTime;
    }

    /**
     * Sets the value of the cycleTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setCycleTime(BigDecimal value) {
        this.cycleTime = value;
    }

    /**
     * Gets the value of the moveTime property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getMoveTime() {
        return moveTime;
    }

    /**
     * Sets the value of the moveTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setMoveTime(BigDecimal value) {
        this.moveTime = value;
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
     * Gets the value of the laborCode1 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLaborCode1() {
        return laborCode1;
    }

    /**
     * Sets the value of the laborCode1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLaborCode1(String value) {
        this.laborCode1 = value;
    }

    /**
     * Gets the value of the laborCode2 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLaborCode2() {
        return laborCode2;
    }

    /**
     * Sets the value of the laborCode2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLaborCode2(String value) {
        this.laborCode2 = value;
    }

    /**
     * Gets the value of the nextRouteNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNextRouteNumber() {
        return nextRouteNumber;
    }

    /**
     * Sets the value of the nextRouteNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNextRouteNumber(String value) {
        this.nextRouteNumber = value;
    }

    /**
     * Gets the value of the routePartNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRoutePartNumber() {
        return routePartNumber;
    }

    /**
     * Sets the value of the routePartNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRoutePartNumber(String value) {
        this.routePartNumber = value;
    }

    /**
     * Gets the value of the percentageComplete property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getPercentageComplete() {
        return percentageComplete;
    }

    /**
     * Sets the value of the percentageComplete property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setPercentageComplete(Integer value) {
        this.percentageComplete = value;
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
     * Gets the value of the drawingNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDrawingNumber() {
        return drawingNumber;
    }

    /**
     * Sets the value of the drawingNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDrawingNumber(String value) {
        this.drawingNumber = value;
    }

    /**
     * Gets the value of the waitHours property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getWaitHours() {
        return waitHours;
    }

    /**
     * Sets the value of the waitHours property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setWaitHours(Integer value) {
        this.waitHours = value;
    }

    /**
     * Gets the value of the isMultiEmployeeOperation property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsMultiEmployeeOperation() {
        return isMultiEmployeeOperation;
    }

    /**
     * Sets the value of the isMultiEmployeeOperation property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsMultiEmployeeOperation(Boolean value) {
        this.isMultiEmployeeOperation = value;
    }

    /**
     * Gets the value of the numberOfEmployees property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getNumberOfEmployees() {
        return numberOfEmployees;
    }

    /**
     * Sets the value of the numberOfEmployees property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setNumberOfEmployees(Integer value) {
        this.numberOfEmployees = value;
    }

    /**
     * Gets the value of the numberOfCrews property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getNumberOfCrews() {
        return numberOfCrews;
    }

    /**
     * Sets the value of the numberOfCrews property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setNumberOfCrews(Integer value) {
        this.numberOfCrews = value;
    }

    /**
     * Gets the value of the isLastSequenceofTheDay property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsLastSequenceofTheDay() {
        return isLastSequenceofTheDay;
    }

    /**
     * Sets the value of the isLastSequenceofTheDay property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsLastSequenceofTheDay(Boolean value) {
        this.isLastSequenceofTheDay = value;
    }

    /**
     * Gets the value of the userDef1 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUserDef1() {
        return userDef1;
    }

    /**
     * Sets the value of the userDef1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUserDef1(String value) {
        this.userDef1 = value;
    }

    /**
     * Gets the value of the userDef2 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUserDef2() {
        return userDef2;
    }

    /**
     * Sets the value of the userDef2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUserDef2(String value) {
        this.userDef2 = value;
    }

    /**
     * Gets the value of the dateCreated property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDateCreated() {
        return dateCreated;
    }

    /**
     * Sets the value of the dateCreated property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDateCreated(XMLGregorianCalendar value) {
        this.dateCreated = value;
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
     * Gets the value of the isCapacityRequirementsPlanned property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsCapacityRequirementsPlanned() {
        return isCapacityRequirementsPlanned;
    }

    /**
     * Sets the value of the isCapacityRequirementsPlanned property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsCapacityRequirementsPlanned(Boolean value) {
        this.isCapacityRequirementsPlanned = value;
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
     * Gets the value of the pieces property.
     * 
     * @return
     *     possible object is
     *     {@link Quantity }
     *     
     */
    public Quantity getPieces() {
        return pieces;
    }

    /**
     * Sets the value of the pieces property.
     * 
     * @param value
     *     allowed object is
     *     {@link Quantity }
     *     
     */
    public void setPieces(Quantity value) {
        this.pieces = value;
    }

    /**
     * Gets the value of the rejects property.
     * 
     * @return
     *     possible object is
     *     {@link Quantity }
     *     
     */
    public Quantity getRejects() {
        return rejects;
    }

    /**
     * Sets the value of the rejects property.
     * 
     * @param value
     *     allowed object is
     *     {@link Quantity }
     *     
     */
    public void setRejects(Quantity value) {
        this.rejects = value;
    }

    /**
     * Gets the value of the isAutoBackFlushLabor property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsAutoBackFlushLabor() {
        return isAutoBackFlushLabor;
    }

    /**
     * Sets the value of the isAutoBackFlushLabor property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsAutoBackFlushLabor(Boolean value) {
        this.isAutoBackFlushLabor = value;
    }

    /**
     * Gets the value of the isAutoBackFlushMachine property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsAutoBackFlushMachine() {
        return isAutoBackFlushMachine;
    }

    /**
     * Sets the value of the isAutoBackFlushMachine property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsAutoBackFlushMachine(Boolean value) {
        this.isAutoBackFlushMachine = value;
    }

    /**
     * Gets the value of the numberOfMachines property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getNumberOfMachines() {
        return numberOfMachines;
    }

    /**
     * Sets the value of the numberOfMachines property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setNumberOfMachines(Integer value) {
        this.numberOfMachines = value;
    }

    /**
     * Gets the value of the isQualityAssuranceNeeded property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsQualityAssuranceNeeded() {
        return isQualityAssuranceNeeded;
    }

    /**
     * Sets the value of the isQualityAssuranceNeeded property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsQualityAssuranceNeeded(Boolean value) {
        this.isQualityAssuranceNeeded = value;
    }

    /**
     * Gets the value of the poOffsetDays property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getPOOffsetDays() {
        return poOffsetDays;
    }

    /**
     * Sets the value of the poOffsetDays property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setPOOffsetDays(Integer value) {
        this.poOffsetDays = value;
    }

    /**
     * Gets the value of the wipOutputPerMOStartQty property.
     * 
     * @return
     *     possible object is
     *     {@link Quantity }
     *     
     */
    public Quantity getWIPOutputPerMOStartQty() {
        return wipOutputPerMOStartQty;
    }

    /**
     * Sets the value of the wipOutputPerMOStartQty property.
     * 
     * @param value
     *     allowed object is
     *     {@link Quantity }
     *     
     */
    public void setWIPOutputPerMOStartQty(Quantity value) {
        this.wipOutputPerMOStartQty = value;
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
     * Gets the value of the serviceItem property.
     * 
     * @return
     *     possible object is
     *     {@link ManufacturingOrderServiceItem }
     *     
     */
    public ManufacturingOrderServiceItem getServiceItem() {
        return serviceItem;
    }

    /**
     * Sets the value of the serviceItem property.
     * 
     * @param value
     *     allowed object is
     *     {@link ManufacturingOrderServiceItem }
     *     
     */
    public void setServiceItem(ManufacturingOrderServiceItem value) {
        this.serviceItem = value;
    }

    /**
     * Gets the value of the isDone property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsDone() {
        return isDone;
    }

    /**
     * Sets the value of the isDone property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsDone(Boolean value) {
        this.isDone = value;
    }

}
