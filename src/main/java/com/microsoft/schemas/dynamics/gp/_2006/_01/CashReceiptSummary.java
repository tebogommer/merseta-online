
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for CashReceiptSummary complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CashReceiptSummary"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Key" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ReceivablesDocumentKey" minOccurs="0"/&gt;
 *         &lt;element name="CustomerKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}CustomerKey" minOccurs="0"/&gt;
 *         &lt;element name="BatchKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}BatchKey" minOccurs="0"/&gt;
 *         &lt;element name="Date" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="CurrencyKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}CurrencyKey" minOccurs="0"/&gt;
 *         &lt;element name="BankAccountKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}BankAccountKey" minOccurs="0"/&gt;
 *         &lt;element name="Description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="Amount" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="TransactionState" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ReceivablesTransactionState"/&gt;
 *         &lt;element name="ModifiedDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CashReceiptSummary", propOrder = {
    "key",
    "customerKey",
    "batchKey",
    "date",
    "currencyKey",
    "bankAccountKey",
    "description",
    "amount",
    "transactionState",
    "modifiedDate"
})
public class CashReceiptSummary {

    @XmlElement(name = "Key")
    protected ReceivablesDocumentKey key;
    @XmlElement(name = "CustomerKey")
    protected CustomerKey customerKey;
    @XmlElement(name = "BatchKey")
    protected BatchKey batchKey;
    @XmlElement(name = "Date", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar date;
    @XmlElement(name = "CurrencyKey")
    protected CurrencyKey currencyKey;
    @XmlElement(name = "BankAccountKey")
    protected BankAccountKey bankAccountKey;
    @XmlElement(name = "Description")
    protected String description;
    @XmlElement(name = "Amount")
    protected MoneyAmount amount;
    @XmlElement(name = "TransactionState", required = true, nillable = true)
    @XmlSchemaType(name = "string")
    protected ReceivablesTransactionState transactionState;
    @XmlElement(name = "ModifiedDate", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar modifiedDate;

    /**
     * Gets the value of the key property.
     * 
     * @return
     *     possible object is
     *     {@link ReceivablesDocumentKey }
     *     
     */
    public ReceivablesDocumentKey getKey() {
        return key;
    }

    /**
     * Sets the value of the key property.
     * 
     * @param value
     *     allowed object is
     *     {@link ReceivablesDocumentKey }
     *     
     */
    public void setKey(ReceivablesDocumentKey value) {
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
     * Gets the value of the amount property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getAmount() {
        return amount;
    }

    /**
     * Sets the value of the amount property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setAmount(MoneyAmount value) {
        this.amount = value;
    }

    /**
     * Gets the value of the transactionState property.
     * 
     * @return
     *     possible object is
     *     {@link ReceivablesTransactionState }
     *     
     */
    public ReceivablesTransactionState getTransactionState() {
        return transactionState;
    }

    /**
     * Sets the value of the transactionState property.
     * 
     * @param value
     *     allowed object is
     *     {@link ReceivablesTransactionState }
     *     
     */
    public void setTransactionState(ReceivablesTransactionState value) {
        this.transactionState = value;
    }

    /**
     * Gets the value of the modifiedDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getModifiedDate() {
        return modifiedDate;
    }

    /**
     * Sets the value of the modifiedDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setModifiedDate(XMLGregorianCalendar value) {
        this.modifiedDate = value;
    }

}
