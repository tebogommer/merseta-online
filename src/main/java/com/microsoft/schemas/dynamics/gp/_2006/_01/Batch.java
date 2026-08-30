
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for Batch complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Batch"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/gp/2006/01}BusinessObject"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Key" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}BatchKey" minOccurs="0"/&gt;
 *         &lt;element name="NumberOfTransactions" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="Frequency" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}PostingFrequency"/&gt;
 *         &lt;element name="RecurringLastPostedDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="NumberOfPostings" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="Comment" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="Total" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="CurrencyKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}CurrencyKey" minOccurs="0"/&gt;
 *         &lt;element name="ControlNumberOfTransactions" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="ControlTotal" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="GeneralLedgerPostedDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="BankAccountKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}BankAccountKey" minOccurs="0"/&gt;
 *         &lt;element name="WorkflowPriority" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}WorkflowPriority"/&gt;
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
@XmlType(name = "Batch", propOrder = {
    "key",
    "numberOfTransactions",
    "frequency",
    "recurringLastPostedDate",
    "numberOfPostings",
    "comment",
    "total",
    "currencyKey",
    "controlNumberOfTransactions",
    "controlTotal",
    "generalLedgerPostedDate",
    "bankAccountKey",
    "workflowPriority",
    "workflows"
})
public class Batch
    extends BusinessObject
{

    @XmlElement(name = "Key")
    protected BatchKey key;
    @XmlElement(name = "NumberOfTransactions", required = true, type = Integer.class, nillable = true)
    protected Integer numberOfTransactions;
    @XmlElement(name = "Frequency", required = true, nillable = true)
    @XmlSchemaType(name = "string")
    protected PostingFrequency frequency;
    @XmlElement(name = "RecurringLastPostedDate", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar recurringLastPostedDate;
    @XmlElement(name = "NumberOfPostings", required = true, type = Integer.class, nillable = true)
    protected Integer numberOfPostings;
    @XmlElement(name = "Comment")
    protected String comment;
    @XmlElement(name = "Total")
    protected MoneyAmount total;
    @XmlElement(name = "CurrencyKey")
    protected CurrencyKey currencyKey;
    @XmlElement(name = "ControlNumberOfTransactions", required = true, type = Integer.class, nillable = true)
    protected Integer controlNumberOfTransactions;
    @XmlElement(name = "ControlTotal")
    protected MoneyAmount controlTotal;
    @XmlElement(name = "GeneralLedgerPostedDate", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar generalLedgerPostedDate;
    @XmlElement(name = "BankAccountKey")
    protected BankAccountKey bankAccountKey;
    @XmlElement(name = "WorkflowPriority", required = true, nillable = true)
    @XmlSchemaType(name = "string")
    protected WorkflowPriority workflowPriority;
    @XmlElement(name = "Workflows")
    protected ArrayOfWorkflow workflows;

    /**
     * Gets the value of the key property.
     * 
     * @return
     *     possible object is
     *     {@link BatchKey }
     *     
     */
    public BatchKey getKey() {
        return key;
    }

    /**
     * Sets the value of the key property.
     * 
     * @param value
     *     allowed object is
     *     {@link BatchKey }
     *     
     */
    public void setKey(BatchKey value) {
        this.key = value;
    }

    /**
     * Gets the value of the numberOfTransactions property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getNumberOfTransactions() {
        return numberOfTransactions;
    }

    /**
     * Sets the value of the numberOfTransactions property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setNumberOfTransactions(Integer value) {
        this.numberOfTransactions = value;
    }

    /**
     * Gets the value of the frequency property.
     * 
     * @return
     *     possible object is
     *     {@link PostingFrequency }
     *     
     */
    public PostingFrequency getFrequency() {
        return frequency;
    }

    /**
     * Sets the value of the frequency property.
     * 
     * @param value
     *     allowed object is
     *     {@link PostingFrequency }
     *     
     */
    public void setFrequency(PostingFrequency value) {
        this.frequency = value;
    }

    /**
     * Gets the value of the recurringLastPostedDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getRecurringLastPostedDate() {
        return recurringLastPostedDate;
    }

    /**
     * Sets the value of the recurringLastPostedDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setRecurringLastPostedDate(XMLGregorianCalendar value) {
        this.recurringLastPostedDate = value;
    }

    /**
     * Gets the value of the numberOfPostings property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getNumberOfPostings() {
        return numberOfPostings;
    }

    /**
     * Sets the value of the numberOfPostings property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setNumberOfPostings(Integer value) {
        this.numberOfPostings = value;
    }

    /**
     * Gets the value of the comment property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getComment() {
        return comment;
    }

    /**
     * Sets the value of the comment property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setComment(String value) {
        this.comment = value;
    }

    /**
     * Gets the value of the total property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getTotal() {
        return total;
    }

    /**
     * Sets the value of the total property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setTotal(MoneyAmount value) {
        this.total = value;
    }

    /**
     * Gets the value of the currencyKey property.
     * 
     * @return
     *     possible object is
     *     {@link CurrencyKey }
     *     
     */
    public CurrencyKey getCurrencyKey() {
        return currencyKey;
    }

    /**
     * Sets the value of the currencyKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link CurrencyKey }
     *     
     */
    public void setCurrencyKey(CurrencyKey value) {
        this.currencyKey = value;
    }

    /**
     * Gets the value of the controlNumberOfTransactions property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getControlNumberOfTransactions() {
        return controlNumberOfTransactions;
    }

    /**
     * Sets the value of the controlNumberOfTransactions property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setControlNumberOfTransactions(Integer value) {
        this.controlNumberOfTransactions = value;
    }

    /**
     * Gets the value of the controlTotal property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getControlTotal() {
        return controlTotal;
    }

    /**
     * Sets the value of the controlTotal property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setControlTotal(MoneyAmount value) {
        this.controlTotal = value;
    }

    /**
     * Gets the value of the generalLedgerPostedDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getGeneralLedgerPostedDate() {
        return generalLedgerPostedDate;
    }

    /**
     * Sets the value of the generalLedgerPostedDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setGeneralLedgerPostedDate(XMLGregorianCalendar value) {
        this.generalLedgerPostedDate = value;
    }

    /**
     * Gets the value of the bankAccountKey property.
     * 
     * @return
     *     possible object is
     *     {@link BankAccountKey }
     *     
     */
    public BankAccountKey getBankAccountKey() {
        return bankAccountKey;
    }

    /**
     * Sets the value of the bankAccountKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link BankAccountKey }
     *     
     */
    public void setBankAccountKey(BankAccountKey value) {
        this.bankAccountKey = value;
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
