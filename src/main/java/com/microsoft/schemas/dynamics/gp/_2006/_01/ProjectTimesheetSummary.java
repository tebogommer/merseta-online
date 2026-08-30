
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for ProjectTimesheetSummary complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ProjectTimesheetSummary"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Key" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ProjectTimesheetKey" minOccurs="0"/&gt;
 *         &lt;element name="TransactionState" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ProjectTransactionState"/&gt;
 *         &lt;element name="TransactionType" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ProjectTransactionType"/&gt;
 *         &lt;element name="Date" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="EmployeeKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}EmployeeKey" minOccurs="0"/&gt;
 *         &lt;element name="BatchKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}BatchKey" minOccurs="0"/&gt;
 *         &lt;element name="ReportingPeriod" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="ReportingDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="ReferenceDocumentNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="TotalCost" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ProjectTimesheetSummary", propOrder = {
    "key",
    "transactionState",
    "transactionType",
    "date",
    "employeeKey",
    "batchKey",
    "reportingPeriod",
    "reportingDate",
    "referenceDocumentNumber",
    "totalCost"
})
public class ProjectTimesheetSummary {

    @XmlElement(name = "Key")
    protected ProjectTimesheetKey key;
    @XmlElement(name = "TransactionState", required = true, nillable = true)
    @XmlSchemaType(name = "string")
    protected ProjectTransactionState transactionState;
    @XmlElement(name = "TransactionType", required = true, nillable = true)
    @XmlSchemaType(name = "string")
    protected ProjectTransactionType transactionType;
    @XmlElement(name = "Date", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar date;
    @XmlElement(name = "EmployeeKey")
    protected EmployeeKey employeeKey;
    @XmlElement(name = "BatchKey")
    protected BatchKey batchKey;
    @XmlElement(name = "ReportingPeriod", required = true, type = Integer.class, nillable = true)
    protected Integer reportingPeriod;
    @XmlElement(name = "ReportingDate", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar reportingDate;
    @XmlElement(name = "ReferenceDocumentNumber")
    protected String referenceDocumentNumber;
    @XmlElement(name = "TotalCost")
    protected MoneyAmount totalCost;

    /**
     * Gets the value of the key property.
     * 
     * @return
     *     possible object is
     *     {@link ProjectTimesheetKey }
     *     
     */
    public ProjectTimesheetKey getKey() {
        return key;
    }

    /**
     * Sets the value of the key property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProjectTimesheetKey }
     *     
     */
    public void setKey(ProjectTimesheetKey value) {
        this.key = value;
    }

    /**
     * Gets the value of the transactionState property.
     * 
     * @return
     *     possible object is
     *     {@link ProjectTransactionState }
     *     
     */
    public ProjectTransactionState getTransactionState() {
        return transactionState;
    }

    /**
     * Sets the value of the transactionState property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProjectTransactionState }
     *     
     */
    public void setTransactionState(ProjectTransactionState value) {
        this.transactionState = value;
    }

    /**
     * Gets the value of the transactionType property.
     * 
     * @return
     *     possible object is
     *     {@link ProjectTransactionType }
     *     
     */
    public ProjectTransactionType getTransactionType() {
        return transactionType;
    }

    /**
     * Sets the value of the transactionType property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProjectTransactionType }
     *     
     */
    public void setTransactionType(ProjectTransactionType value) {
        this.transactionType = value;
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
     * Gets the value of the employeeKey property.
     * 
     * @return
     *     possible object is
     *     {@link EmployeeKey }
     *     
     */
    public EmployeeKey getEmployeeKey() {
        return employeeKey;
    }

    /**
     * Sets the value of the employeeKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link EmployeeKey }
     *     
     */
    public void setEmployeeKey(EmployeeKey value) {
        this.employeeKey = value;
    }

    /**
     * Gets the value of the batchKey property.
     * 
     * @return
     *     possible object is
     *     {@link BatchKey }
     *     
     */
    public BatchKey getBatchKey() {
        return batchKey;
    }

    /**
     * Sets the value of the batchKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link BatchKey }
     *     
     */
    public void setBatchKey(BatchKey value) {
        this.batchKey = value;
    }

    /**
     * Gets the value of the reportingPeriod property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getReportingPeriod() {
        return reportingPeriod;
    }

    /**
     * Sets the value of the reportingPeriod property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setReportingPeriod(Integer value) {
        this.reportingPeriod = value;
    }

    /**
     * Gets the value of the reportingDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getReportingDate() {
        return reportingDate;
    }

    /**
     * Sets the value of the reportingDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setReportingDate(XMLGregorianCalendar value) {
        this.reportingDate = value;
    }

    /**
     * Gets the value of the referenceDocumentNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReferenceDocumentNumber() {
        return referenceDocumentNumber;
    }

    /**
     * Sets the value of the referenceDocumentNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReferenceDocumentNumber(String value) {
        this.referenceDocumentNumber = value;
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

}
