
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for SalesDocumentSummaryBase complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SalesDocumentSummaryBase"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="DocumentTypeKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}SalesDocumentTypeKey" minOccurs="0"/&gt;
 *         &lt;element name="Key" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}SalesDocumentKey" minOccurs="0"/&gt;
 *         &lt;element name="MasterNumber" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="CustomerKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}CustomerKey" minOccurs="0"/&gt;
 *         &lt;element name="CustomerPONumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="CustomerName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="SalespersonKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}SalespersonKey" minOccurs="0"/&gt;
 *         &lt;element name="Date" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="CurrencyKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}CurrencyKey" minOccurs="0"/&gt;
 *         &lt;element name="LineTotalAmount" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="TradeDiscountAmount" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="FreightAmount" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="MiscellaneousAmount" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="TaxAmount" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="TotalAmount" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="CreatedDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="ModifiedDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="LastModifiedDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="TransactionState" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}SalesTransactionState"/&gt;
 *         &lt;element name="BatchKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}BatchKey" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SalesDocumentSummaryBase", propOrder = {
    "documentTypeKey",
    "key",
    "masterNumber",
    "customerKey",
    "customerPONumber",
    "customerName",
    "salespersonKey",
    "date",
    "currencyKey",
    "lineTotalAmount",
    "tradeDiscountAmount",
    "freightAmount",
    "miscellaneousAmount",
    "taxAmount",
    "totalAmount",
    "createdDate",
    "modifiedDate",
    "lastModifiedDate",
    "transactionState",
    "batchKey"
})
@XmlSeeAlso({
    SalesDocumentSummary.class,
    SalesQuoteSummary.class,
    SalesReturnSummary.class,
    SalesInvoiceSummary.class,
    SalesOrderSummary.class,
    SalesFulfillmentOrderSummary.class,
    SalesBackorderSummary.class
})
public abstract class SalesDocumentSummaryBase {

