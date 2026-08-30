
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for SalesInvoice complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SalesInvoice"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/gp/2006/01}SalesDocument"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="PaymentAmount" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="DepositAmount" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="Terms" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}SalesTerms" minOccurs="0"/&gt;
 *         &lt;element name="GeneralLedgerPostingDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="PostedDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="PostedBy" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="IsDirectDebit" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="WorkflowPriority" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}WorkflowPriority"/&gt;
 *         &lt;element name="Lines" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ArrayOfSalesInvoiceLine" minOccurs="0"/&gt;
 *         &lt;element name="Payments" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ArrayOfSalesPayment" minOccurs="0"/&gt;
 *         &lt;element name="Distributions" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ArrayOfSalesDistribution" minOccurs="0"/&gt;
 *         &lt;element name="Workflows" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ArrayOfWorkflow" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SalesInvoice", propOrder = {
    "paymentAmount",
    "depositAmount",
    "terms",
    "generalLedgerPostingDate",
    "postedDate",
    "postedBy",
    "isDirectDebit",
    "workflowPriority",
    "lines",
    "payments",
    "distributions",
    "workflows"
})
public class SalesInvoice
    extends SalesDocument
{

    @XmlElement(name = "PaymentAmount")
    protected MoneyAmount paymentAmount;
    @XmlElement(name = "DepositAmount")
    protected MoneyAmount depositAmount;
    @XmlElement(name = "Terms")
    protected SalesTerms terms;
    @XmlElement(name = "GeneralLedgerPostingDate", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar generalLedgerPostingDate;
    @XmlElement(name = "PostedDate", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar postedDate;
    @XmlElement(name = "PostedBy")
    protected String postedBy;
    @XmlElement(name = "IsDirectDebit", required = true, type = Boolean.class, nillable = true)
    protected Boolean isDirectDebit;
    @XmlElement(name = "WorkflowPriority", required = true, nillable = true)
    @XmlSchemaType(name = "string")
    protected WorkflowPriority workflowPriority;
    @XmlElement(name = "Lines")
    protected ArrayOfSalesInvoiceLine lines;
    @XmlElement(name = "Payments")
    protected ArrayOfSalesPayment payments;
    @XmlElement(name = "Distributions")
    protected ArrayOfSalesDistribution distributions;
    @XmlElement(name = "Workflows")
    protected ArrayOfWorkflow workflows;

    /**
     * Gets the value of the paymentAmount property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getPaymentAmount() {
        return paymentAmount;
    }

    /**
     * Sets the value of the paymentAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setPaymentAmount(MoneyAmount value) {
        this.paymentAmount = value;
    }

    /**
     * Gets the value of the depositAmount property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getDepositAmount() {
        return depositAmount;
    }

    /**
     * Sets the value of the depositAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setDepositAmount(MoneyAmount value) {
        this.depositAmount = value;
    }

    /**
     * Gets the value of the terms property.
     * 
     * @return
     *     possible object is
     *     {@link SalesTerms }
     *     
     */
    public SalesTerms getTerms() {
        return terms;
    }

    /**
     * Sets the value of the terms property.
     * 
     * @param value
     *     allowed object is
     *     {@link SalesTerms }
     *     
     */
    public void setTerms(SalesTerms value) {
        this.terms = value;
    }

    /**
     * Gets the value of the generalLedgerPostingDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getGeneralLedgerPostingDate() {
        return generalLedgerPostingDate;
    }

    /**
     * Sets the value of the generalLedgerPostingDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setGeneralLedgerPostingDate(XMLGregorianCalendar value) {
        this.generalLedgerPostingDate = value;
    }

    /**
     * Gets the value of the postedDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getPostedDate() {
        return postedDate;
    }

    /**
     * Sets the value of the postedDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setPostedDate(XMLGregorianCalendar value) {
        this.postedDate = value;
    }

    /**
     * Gets the value of the postedBy property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPostedBy() {
        return postedBy;
    }

    /**
     * Sets the value of the postedBy property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPostedBy(String value) {
        this.postedBy = value;
    }

    /**
     * Gets the value of the isDirectDebit property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsDirectDebit() {
        return isDirectDebit;
    }

    /**
     * Sets the value of the isDirectDebit property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsDirectDebit(Boolean value) {
        this.isDirectDebit = value;
    }

    /**
     * Gets the value of the workflowPriority property.
     * 
     * @return
     *     possible object is
     *     {@link WorkflowPriority }
     *     
     */
    public WorkflowPriority getWorkflowPriority() {
        return workflowPriority;
    }

    /**
     * Sets the value of the workflowPriority property.
     * 
     * @param value
     *     allowed object is
     *     {@link WorkflowPriority }
     *     
     */
    public void setWorkflowPriority(WorkflowPriority value) {
        this.workflowPriority = value;
    }

    /**
     * Gets the value of the lines property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfSalesInvoiceLine }
     *     
     */
    public ArrayOfSalesInvoiceLine getLines() {
        return lines;
    }

    /**
     * Sets the value of the lines property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfSalesInvoiceLine }
     *     
     */
    public void setLines(ArrayOfSalesInvoiceLine value) {
        this.lines = value;
    }

    /**
     * Gets the value of the payments property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfSalesPayment }
     *     
     */
    public ArrayOfSalesPayment getPayments() {
        return payments;
    }

    /**
     * Sets the value of the payments property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfSalesPayment }
     *     
     */
    public void setPayments(ArrayOfSalesPayment value) {
        this.payments = value;
    }

    /**
     * Gets the value of the distributions property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfSalesDistribution }
     *     
     */
    public ArrayOfSalesDistribution getDistributions() {
        return distributions;
    }

    /**
     * Sets the value of the distributions property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfSalesDistribution }
     *     
     */
    public void setDistributions(ArrayOfSalesDistribution value) {
        this.distributions = value;
    }

    /**
     * Gets the value of the workflows property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfWorkflow }
     *     
     */
    public ArrayOfWorkflow getWorkflows() {
        return workflows;
    }

    /**
     * Sets the value of the workflows property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfWorkflow }
     *     
     */
    public void setWorkflows(ArrayOfWorkflow value) {
        this.workflows = value;
    }

}
