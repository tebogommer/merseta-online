
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for ProjectChangeOrderSummary complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ProjectChangeOrderSummary"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Key" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ProjectChangeOrderKey" minOccurs="0"/&gt;
 *         &lt;element name="Date" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="Status" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ProjectChangeOrderStatus"/&gt;
 *         &lt;element name="CustomerKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}CustomerKey" minOccurs="0"/&gt;
 *         &lt;element name="ApprovedBy" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="ApprovalDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="RevisedProjectAmount" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="RevisedBudgetTotalAmount" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="RevisedFeeTotalAmount" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="TotalChangeOrderAmount" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
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
@XmlType(name = "ProjectChangeOrderSummary", propOrder = {
    "key",
    "date",
    "status",
    "customerKey",
    "approvedBy",
    "approvalDate",
    "revisedProjectAmount",
    "revisedBudgetTotalAmount",
    "revisedFeeTotalAmount",
    "totalChangeOrderAmount",
    "totalCost"
})
public class ProjectChangeOrderSummary {

    @XmlElement(name = "Key")
    protected ProjectChangeOrderKey key;
    @XmlElement(name = "Date", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar date;
    @XmlElement(name = "Status", required = true, nillable = true)
    @XmlSchemaType(name = "string")
    protected ProjectChangeOrderStatus status;
    @XmlElement(name = "CustomerKey")
    protected CustomerKey customerKey;
    @XmlElement(name = "ApprovedBy")
    protected String approvedBy;
    @XmlElement(name = "ApprovalDate", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar approvalDate;
    @XmlElement(name = "RevisedProjectAmount")
    protected MoneyAmount revisedProjectAmount;
    @XmlElement(name = "RevisedBudgetTotalAmount")
    protected MoneyAmount revisedBudgetTotalAmount;
    @XmlElement(name = "RevisedFeeTotalAmount")
    protected MoneyAmount revisedFeeTotalAmount;
    @XmlElement(name = "TotalChangeOrderAmount")
    protected MoneyAmount totalChangeOrderAmount;
    @XmlElement(name = "TotalCost")
    protected MoneyAmount totalCost;

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
