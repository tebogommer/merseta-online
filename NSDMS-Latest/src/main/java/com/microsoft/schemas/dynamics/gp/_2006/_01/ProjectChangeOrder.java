
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for ProjectChangeOrder complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ProjectChangeOrder"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/gp/2006/01}BusinessObject"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Key" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ProjectChangeOrderKey" minOccurs="0"/&gt;
 *         &lt;element name="CustomerKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}CustomerKey" minOccurs="0"/&gt;
 *         &lt;element name="Date" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="Description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="CustomerChangeOrderNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="Type" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ProjectChangeOrderType"/&gt;
 *         &lt;element name="Status" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ProjectChangeOrderStatus"/&gt;
 *         &lt;element name="RequestedBy" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="EstimatedBy" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="RevisedBy" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="RevisersPosition" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="LastProcessedDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="ModifiedBy" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="PreviousContractBeginDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="ContractBeginDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="PreviousContractEndDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="ContractEndDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="ApprovedBy" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="ApproverPosition" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="ApprovalDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="TrackChangesToType" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ProjectChangeOrderTrackChangesToType"/&gt;
 *         &lt;element name="RevisedBudgetTotalAmount" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="RevisedFeeTotalAmount" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="TotalChangeOrderAmount" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="PreviousProjectAmount" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="VarianceProjectAmount" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="RevisedProjectAmount" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="ProjectAmount" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="ReasonForRevision" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="DocumentStatus" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ProjectChangeOrderDocumentStatus"/&gt;
 *         &lt;element name="SequenceNumber" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="TotalCost" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="TotalBilling" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="TotalQuantity" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}Quantity" minOccurs="0"/&gt;
 *         &lt;element name="VarianceTotalBilling" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="VarianceTotalCost" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="VarianceTotalQuantity" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}Quantity" minOccurs="0"/&gt;
 *         &lt;element name="Budgets" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ArrayOfProjectChangeOrderBudget" minOccurs="0"/&gt;
 *         &lt;element name="Fees" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ArrayOfProjectChangeOrderFee" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ProjectChangeOrder", propOrder = {
    "key",
    "customerKey",
    "date",
    "description",
    "customerChangeOrderNumber",
    "type",
    "status",
    "requestedBy",
    "estimatedBy",
    "revisedBy",
    "revisersPosition",
    "lastProcessedDate",
    "modifiedBy",
    "previousContractBeginDate",
    "contractBeginDate",
    "previousContractEndDate",
    "contractEndDate",
    "approvedBy",
    "approverPosition",
    "approvalDate",
    "trackChangesToType",
    "revisedBudgetTotalAmount",
    "revisedFeeTotalAmount",
    "totalChangeOrderAmount",
    "previousProjectAmount",
    "varianceProjectAmount",
    "revisedProjectAmount",
    "projectAmount",
    "reasonForRevision",
    "documentStatus",
    "sequenceNumber",
    "totalCost",
    "totalBilling",
    "totalQuantity",
    "varianceTotalBilling",
    "varianceTotalCost",
    "varianceTotalQuantity",
    "budgets",
    "fees"
})
public class ProjectChangeOrder
    extends BusinessObject
{

    @XmlElement(name = "Key")
    protected ProjectChangeOrderKey key;
    @XmlElement(name = "CustomerKey")
    protected CustomerKey customerKey;
    @XmlElement(name = "Date", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar date;
    @XmlElement(name = "Description")
    protected String description;
    @XmlElement(name = "CustomerChangeOrderNumber")
    protected String customerChangeOrderNumber;
    @XmlElement(name = "Type", required = true, nillable = true)
    @XmlSchemaType(name = "string")
    protected ProjectChangeOrderType type;
    @XmlElement(name = "Status", required = true, nillable = true)
    @XmlSchemaType(name = "string")
    protected ProjectChangeOrderStatus status;
    @XmlElement(name = "RequestedBy")
    protected String requestedBy;
    @XmlElement(name = "EstimatedBy")
    protected String estimatedBy;
    @XmlElement(name = "RevisedBy")
    protected String revisedBy;
    @XmlElement(name = "RevisersPosition")
    protected String revisersPosition;
    @XmlElement(name = "LastProcessedDate", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar lastProcessedDate;
    @XmlElement(name = "ModifiedBy")
    protected String modifiedBy;
    @XmlElement(name = "PreviousContractBeginDate", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar previousContractBeginDate;
    @XmlElement(name = "ContractBeginDate", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar contractBeginDate;
    @XmlElement(name = "PreviousContractEndDate", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar previousContractEndDate;
    @XmlElement(name = "ContractEndDate", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar contractEndDate;
    @XmlElement(name = "ApprovedBy")
    protected String approvedBy;
    @XmlElement(name = "ApproverPosition")
    protected String approverPosition;
    @XmlElement(name = "ApprovalDate", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar approvalDate;
    @XmlElement(name = "TrackChangesToType", required = true, nillable = true)
    @XmlSchemaType(name = "string")
    protected ProjectChangeOrderTrackChangesToType trackChangesToType;
    @XmlElement(name = "RevisedBudgetTotalAmount")
    protected MoneyAmount revisedBudgetTotalAmount;
    @XmlElement(name = "RevisedFeeTotalAmount")
    protected MoneyAmount revisedFeeTotalAmount;
    @XmlElement(name = "TotalChangeOrderAmount")
    protected MoneyAmount totalChangeOrderAmount;
    @XmlElement(name = "PreviousProjectAmount")
    protected MoneyAmount previousProjectAmount;
    @XmlElement(name = "VarianceProjectAmount")
    protected MoneyAmount varianceProjectAmount;
    @XmlElement(name = "RevisedProjectAmount")
    protected MoneyAmount revisedProjectAmount;
    @XmlElement(name = "ProjectAmount")
    protected MoneyAmount projectAmount;
    @XmlElement(name = "ReasonForRevision")
    protected String reasonForRevision;
    @XmlElement(name = "DocumentStatus", required = true, nillable = true)
    @XmlSchemaType(name = "string")
    protected ProjectChangeOrderDocumentStatus documentStatus;
    @XmlElement(name = "SequenceNumber", required = true, type = Integer.class, nillable = true)
    protected Integer sequenceNumber;
    @XmlElement(name = "TotalCost")
    protected MoneyAmount totalCost;
    @XmlElement(name = "TotalBilling")
    protected MoneyAmount totalBilling;
    @XmlElement(name = "TotalQuantity")
    protected Quantity totalQuantity;
    @XmlElement(name = "VarianceTotalBilling")
    protected MoneyAmount varianceTotalBilling;
    @XmlElement(name = "VarianceTotalCost")
    protected MoneyAmount varianceTotalCost;
    @XmlElement(name = "VarianceTotalQuantity")
    protected Quantity varianceTotalQuantity;
    @XmlElement(name = "Budgets")
    protected ArrayOfProjectChangeOrderBudget budgets;
    @XmlElement(name = "Fees")
    protected ArrayOfProjectChangeOrderFee fees;

    /**
     * Gets the value of the key property.
     * 
     * @return
     *     possible object is
     *     {@link ProjectChangeOrderKey }
     *     
     */
    public ProjectChangeOrderKey getKey() {
        return key;
    }

    /**
     * Sets the value of the key property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProjectChangeOrderKey }
     *     
     */
    public void setKey(ProjectChangeOrderKey value) {
        this.key = value;
    }

    /**
     * Gets the value of the customerKey property.
     * 
     * @return
     *     possible object is
     *     {@link CustomerKey }
     *     
     */
    public CustomerKey getCustomerKey() {
        return customerKey;
    }

    /**
     * Sets the value of the customerKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link CustomerKey }
     *     
     */
    public void setCustomerKey(CustomerKey value) {
        this.customerKey = value;
    }

    /**
     * Gets the value of the date property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDate() {
        return date;
    }

    /**
     * Sets the value of the date property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDate(XMLGregorianCalendar value) {
        this.date = value;
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
     * Gets the value of the customerChangeOrderNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCustomerChangeOrderNumber() {
        return customerChangeOrderNumber;
    }

    /**
     * Sets the value of the customerChangeOrderNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCustomerChangeOrderNumber(String value) {
        this.customerChangeOrderNumber = value;
    }

    /**
     * Gets the value of the type property.
     * 
     * @return
     *     possible object is
     *     {@link ProjectChangeOrderType }
     *     
     */
    public ProjectChangeOrderType getType() {
        return type;
    }

    /**
     * Sets the value of the type property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProjectChangeOrderType }
     *     
     */
    public void setType(ProjectChangeOrderType value) {
        this.type = value;
    }

    /**
     * Gets the value of the status property.
     * 
     * @return
     *     possible object is
     *     {@link ProjectChangeOrderStatus }
     *     
     */
    public ProjectChangeOrderStatus getStatus() {
        return status;
    }

    /**
     * Sets the value of the status property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProjectChangeOrderStatus }
     *     
     */
    public void setStatus(ProjectChangeOrderStatus value) {
        this.status = value;
    }

    /**
     * Gets the value of the requestedBy property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRequestedBy() {
        return requestedBy;
    }

    /**
     * Sets the value of the requestedBy property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRequestedBy(String value) {
        this.requestedBy = value;
    }

    /**
     * Gets the value of the estimatedBy property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEstimatedBy() {
        return estimatedBy;
    }

    /**
     * Sets the value of the estimatedBy property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEstimatedBy(String value) {
        this.estimatedBy = value;
    }

    /**
     * Gets the value of the revisedBy property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRevisedBy() {
        return revisedBy;
    }

    /**
     * Sets the value of the revisedBy property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRevisedBy(String value) {
        this.revisedBy = value;
    }

    /**
     * Gets the value of the revisersPosition property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRevisersPosition() {
        return revisersPosition;
    }

    /**
     * Sets the value of the revisersPosition property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRevisersPosition(String value) {
        this.revisersPosition = value;
    }

    /**
     * Gets the value of the lastProcessedDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getLastProcessedDate() {
        return lastProcessedDate;
    }

    /**
     * Sets the value of the lastProcessedDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setLastProcessedDate(XMLGregorianCalendar value) {
        this.lastProcessedDate = value;
    }

    /**
     * Gets the value of the modifiedBy property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getModifiedBy() {
        return modifiedBy;
    }

    /**
     * Sets the value of the modifiedBy property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setModifiedBy(String value) {
        this.modifiedBy = value;
    }

    /**
     * Gets the value of the previousContractBeginDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getPreviousContractBeginDate() {
        return previousContractBeginDate;
    }

    /**
     * Sets the value of the previousContractBeginDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setPreviousContractBeginDate(XMLGregorianCalendar value) {
        this.previousContractBeginDate = value;
    }

    /**
     * Gets the value of the contractBeginDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getContractBeginDate() {
        return contractBeginDate;
    }

    /**
     * Sets the value of the contractBeginDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setContractBeginDate(XMLGregorianCalendar value) {
        this.contractBeginDate = value;
    }

    /**
     * Gets the value of the previousContractEndDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getPreviousContractEndDate() {
        return previousContractEndDate;
    }

    /**
     * Sets the value of the previousContractEndDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setPreviousContractEndDate(XMLGregorianCalendar value) {
        this.previousContractEndDate = value;
    }

    /**
     * Gets the value of the contractEndDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getContractEndDate() {
        return contractEndDate;
    }

    /**
     * Sets the value of the contractEndDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setContractEndDate(XMLGregorianCalendar value) {
        this.contractEndDate = value;
    }

    /**
     * Gets the value of the approvedBy property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getApprovedBy() {
        return approvedBy;
    }

    /**
     * Sets the value of the approvedBy property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setApprovedBy(String value) {
        this.approvedBy = value;
    }

    /**
     * Gets the value of the approverPosition property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getApproverPosition() {
        return approverPosition;
    }

    /**
     * Sets the value of the approverPosition property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setApproverPosition(String value) {
        this.approverPosition = value;
    }

    /**
     * Gets the value of the approvalDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getApprovalDate() {
        return approvalDate;
    }

    /**
     * Sets the value of the approvalDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setApprovalDate(XMLGregorianCalendar value) {
        this.approvalDate = value;
    }

    /**
     * Gets the value of the trackChangesToType property.
     * 
     * @return
     *     possible object is
     *     {@link ProjectChangeOrderTrackChangesToType }
     *     
     */
    public ProjectChangeOrderTrackChangesToType getTrackChangesToType() {
        return trackChangesToType;
    }

    /**
     * Sets the value of the trackChangesToType property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProjectChangeOrderTrackChangesToType }
     *     
     */
    public void setTrackChangesToType(ProjectChangeOrderTrackChangesToType value) {
        this.trackChangesToType = value;
    }

    /**
     * Gets the value of the revisedBudgetTotalAmount property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getRevisedBudgetTotalAmount() {
        return revisedBudgetTotalAmount;
    }

    /**
     * Sets the value of the revisedBudgetTotalAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setRevisedBudgetTotalAmount(MoneyAmount value) {
        this.revisedBudgetTotalAmount = value;
    }

    /**
     * Gets the value of the revisedFeeTotalAmount property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getRevisedFeeTotalAmount() {
        return revisedFeeTotalAmount;
    }

    /**
     * Sets the value of the revisedFeeTotalAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setRevisedFeeTotalAmount(MoneyAmount value) {
        this.revisedFeeTotalAmount = value;
    }

    /**
     * Gets the value of the totalChangeOrderAmount property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getTotalChangeOrderAmount() {
        return totalChangeOrderAmount;
    }

    /**
     * Sets the value of the totalChangeOrderAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setTotalChangeOrderAmount(MoneyAmount value) {
        this.totalChangeOrderAmount = value;
    }

    /**
     * Gets the value of the previousProjectAmount property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getPreviousProjectAmount() {
        return previousProjectAmount;
    }

    /**
     * Sets the value of the previousProjectAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setPreviousProjectAmount(MoneyAmount value) {
        this.previousProjectAmount = value;
    }

    /**
     * Gets the value of the varianceProjectAmount property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getVarianceProjectAmount() {
        return varianceProjectAmount;
    }

    /**
     * Sets the value of the varianceProjectAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setVarianceProjectAmount(MoneyAmount value) {
        this.varianceProjectAmount = value;
    }

    /**
     * Gets the value of the revisedProjectAmount property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getRevisedProjectAmount() {
        return revisedProjectAmount;
    }

    /**
     * Sets the value of the revisedProjectAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setRevisedProjectAmount(MoneyAmount value) {
        this.revisedProjectAmount = value;
    }

    /**
     * Gets the value of the projectAmount property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getProjectAmount() {
        return projectAmount;
    }

    /**
     * Sets the value of the projectAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setProjectAmount(MoneyAmount value) {
        this.projectAmount = value;
    }

    /**
     * Gets the value of the reasonForRevision property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReasonForRevision() {
        return reasonForRevision;
    }

    /**
     * Sets the value of the reasonForRevision property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReasonForRevision(String value) {
        this.reasonForRevision = value;
    }

    /**
     * Gets the value of the documentStatus property.
     * 
     * @return
     *     possible object is
     *     {@link ProjectChangeOrderDocumentStatus }
     *     
     */
    public ProjectChangeOrderDocumentStatus getDocumentStatus() {
        return documentStatus;
    }

    /**
     * Sets the value of the documentStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProjectChangeOrderDocumentStatus }
     *     
     */
    public void setDocumentStatus(ProjectChangeOrderDocumentStatus value) {
        this.documentStatus = value;
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
     * Gets the value of the totalCost property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getTotalCost() {
        return totalCost;
    }

    /**
     * Sets the value of the totalCost property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setTotalCost(MoneyAmount value) {
        this.totalCost = value;
    }

    /**
     * Gets the value of the totalBilling property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getTotalBilling() {
        return totalBilling;
    }

    /**
     * Sets the value of the totalBilling property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setTotalBilling(MoneyAmount value) {
        this.totalBilling = value;
    }

    /**
     * Gets the value of the totalQuantity property.
     * 
     * @return
     *     possible object is
     *     {@link Quantity }
     *     
     */
    public Quantity getTotalQuantity() {
        return totalQuantity;
    }

    /**
     * Sets the value of the totalQuantity property.
     * 
     * @param value
     *     allowed object is
     *     {@link Quantity }
     *     
     */
    public void setTotalQuantity(Quantity value) {
        this.totalQuantity = value;
    }

    /**
     * Gets the value of the varianceTotalBilling property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getVarianceTotalBilling() {
        return varianceTotalBilling;
    }

    /**
     * Sets the value of the varianceTotalBilling property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setVarianceTotalBilling(MoneyAmount value) {
        this.varianceTotalBilling = value;
    }

    /**
     * Gets the value of the varianceTotalCost property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getVarianceTotalCost() {
        return varianceTotalCost;
    }

    /**
     * Sets the value of the varianceTotalCost property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setVarianceTotalCost(MoneyAmount value) {
        this.varianceTotalCost = value;
    }

    /**
     * Gets the value of the varianceTotalQuantity property.
     * 
     * @return
     *     possible object is
     *     {@link Quantity }
     *     
     */
    public Quantity getVarianceTotalQuantity() {
        return varianceTotalQuantity;
    }

    /**
     * Sets the value of the varianceTotalQuantity property.
     * 
     * @param value
     *     allowed object is
     *     {@link Quantity }
     *     
     */
    public void setVarianceTotalQuantity(Quantity value) {
        this.varianceTotalQuantity = value;
    }

    /**
     * Gets the value of the budgets property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfProjectChangeOrderBudget }
     *     
     */
    public ArrayOfProjectChangeOrderBudget getBudgets() {
        return budgets;
    }

    /**
     * Sets the value of the budgets property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfProjectChangeOrderBudget }
     *     
     */
    public void setBudgets(ArrayOfProjectChangeOrderBudget value) {
        this.budgets = value;
    }

    /**
     * Gets the value of the fees property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfProjectChangeOrderFee }
     *     
     */
    public ArrayOfProjectChangeOrderFee getFees() {
        return fees;
    }

    /**
     * Sets the value of the fees property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfProjectChangeOrderFee }
     *     
     */
    public void setFees(ArrayOfProjectChangeOrderFee value) {
        this.fees = value;
    }

}
