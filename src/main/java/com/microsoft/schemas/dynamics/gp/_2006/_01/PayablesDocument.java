
package com.microsoft.schemas.dynamics.gp._2006._01;

import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for PayablesDocument complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PayablesDocument"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/gp/2006/01}BusinessObject"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Key" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}PayablesDocumentKey" minOccurs="0"/&gt;
 *         &lt;element name="VendorDocumentNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="DocumentAmount" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="Date" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="GeneralLedgerPostingDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="PurchasesAmount" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="Description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="TradeDiscountAmount" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="TaxAmount" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="FreightAmount" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="Amount1099" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="MiscellaneousAmount" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="PONumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="ExchangeRate" type="{http://www.w3.org/2001/XMLSchema}decimal"/&gt;
 *         &lt;element name="ExchangeDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="Type" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}PayablesDocumentType"/&gt;
 *         &lt;element name="TransactionState" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}PayablesTransactionState"/&gt;
 *         &lt;element name="VendorName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="ChargeAmount" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="ModifiedBy" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="ModifiedDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="PostedBy" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="PostedDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="TaxDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="AuditTrailCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="IsVoided" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="IsIntercompanyTransaction" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="HasIntercompanyDistributions" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="BatchKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}BatchKey" minOccurs="0"/&gt;
 *         &lt;element name="VendorKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}VendorKey" minOccurs="0"/&gt;
 *         &lt;element name="AddressKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}AddressKey" minOccurs="0"/&gt;
 *         &lt;element name="RemitToAddressKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}AddressKey" minOccurs="0"/&gt;
 *         &lt;element name="TaxScheduleKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}TaxScheduleKey" minOccurs="0"/&gt;
 *         &lt;element name="ShippingMethodKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ShippingMethodKey" minOccurs="0"/&gt;
 *         &lt;element name="PurchaseTaxScheduleKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}TaxScheduleKey" minOccurs="0"/&gt;
 *         &lt;element name="FreightTaxScheduleKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}TaxScheduleKey" minOccurs="0"/&gt;
 *         &lt;element name="MiscellaneousTaxScheduleKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}TaxScheduleKey" minOccurs="0"/&gt;
 *         &lt;element name="CurrencyKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}CurrencyKey" minOccurs="0"/&gt;
 *         &lt;element name="Distributions" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ArrayOfPayablesDistribution" minOccurs="0"/&gt;
 *         &lt;element name="Taxes" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ArrayOfPayablesTax" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PayablesDocument", propOrder = {
    "key",
    "vendorDocumentNumber",
    "documentAmount",
    "date",
    "generalLedgerPostingDate",
    "purchasesAmount",
    "description",
    "tradeDiscountAmount",
    "taxAmount",
    "freightAmount",
    "amount1099",
    "miscellaneousAmount",
    "poNumber",
    "exchangeRate",
    "exchangeDate",
    "type",
    "transactionState",
    "vendorName",
    "chargeAmount",
    "modifiedBy",
    "modifiedDate",
    "postedBy",
    "postedDate",
    "taxDate",
    "auditTrailCode",
    "isVoided",
    "isIntercompanyTransaction",
    "hasIntercompanyDistributions",
    "batchKey",
    "vendorKey",
    "addressKey",
    "remitToAddressKey",
    "taxScheduleKey",
    "shippingMethodKey",
    "purchaseTaxScheduleKey",
    "freightTaxScheduleKey",
    "miscellaneousTaxScheduleKey",
    "currencyKey",
    "distributions",
    "taxes"
})
@XmlSeeAlso({
    PayablesCreditDocument.class,
    PayablesDebitDocument.class
})
public abstract class PayablesDocument
    extends BusinessObject
{

    @XmlElement(name = "Key")
    protected PayablesDocumentKey key;
    @XmlElement(name = "VendorDocumentNumber")
    protected String vendorDocumentNumber;
    @XmlElement(name = "DocumentAmount")
    protected MoneyAmount documentAmount;
    @XmlElement(name = "Date", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar date;
    @XmlElement(name = "GeneralLedgerPostingDate", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar generalLedgerPostingDate;
    @XmlElement(name = "PurchasesAmount")
    protected MoneyAmount purchasesAmount;
    @XmlElement(name = "Description")
    protected String description;
    @XmlElement(name = "TradeDiscountAmount")
    protected MoneyAmount tradeDiscountAmount;
    @XmlElement(name = "TaxAmount")
    protected MoneyAmount taxAmount;
    @XmlElement(name = "FreightAmount")
    protected MoneyAmount freightAmount;
    @XmlElement(name = "Amount1099")
    protected MoneyAmount amount1099;
    @XmlElement(name = "MiscellaneousAmount")
    protected MoneyAmount miscellaneousAmount;
    @XmlElement(name = "PONumber")
    protected String poNumber;
    @XmlElement(name = "ExchangeRate", required = true, nillable = true)
    protected BigDecimal exchangeRate;
    @XmlElement(name = "ExchangeDate", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar exchangeDate;
    @XmlElement(name = "Type", required = true, nillable = true)
    @XmlSchemaType(name = "string")
    protected PayablesDocumentType type;
    @XmlElement(name = "TransactionState", required = true, nillable = true)
    @XmlSchemaType(name = "string")
    protected PayablesTransactionState transactionState;
    @XmlElement(name = "VendorName")
    protected String vendorName;
    @XmlElement(name = "ChargeAmount")
    protected MoneyAmount chargeAmount;
    @XmlElement(name = "ModifiedBy")
    protected String modifiedBy;
    @XmlElement(name = "ModifiedDate", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar modifiedDate;
    @XmlElement(name = "PostedBy")
    protected String postedBy;
    @XmlElement(name = "PostedDate", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar postedDate;
    @XmlElement(name = "TaxDate", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar taxDate;
    @XmlElement(name = "AuditTrailCode")
    protected String auditTrailCode;
    @XmlElement(name = "IsVoided", required = true, type = Boolean.class, nillable = true)
    protected Boolean isVoided;
    @XmlElement(name = "IsIntercompanyTransaction", required = true, type = Boolean.class, nillable = true)
    protected Boolean isIntercompanyTransaction;
    @XmlElement(name = "HasIntercompanyDistributions", required = true, type = Boolean.class, nillable = true)
    protected Boolean hasIntercompanyDistributions;
    @XmlElement(name = "BatchKey")
    protected BatchKey batchKey;
    @XmlElement(name = "VendorKey")
    protected VendorKey vendorKey;
    @XmlElement(name = "AddressKey")
    protected AddressKey addressKey;
    @XmlElement(name = "RemitToAddressKey")
    protected AddressKey remitToAddressKey;
    @XmlElement(name = "TaxScheduleKey")
    protected TaxScheduleKey taxScheduleKey;
    @XmlElement(name = "ShippingMethodKey")
    protected ShippingMethodKey shippingMethodKey;
    @XmlElement(name = "PurchaseTaxScheduleKey")
    protected TaxScheduleKey purchaseTaxScheduleKey;
    @XmlElement(name = "FreightTaxScheduleKey")
    protected TaxScheduleKey freightTaxScheduleKey;
    @XmlElement(name = "MiscellaneousTaxScheduleKey")
    protected TaxScheduleKey miscellaneousTaxScheduleKey;
    @XmlElement(name = "CurrencyKey")
    protected CurrencyKey currencyKey;
    @XmlElement(name = "Distributions")
    protected ArrayOfPayablesDistribution distributions;
    @XmlElement(name = "Taxes")
    protected ArrayOfPayablesTax taxes;

    /**
     * Gets the value of the key property.
     * 
     * @return
     *     possible object is
     *     {@link PayablesDocumentKey }
     *     
     */
    public PayablesDocumentKey getKey() {
        return key;
    }

    /**
     * Sets the value of the key property.
     * 
     * @param value
     *     allowed object is
     *     {@link PayablesDocumentKey }
     *     
     */
    public void setKey(PayablesDocumentKey value) {
        this.key = value;
    }

    /**
     * Gets the value of the vendorDocumentNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVendorDocumentNumber() {
        return vendorDocumentNumber;
    }

    /**
     * Sets the value of the vendorDocumentNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVendorDocumentNumber(String value) {
        this.vendorDocumentNumber = value;
    }

    /**
     * Gets the value of the documentAmount property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getDocumentAmount() {
        return documentAmount;
    }

    /**
     * Sets the value of the documentAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setDocumentAmount(MoneyAmount value) {
        this.documentAmount = value;
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
     * Gets the value of the purchasesAmount property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getPurchasesAmount() {
        return purchasesAmount;
    }

    /**
     * Sets the value of the purchasesAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setPurchasesAmount(MoneyAmount value) {
        this.purchasesAmount = value;
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
     * Gets the value of the amount1099 property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getAmount1099() {
        return amount1099;
    }

    /**
     * Sets the value of the amount1099 property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setAmount1099(MoneyAmount value) {
        this.amount1099 = value;
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
     * Gets the value of the poNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPONumber() {
        return poNumber;
    }

    /**
     * Sets the value of the poNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPONumber(String value) {
        this.poNumber = value;
    }

    /**
     * Gets the value of the exchangeRate property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getExchangeRate() {
        return exchangeRate;
    }

    /**
     * Sets the value of the exchangeRate property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setExchangeRate(BigDecimal value) {
        this.exchangeRate = value;
    }

    /**
     * Gets the value of the exchangeDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getExchangeDate() {
        return exchangeDate;
    }

    /**
     * Sets the value of the exchangeDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setExchangeDate(XMLGregorianCalendar value) {
        this.exchangeDate = value;
    }

    /**
     * Gets the value of the type property.
     * 
     * @return
     *     possible object is
     *     {@link PayablesDocumentType }
     *     
     */
    public PayablesDocumentType getType() {
        return type;
    }

    /**
     * Sets the value of the type property.
     * 
     * @param value
     *     allowed object is
     *     {@link PayablesDocumentType }
     *     
     */
    public void setType(PayablesDocumentType value) {
        this.type = value;
    }

    /**
     * Gets the value of the transactionState property.
     * 
     * @return
     *     possible object is
     *     {@link PayablesTransactionState }
     *     
     */
    public PayablesTransactionState getTransactionState() {
        return transactionState;
    }

    /**
     * Sets the value of the transactionState property.
     * 
     * @param value
     *     allowed object is
     *     {@link PayablesTransactionState }
     *     
     */
    public void setTransactionState(PayablesTransactionState value) {
        this.transactionState = value;
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
     * Gets the value of the chargeAmount property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getChargeAmount() {
        return chargeAmount;
    }

    /**
     * Sets the value of the chargeAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setChargeAmount(MoneyAmount value) {
        this.chargeAmount = value;
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
     * Gets the value of the taxDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getTaxDate() {
        return taxDate;
    }

    /**
     * Sets the value of the taxDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setTaxDate(XMLGregorianCalendar value) {
        this.taxDate = value;
    }

    /**
     * Gets the value of the auditTrailCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAuditTrailCode() {
        return auditTrailCode;
    }

    /**
     * Sets the value of the auditTrailCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAuditTrailCode(String value) {
        this.auditTrailCode = value;
    }

    /**
     * Gets the value of the isVoided property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsVoided() {
        return isVoided;
    }

    /**
     * Sets the value of the isVoided property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsVoided(Boolean value) {
        this.isVoided = value;
    }

    /**
     * Gets the value of the isIntercompanyTransaction property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsIntercompanyTransaction() {
        return isIntercompanyTransaction;
    }

    /**
     * Sets the value of the isIntercompanyTransaction property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsIntercompanyTransaction(Boolean value) {
        this.isIntercompanyTransaction = value;
    }

    /**
     * Gets the value of the hasIntercompanyDistributions property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isHasIntercompanyDistributions() {
        return hasIntercompanyDistributions;
    }

    /**
     * Sets the value of the hasIntercompanyDistributions property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setHasIntercompanyDistributions(Boolean value) {
        this.hasIntercompanyDistributions = value;
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
     * Gets the value of the addressKey property.
     * 
     * @return
     *     possible object is
     *     {@link AddressKey }
     *     
     */
    public AddressKey getAddressKey() {
        return addressKey;
    }

    /**
     * Sets the value of the addressKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link AddressKey }
     *     
     */
    public void setAddressKey(AddressKey value) {
        this.addressKey = value;
    }

    /**
     * Gets the value of the remitToAddressKey property.
     * 
     * @return
     *     possible object is
     *     {@link AddressKey }
     *     
     */
    public AddressKey getRemitToAddressKey() {
        return remitToAddressKey;
    }

    /**
     * Sets the value of the remitToAddressKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link AddressKey }
     *     
     */
    public void setRemitToAddressKey(AddressKey value) {
        this.remitToAddressKey = value;
    }

    /**
     * Gets the value of the taxScheduleKey property.
     * 
     * @return
     *     possible object is
     *     {@link TaxScheduleKey }
     *     
     */
    public TaxScheduleKey getTaxScheduleKey() {
        return taxScheduleKey;
    }

    /**
     * Sets the value of the taxScheduleKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link TaxScheduleKey }
     *     
     */
    public void setTaxScheduleKey(TaxScheduleKey value) {
        this.taxScheduleKey = value;
    }

    /**
     * Gets the value of the shippingMethodKey property.
     * 
     * @return
     *     possible object is
     *     {@link ShippingMethodKey }
     *     
     */
    public ShippingMethodKey getShippingMethodKey() {
        return shippingMethodKey;
    }

    /**
     * Sets the value of the shippingMethodKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link ShippingMethodKey }
     *     
     */
    public void setShippingMethodKey(ShippingMethodKey value) {
        this.shippingMethodKey = value;
    }

    /**
     * Gets the value of the purchaseTaxScheduleKey property.
     * 
     * @return
     *     possible object is
     *     {@link TaxScheduleKey }
     *     
     */
    public TaxScheduleKey getPurchaseTaxScheduleKey() {
        return purchaseTaxScheduleKey;
    }

    /**
     * Sets the value of the purchaseTaxScheduleKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link TaxScheduleKey }
     *     
     */
    public void setPurchaseTaxScheduleKey(TaxScheduleKey value) {
        this.purchaseTaxScheduleKey = value;
    }

    /**
     * Gets the value of the freightTaxScheduleKey property.
     * 
     * @return
     *     possible object is
     *     {@link TaxScheduleKey }
     *     
     */
    public TaxScheduleKey getFreightTaxScheduleKey() {
        return freightTaxScheduleKey;
    }

    /**
     * Sets the value of the freightTaxScheduleKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link TaxScheduleKey }
     *     
     */
    public void setFreightTaxScheduleKey(TaxScheduleKey value) {
        this.freightTaxScheduleKey = value;
    }

    /**
     * Gets the value of the miscellaneousTaxScheduleKey property.
     * 
     * @return
     *     possible object is
     *     {@link TaxScheduleKey }
     *     
     */
    public TaxScheduleKey getMiscellaneousTaxScheduleKey() {
        return miscellaneousTaxScheduleKey;
    }

    /**
     * Sets the value of the miscellaneousTaxScheduleKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link TaxScheduleKey }
     *     
     */
    public void setMiscellaneousTaxScheduleKey(TaxScheduleKey value) {
        this.miscellaneousTaxScheduleKey = value;
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
     * Gets the value of the distributions property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfPayablesDistribution }
     *     
     */
    public ArrayOfPayablesDistribution getDistributions() {
        return distributions;
    }

    /**
     * Sets the value of the distributions property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfPayablesDistribution }
     *     
     */
    public void setDistributions(ArrayOfPayablesDistribution value) {
        this.distributions = value;
    }

    /**
     * Gets the value of the taxes property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfPayablesTax }
     *     
     */
    public ArrayOfPayablesTax getTaxes() {
        return taxes;
    }

    /**
     * Sets the value of the taxes property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfPayablesTax }
     *     
     */
    public void setTaxes(ArrayOfPayablesTax value) {
        this.taxes = value;
    }

}