    @XmlElement(name = "DocumentTypeKey")
    protected SalesDocumentTypeKey documentTypeKey;
    @XmlElement(name = "Key")
    protected SalesDocumentKey key;
    @XmlElement(name = "MasterNumber", required = true, type = Integer.class, nillable = true)
    protected Integer masterNumber;
    @XmlElement(name = "CustomerKey")
    protected CustomerKey customerKey;
    @XmlElement(name = "CustomerPONumber")
    protected String customerPONumber;
    @XmlElement(name = "CustomerName")
    protected String customerName;
    @XmlElement(name = "SalespersonKey")
    protected SalespersonKey salespersonKey;
    @XmlElement(name = "Date", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar date;
    @XmlElement(name = "CurrencyKey")
    protected CurrencyKey currencyKey;
    @XmlElement(name = "LineTotalAmount")
    protected MoneyAmount lineTotalAmount;
    @XmlElement(name = "TradeDiscountAmount")
    protected MoneyAmount tradeDiscountAmount;
    @XmlElement(name = "FreightAmount")
    protected MoneyAmount freightAmount;
    @XmlElement(name = "MiscellaneousAmount")
    protected MoneyAmount miscellaneousAmount;
    @XmlElement(name = "TaxAmount")
    protected MoneyAmount taxAmount;
    @XmlElement(name = "TotalAmount")
    protected MoneyAmount totalAmount;
    @XmlElement(name = "CreatedDate", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar createdDate;
    @XmlElement(name = "ModifiedDate", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar modifiedDate;
    @XmlElement(name = "LastModifiedDate", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar lastModifiedDate;
    @XmlElement(name = "TransactionState", required = true, nillable = true)
    @XmlSchemaType(name = "string")
    protected SalesTransactionState transactionState;
    @XmlElement(name = "BatchKey")
    protected BatchKey batchKey;

    /**
     * Gets the value of the documentTypeKey property.
     * 
     * @return
     *     possible object is
     *     {@link SalesDocumentTypeKey }
     *     
     */
    public SalesDocumentTypeKey getDocumentTypeKey() {
        return documentTypeKey;
    }

    /**
     * Sets the value of the documentTypeKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link SalesDocumentTypeKey }
     *     
     */
    public void setDocumentTypeKey(SalesDocumentTypeKey value) {
        this.documentTypeKey = value;
    }

    /**
     * Gets the value of the key property.
     * 
     * @return
     *     possible object is
     *     {@link SalesDocumentKey }
     *     
     */
    public SalesDocumentKey getKey() {
        return key;
    }

    /**
     * Sets the value of the key property.
     * 
     * @param value
     *     allowed object is
     *     {@link SalesDocumentKey }
     *     
     */
    public void setKey(SalesDocumentKey value) {
        this.key = value;
    }

    /**
     * Gets the value of the masterNumber property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getMasterNumber() {
        return masterNumber;
    }

    /**
     * Sets the value of the masterNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setMasterNumber(Integer value) {
        this.masterNumber = value;
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
     * Gets the value of the customerPONumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCustomerPONumber() {
        return customerPONumber;
    }

    /**
     * Sets the value of the customerPONumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCustomerPONumber(String value) {
        this.customerPONumber = value;
    }

    /**
     * Gets the value of the customerName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCustomerName() {
        return customerName;
    }

    /**
     * Sets the value of the customerName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCustomerName(String value) {
        this.customerName = value;
    }

    /**
     * Gets the value of the salespersonKey property.
     * 
     * @return
     *     possible object is
     *     {@link SalespersonKey }
     *     
     */
    public SalespersonKey getSalespersonKey() {
        return salespersonKey;
    }

    /**
     * Sets the value of the salespersonKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link SalespersonKey }
     *     
     */
    public void setSalespersonKey(SalespersonKey value) {
        this.salespersonKey = value;
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
     * Gets the value of the lineTotalAmount property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getLineTotalAmount() {
        return lineTotalAmount;
    }

    /**
     * Sets the value of the lineTotalAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setLineTotalAmount(MoneyAmount value) {
        this.lineTotalAmount = value;
    }

    /**
     * Gets the value of the tradeDiscountAmount property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getTradeDiscountAmount() {
        return tradeDiscountAmount;
    }

    /**
     * Sets the value of the tradeDiscountAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setTradeDiscountAmount(MoneyAmount value) {
        this.tradeDiscountAmount = value;
    }

    /**
     * Gets the value of the freightAmount property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getFreightAmount() {
        return freightAmount;
    }

    /**
     * Sets the value of the freightAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setFreightAmount(MoneyAmount value) {
        this.freightAmount = value;
    }

    /**
     * Gets the value of the miscellaneousAmount property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getMiscellaneousAmount() {
        return miscellaneousAmount;
    }

    /**
     * Sets the value of the miscellaneousAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setMiscellaneousAmount(MoneyAmount value) {
        this.miscellaneousAmount = value;
    }

    /**
     * Gets the value of the taxAmount property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getTaxAmount() {
        return taxAmount;
    }

    /**
     * Sets the value of the taxAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setTaxAmount(MoneyAmount value) {
        this.taxAmount = value;
    }

    /**
     * Gets the value of the totalAmount property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getTotalAmount() {
        return totalAmount;
    }

    /**
     * Sets the value of the totalAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setTotalAmount(MoneyAmount value) {
        this.totalAmount = value;
    }

    /**
     * Gets the value of the createdDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getCreatedDate() {
        return createdDate;
    }

    /**
     * Sets the value of the createdDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setCreatedDate(XMLGregorianCalendar value) {
        this.createdDate = value;
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
     * Gets the value of the transactionState property.
     * 
     * @return
     *     possible object is
     *     {@link SalesTransactionState }
     *     
     */
    public SalesTransactionState getTransactionState() {
        return transactionState;
    }

    /**
     * Sets the value of the transactionState property.
     * 
     * @param value
     *     allowed object is
     *     {@link SalesTransactionState }
     *     
     */
    public void setTransactionState(SalesTransactionState value) {
        this.transactionState = value;
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

}
