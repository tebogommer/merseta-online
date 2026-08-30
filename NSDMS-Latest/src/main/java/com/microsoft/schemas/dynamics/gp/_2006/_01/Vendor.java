
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for Vendor complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Vendor"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/gp/2006/01}BusinessObject"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Key" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}VendorKey" minOccurs="0"/&gt;
 *         &lt;element name="Name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="ShortName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="CheckName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="IsOnHold" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="IsActive" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="IsOneTime" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="ClassKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}VendorClassKey" minOccurs="0"/&gt;
 *         &lt;element name="DefaultAddressKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}VendorAddressKey" minOccurs="0"/&gt;
 *         &lt;element name="PurchaseAddressKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}VendorAddressKey" minOccurs="0"/&gt;
 *         &lt;element name="RemitToAddressKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}VendorAddressKey" minOccurs="0"/&gt;
 *         &lt;element name="ShipFromAddressKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}VendorAddressKey" minOccurs="0"/&gt;
 *         &lt;element name="VendorAccountNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="Comment1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="Comment2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="Notes" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="CurrencyKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}CurrencyKey" minOccurs="0"/&gt;
 *         &lt;element name="RateTypeKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}RateTypeKey" minOccurs="0"/&gt;
 *         &lt;element name="PaymentTermsKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}PaymentTermsKey" minOccurs="0"/&gt;
 *         &lt;element name="DiscountGracePeriod" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="DueDateGracePeriod" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="PaymentPriority" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="MinimumOrderAmount" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="TradeDiscountPercent" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}Percent" minOccurs="0"/&gt;
 *         &lt;element name="TaxSchedule" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="TaxIdentificationNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="TaxRegistrationNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="BankAccountKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}BankAccountKey" minOccurs="0"/&gt;
 *         &lt;element name="UserDefined1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="UserDefined2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="Tax1099Type" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}Tax1099Type"/&gt;
 *         &lt;element name="Tax1099BoxNumber" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="FreeOnBoard" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}FreeOnBoard"/&gt;
 *         &lt;element name="UserLanguageKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}LanguageKey" minOccurs="0"/&gt;
 *         &lt;element name="MinimumPayment" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyPercentChoice" minOccurs="0"/&gt;
 *         &lt;element name="MaximumInvoice" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MaximumInvoice" minOccurs="0"/&gt;
 *         &lt;element name="CreditLimit" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}VendorCreditLimit" minOccurs="0"/&gt;
 *         &lt;element name="MaximumWriteoff" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MaximumWriteoff" minOccurs="0"/&gt;
 *         &lt;element name="AllowRevaluation" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="PostResultsTo" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}PayablesPostResultsTo"/&gt;
 *         &lt;element name="HistoryOptions" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}HistoryOptions" minOccurs="0"/&gt;
 *         &lt;element name="DefaultCashAccountType" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}PayablesCashAccountType"/&gt;
 *         &lt;element name="CashGLAccountKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}GLAccountNumberKey" minOccurs="0"/&gt;
 *         &lt;element name="AccountsPayableGLAccountKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}GLAccountNumberKey" minOccurs="0"/&gt;
 *         &lt;element name="TermsDiscountAvailableGLAccountKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}GLAccountNumberKey" minOccurs="0"/&gt;
 *         &lt;element name="TermsDiscountTakenGLAccountKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}GLAccountNumberKey" minOccurs="0"/&gt;
 *         &lt;element name="FinanceChargesGLAccountKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}GLAccountNumberKey" minOccurs="0"/&gt;
 *         &lt;element name="PurchasesGLAccountKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}GLAccountNumberKey" minOccurs="0"/&gt;
 *         &lt;element name="TradeDiscountGLAccountKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}GLAccountNumberKey" minOccurs="0"/&gt;
 *         &lt;element name="MiscellaneousGLAccountKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}GLAccountNumberKey" minOccurs="0"/&gt;
 *         &lt;element name="FreightGLAccountKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}GLAccountNumberKey" minOccurs="0"/&gt;
 *         &lt;element name="TaxGLAccountKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}GLAccountNumberKey" minOccurs="0"/&gt;
 *         &lt;element name="WriteoffGLAccountKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}GLAccountNumberKey" minOccurs="0"/&gt;
 *         &lt;element name="AccruedPurchasesGLAccountKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}GLAccountNumberKey" minOccurs="0"/&gt;
 *         &lt;element name="PurchasePriceVarianceGLAccountKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}GLAccountNumberKey" minOccurs="0"/&gt;
 *         &lt;element name="CreatedDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="ModifiedDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="ProjectAccountingOptions" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ProjectAccountingOptions" minOccurs="0"/&gt;
 *         &lt;element name="Addresses" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ArrayOfVendorAddress" minOccurs="0"/&gt;
 *         &lt;element name="WorkflowPriority" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}WorkflowPriority"/&gt;
 *         &lt;element name="Workflows" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ArrayOfWorkflow" minOccurs="0"/&gt;
 *         &lt;element name="Language" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="UpdateIfExists" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Vendor", propOrder = {
    "key",
    "name",
    "shortName",
    "checkName",
    "isOnHold",
    "isActive",
    "isOneTime",
    "classKey",
    "defaultAddressKey",
    "purchaseAddressKey",
    "remitToAddressKey",
    "shipFromAddressKey",
    "vendorAccountNumber",
    "comment1",
    "comment2",
    "notes",
    "currencyKey",
    "rateTypeKey",
    "paymentTermsKey",
    "discountGracePeriod",
    "dueDateGracePeriod",
    "paymentPriority",
    "minimumOrderAmount",
    "tradeDiscountPercent",
    "taxSchedule",
    "taxIdentificationNumber",
    "taxRegistrationNumber",
    "bankAccountKey",
    "userDefined1",
    "userDefined2",
    "tax1099Type",
    "tax1099BoxNumber",
    "freeOnBoard",
    "userLanguageKey",
    "minimumPayment",
    "maximumInvoice",
    "creditLimit",
    "maximumWriteoff",
    "allowRevaluation",
    "postResultsTo",
    "historyOptions",
    "defaultCashAccountType",
    "cashGLAccountKey",
    "accountsPayableGLAccountKey",
    "termsDiscountAvailableGLAccountKey",
    "termsDiscountTakenGLAccountKey",
    "financeChargesGLAccountKey",
    "purchasesGLAccountKey",
    "tradeDiscountGLAccountKey",
    "miscellaneousGLAccountKey",
    "freightGLAccountKey",
    "taxGLAccountKey",
    "writeoffGLAccountKey",
    "accruedPurchasesGLAccountKey",
    "purchasePriceVarianceGLAccountKey",
    "createdDate",
    "modifiedDate",
    "projectAccountingOptions",
    "addresses",
    "workflowPriority",
    "workflows",
    "language",
    "UpdateIfExists"
})
public class Vendor
    extends BusinessObject
{

    @XmlElement(name = "Key")
    protected VendorKey key;
    @XmlElement(name = "Name")
    protected String name;
    @XmlElement(name = "ShortName")
    protected String shortName;
    @XmlElement(name = "CheckName")
    protected String checkName;
    @XmlElement(name = "IsOnHold", required = true, type = Boolean.class, nillable = true)
    protected Boolean isOnHold;
    @XmlElement(name = "IsActive", required = true, type = Boolean.class, nillable = true)
    protected Boolean isActive;
    @XmlElement(name = "IsOneTime", required = true, type = Boolean.class, nillable = true)
    protected Boolean isOneTime;
    @XmlElement(name = "ClassKey")
    protected VendorClassKey classKey;
    @XmlElement(name = "DefaultAddressKey")
    protected VendorAddressKey defaultAddressKey;
    @XmlElement(name = "PurchaseAddressKey")
    protected VendorAddressKey purchaseAddressKey;
    @XmlElement(name = "RemitToAddressKey")
    protected VendorAddressKey remitToAddressKey;
    @XmlElement(name = "ShipFromAddressKey")
    protected VendorAddressKey shipFromAddressKey;
    @XmlElement(name = "VendorAccountNumber")
    protected String vendorAccountNumber;
    @XmlElement(name = "Comment1")
    protected String comment1;
    @XmlElement(name = "Comment2")
    protected String comment2;
    @XmlElement(name = "Notes")
    protected String notes;
    @XmlElement(name = "CurrencyKey")
    protected CurrencyKey currencyKey;
    @XmlElement(name = "RateTypeKey")
    protected RateTypeKey rateTypeKey;
    @XmlElement(name = "PaymentTermsKey")
    protected PaymentTermsKey paymentTermsKey;
    @XmlElement(name = "DiscountGracePeriod")
    protected Integer discountGracePeriod;
    @XmlElement(name = "DueDateGracePeriod")
    protected Integer dueDateGracePeriod;
    @XmlElement(name = "PaymentPriority")
    protected String paymentPriority;
    @XmlElement(name = "MinimumOrderAmount")
    protected MoneyAmount minimumOrderAmount;
    @XmlElement(name = "TradeDiscountPercent")
    protected Percent tradeDiscountPercent;
    @XmlElement(name = "TaxSchedule")
    protected String taxSchedule;
    @XmlElement(name = "TaxIdentificationNumber")
    protected String taxIdentificationNumber;
    @XmlElement(name = "TaxRegistrationNumber")
    protected String taxRegistrationNumber;
    @XmlElement(name = "BankAccountKey")
    protected BankAccountKey bankAccountKey;
    @XmlElement(name = "UserDefined1")
    protected String userDefined1;
    @XmlElement(name = "UserDefined2")
    protected String userDefined2;
    @XmlElement(name = "Tax1099Type")
    @XmlSchemaType(name = "string")
    protected Tax1099Type tax1099Type;
    @XmlElement(name = "Tax1099BoxNumber")
    protected Integer tax1099BoxNumber;
    @XmlElement(name = "FreeOnBoard")
    @XmlSchemaType(name = "string")
    protected FreeOnBoard freeOnBoard;
    @XmlElement(name = "UserLanguageKey")
    protected LanguageKey userLanguageKey;
    @XmlElement(name = "MinimumPayment")
    protected MoneyPercentChoice minimumPayment;
    @XmlElement(name = "MaximumInvoice")
    protected MaximumInvoice maximumInvoice;
    @XmlElement(name = "CreditLimit")
    protected VendorCreditLimit creditLimit;
    @XmlElement(name = "MaximumWriteoff")
    protected MaximumWriteoff maximumWriteoff;
    @XmlElement(name = "AllowRevaluation")
    protected Boolean allowRevaluation;
    @XmlElement(name = "PostResultsTo", required = true)
    @XmlSchemaType(name = "string")
    protected PayablesPostResultsTo postResultsTo;
    @XmlElement(name = "HistoryOptions")
    protected HistoryOptions historyOptions;
    @XmlElement(name = "DefaultCashAccountType")
    @XmlSchemaType(name = "string")
    protected PayablesCashAccountType defaultCashAccountType;
    @XmlElement(name = "CashGLAccountKey")
    protected GLAccountNumberKey cashGLAccountKey;
    @XmlElement(name = "AccountsPayableGLAccountKey")
    protected GLAccountNumberKey accountsPayableGLAccountKey;
    @XmlElement(name = "TermsDiscountAvailableGLAccountKey")
    protected GLAccountNumberKey termsDiscountAvailableGLAccountKey;
    @XmlElement(name = "TermsDiscountTakenGLAccountKey")
    protected GLAccountNumberKey termsDiscountTakenGLAccountKey;
    @XmlElement(name = "FinanceChargesGLAccountKey")
    protected GLAccountNumberKey financeChargesGLAccountKey;
    @XmlElement(name = "PurchasesGLAccountKey")
    protected GLAccountNumberKey purchasesGLAccountKey;
    @XmlElement(name = "TradeDiscountGLAccountKey")
    protected GLAccountNumberKey tradeDiscountGLAccountKey;
    @XmlElement(name = "MiscellaneousGLAccountKey")
    protected GLAccountNumberKey miscellaneousGLAccountKey;
    @XmlElement(name = "FreightGLAccountKey")
    protected GLAccountNumberKey freightGLAccountKey;
    @XmlElement(name = "TaxGLAccountKey")
    protected GLAccountNumberKey taxGLAccountKey;
    @XmlElement(name = "WriteoffGLAccountKey")
    protected GLAccountNumberKey writeoffGLAccountKey;
    @XmlElement(name = "AccruedPurchasesGLAccountKey")
    protected GLAccountNumberKey accruedPurchasesGLAccountKey;
    @XmlElement(name = "PurchasePriceVarianceGLAccountKey")
    protected GLAccountNumberKey purchasePriceVarianceGLAccountKey;
    @XmlElement(name = "CreatedDate", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar createdDate;
    @XmlElement(name = "ModifiedDate", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar modifiedDate;
    @XmlElement(name = "ProjectAccountingOptions")
    protected ProjectAccountingOptions projectAccountingOptions;
    @XmlElement(name = "Addresses")
    protected ArrayOfVendorAddress addresses;
    @XmlElement(name = "WorkflowPriority")
    @XmlSchemaType(name = "string")
    protected WorkflowPriority workflowPriority;
    @XmlElement(name = "Workflows")
    protected ArrayOfWorkflow workflows;
    @XmlElement(name = "Language")
    protected String language;
    @XmlElement(name = "UpdateIfExists", required = true, type = Boolean.class, nillable = true)
    protected Boolean UpdateIfExists;

    
    
    
    public Vendor() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Vendor(VendorKey key) {
		super();
		this.key = key;
	}

	/**
     * Gets the value of the key property.
     * 
     * @return
     *     possible object is
     *     {@link VendorKey }
     *     
     */
    public VendorKey getKey() {
        return key;
    }

    /**
     * Sets the value of the key property.
     * 
     * @param value
     *     allowed object is
     *     {@link VendorKey }
     *     
     */
    public void setKey(VendorKey value) {
        this.key = value;
    }

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Gets the value of the shortName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getShortName() {
        return shortName;
    }

    /**
     * Sets the value of the shortName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setShortName(String value) {
        this.shortName = value;
    }

    /**
     * Gets the value of the checkName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCheckName() {
        return checkName;
    }

    /**
     * Sets the value of the checkName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCheckName(String value) {
        this.checkName = value;
    }

    /**
     * Gets the value of the isOnHold property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsOnHold() {
        return isOnHold;
    }
    
    @Transient
    public Boolean getDetailsOnHold() {
        return isOnHold;
    }

    /**
     * Sets the value of the isOnHold property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsOnHold(Boolean value) {
        this.isOnHold = value;
    }

    /**
     * Gets the value of the isActive property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsActive() {
        return isActive;
    }

    /**
     * Sets the value of the isActive property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsActive(Boolean value) {
        this.isActive = value;
    }

    /**
     * Gets the value of the isOneTime property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsOneTime() {
        return isOneTime;
    }

    /**
     * Sets the value of the isOneTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsOneTime(Boolean value) {
        this.isOneTime = value;
    }

    /**
     * Gets the value of the classKey property.
     * 
     * @return
     *     possible object is
     *     {@link VendorClassKey }
     *     
     */
    public VendorClassKey getClassKey() {
        return classKey;
    }

    /**
     * Sets the value of the classKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link VendorClassKey }
     *     
     */
    public void setClassKey(VendorClassKey value) {
        this.classKey = value;
    }

    /**
     * Gets the value of the defaultAddressKey property.
     * 
     * @return
     *     possible object is
     *     {@link VendorAddressKey }
     *     
     */
    public VendorAddressKey getDefaultAddressKey() {
        return defaultAddressKey;
    }

    /**
     * Sets the value of the defaultAddressKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link VendorAddressKey }
     *     
     */
    public void setDefaultAddressKey(VendorAddressKey value) {
        this.defaultAddressKey = value;
    }

    /**
     * Gets the value of the purchaseAddressKey property.
     * 
     * @return
     *     possible object is
     *     {@link VendorAddressKey }
     *     
     */
    public VendorAddressKey getPurchaseAddressKey() {
        return purchaseAddressKey;
    }

    /**
     * Sets the value of the purchaseAddressKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link VendorAddressKey }
     *     
     */
    public void setPurchaseAddressKey(VendorAddressKey value) {
        this.purchaseAddressKey = value;
    }

    /**
     * Gets the value of the remitToAddressKey property.
     * 
     * @return
     *     possible object is
     *     {@link VendorAddressKey }
     *     
     */
    public VendorAddressKey getRemitToAddressKey() {
        return remitToAddressKey;
    }

    /**
     * Sets the value of the remitToAddressKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link VendorAddressKey }
     *     
     */
    public void setRemitToAddressKey(VendorAddressKey value) {
        this.remitToAddressKey = value;
    }

    /**
     * Gets the value of the shipFromAddressKey property.
     * 
     * @return
     *     possible object is
     *     {@link VendorAddressKey }
     *     
     */
    public VendorAddressKey getShipFromAddressKey() {
        return shipFromAddressKey;
    }

    /**
     * Sets the value of the shipFromAddressKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link VendorAddressKey }
     *     
     */
    public void setShipFromAddressKey(VendorAddressKey value) {
        this.shipFromAddressKey = value;
    }

    /**
     * Gets the value of the vendorAccountNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVendorAccountNumber() {
        return vendorAccountNumber;
    }

    /**
     * Sets the value of the vendorAccountNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVendorAccountNumber(String value) {
        this.vendorAccountNumber = value;
    }

    /**
     * Gets the value of the comment1 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getComment1() {
        return comment1;
    }

    /**
     * Sets the value of the comment1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setComment1(String value) {
        this.comment1 = value;
    }

    /**
     * Gets the value of the comment2 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getComment2() {
        return comment2;
    }

    /**
     * Sets the value of the comment2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setComment2(String value) {
        this.comment2 = value;
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
     * Gets the value of the rateTypeKey property.
     * 
     * @return
     *     possible object is
     *     {@link RateTypeKey }
     *     
     */
    public RateTypeKey getRateTypeKey() {
        return rateTypeKey;
    }

    /**
     * Sets the value of the rateTypeKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link RateTypeKey }
     *     
     */
    public void setRateTypeKey(RateTypeKey value) {
        this.rateTypeKey = value;
    }

    /**
     * Gets the value of the paymentTermsKey property.
     * 
     * @return
     *     possible object is
     *     {@link PaymentTermsKey }
     *     
     */
    public PaymentTermsKey getPaymentTermsKey() {
        return paymentTermsKey;
    }

    /**
     * Sets the value of the paymentTermsKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link PaymentTermsKey }
     *     
     */
    public void setPaymentTermsKey(PaymentTermsKey value) {
        this.paymentTermsKey = value;
    }

    /**
     * Gets the value of the discountGracePeriod property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getDiscountGracePeriod() {
        return discountGracePeriod;
    }

    /**
     * Sets the value of the discountGracePeriod property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setDiscountGracePeriod(Integer value) {
        this.discountGracePeriod = value;
    }

    /**
     * Gets the value of the dueDateGracePeriod property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getDueDateGracePeriod() {
        return dueDateGracePeriod;
    }

    /**
     * Sets the value of the dueDateGracePeriod property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setDueDateGracePeriod(Integer value) {
        this.dueDateGracePeriod = value;
    }

    /**
     * Gets the value of the paymentPriority property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPaymentPriority() {
        return paymentPriority;
    }

    /**
     * Sets the value of the paymentPriority property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPaymentPriority(String value) {
        this.paymentPriority = value;
    }

    /**
     * Gets the value of the minimumOrderAmount property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getMinimumOrderAmount() {
        return minimumOrderAmount;
    }

    /**
     * Sets the value of the minimumOrderAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setMinimumOrderAmount(MoneyAmount value) {
        this.minimumOrderAmount = value;
    }

    /**
     * Gets the value of the tradeDiscountPercent property.
     * 
     * @return
     *     possible object is
     *     {@link Percent }
     *     
     */
    public Percent getTradeDiscountPercent() {
        return tradeDiscountPercent;
    }

    /**
     * Sets the value of the tradeDiscountPercent property.
     * 
     * @param value
     *     allowed object is
     *     {@link Percent }
     *     
     */
    public void setTradeDiscountPercent(Percent value) {
        this.tradeDiscountPercent = value;
    }

    /**
     * Gets the value of the taxSchedule property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTaxSchedule() {
        return taxSchedule;
    }

    /**
     * Sets the value of the taxSchedule property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTaxSchedule(String value) {
        this.taxSchedule = value;
    }

    /**
     * Gets the value of the taxIdentificationNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTaxIdentificationNumber() {
        return taxIdentificationNumber;
    }

    /**
     * Sets the value of the taxIdentificationNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTaxIdentificationNumber(String value) {
        this.taxIdentificationNumber = value;
    }

    /**
     * Gets the value of the taxRegistrationNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTaxRegistrationNumber() {
        return taxRegistrationNumber;
    }
    
	@Transient
	public String getMaskedBankAccNumber() {
		if (taxRegistrationNumber != null && taxRegistrationNumber.length() > 0) return "*********" + taxRegistrationNumber.substring(taxRegistrationNumber.length() - 4, taxRegistrationNumber.length());
		else return "";
	}

    /**
     * Sets the value of the taxRegistrationNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTaxRegistrationNumber(String value) {
        this.taxRegistrationNumber = value;
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
     * Gets the value of the userDefined1 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUserDefined1() {
        return userDefined1;
    }

    /**
     * Sets the value of the userDefined1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUserDefined1(String value) {
        this.userDefined1 = value;
    }

    /**
     * Gets the value of the userDefined2 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUserDefined2() {
        return userDefined2;
    }

    /**
     * Sets the value of the userDefined2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUserDefined2(String value) {
        this.userDefined2 = value;
    }

    /**
     * Gets the value of the tax1099Type property.
     * 
     * @return
     *     possible object is
     *     {@link Tax1099Type }
     *     
     */
    public Tax1099Type getTax1099Type() {
        return tax1099Type;
    }

    /**
     * Sets the value of the tax1099Type property.
     * 
     * @param value
     *     allowed object is
     *     {@link Tax1099Type }
     *     
     */
    public void setTax1099Type(Tax1099Type value) {
        this.tax1099Type = value;
    }

    /**
     * Gets the value of the tax1099BoxNumber property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getTax1099BoxNumber() {
        return tax1099BoxNumber;
    }

    /**
     * Sets the value of the tax1099BoxNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setTax1099BoxNumber(Integer value) {
        this.tax1099BoxNumber = value;
    }

    /**
     * Gets the value of the freeOnBoard property.
     * 
     * @return
     *     possible object is
     *     {@link FreeOnBoard }
     *     
     */
    public FreeOnBoard getFreeOnBoard() {
        return freeOnBoard;
    }

    /**
     * Sets the value of the freeOnBoard property.
     * 
     * @param value
     *     allowed object is
     *     {@link FreeOnBoard }
     *     
     */
    public void setFreeOnBoard(FreeOnBoard value) {
        this.freeOnBoard = value;
    }

    /**
     * Gets the value of the userLanguageKey property.
     * 
     * @return
     *     possible object is
     *     {@link LanguageKey }
     *     
     */
    public LanguageKey getUserLanguageKey() {
        return userLanguageKey;
    }

    /**
     * Sets the value of the userLanguageKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link LanguageKey }
     *     
     */
    public void setUserLanguageKey(LanguageKey value) {
        this.userLanguageKey = value;
    }

    /**
     * Gets the value of the minimumPayment property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyPercentChoice }
     *     
     */
    public MoneyPercentChoice getMinimumPayment() {
        return minimumPayment;
    }

    /**
     * Sets the value of the minimumPayment property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyPercentChoice }
     *     
     */
    public void setMinimumPayment(MoneyPercentChoice value) {
        this.minimumPayment = value;
    }

    /**
     * Gets the value of the maximumInvoice property.
     * 
     * @return
     *     possible object is
     *     {@link MaximumInvoice }
     *     
     */
    public MaximumInvoice getMaximumInvoice() {
        return maximumInvoice;
    }

    /**
     * Sets the value of the maximumInvoice property.
     * 
     * @param value
     *     allowed object is
     *     {@link MaximumInvoice }
     *     
     */
    public void setMaximumInvoice(MaximumInvoice value) {
        this.maximumInvoice = value;
    }

    /**
     * Gets the value of the creditLimit property.
     * 
     * @return
     *     possible object is
     *     {@link VendorCreditLimit }
     *     
     */
    public VendorCreditLimit getCreditLimit() {
        return creditLimit;
    }

    /**
     * Sets the value of the creditLimit property.
     * 
     * @param value
     *     allowed object is
     *     {@link VendorCreditLimit }
     *     
     */
    public void setCreditLimit(VendorCreditLimit value) {
        this.creditLimit = value;
    }

    /**
     * Gets the value of the maximumWriteoff property.
     * 
     * @return
     *     possible object is
     *     {@link MaximumWriteoff }
     *     
     */
    public MaximumWriteoff getMaximumWriteoff() {
        return maximumWriteoff;
    }

    /**
     * Sets the value of the maximumWriteoff property.
     * 
     * @param value
     *     allowed object is
     *     {@link MaximumWriteoff }
     *     
     */
    public void setMaximumWriteoff(MaximumWriteoff value) {
        this.maximumWriteoff = value;
    }

    /**
     * Gets the value of the allowRevaluation property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isAllowRevaluation() {
        return allowRevaluation;
    }

    /**
     * Sets the value of the allowRevaluation property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setAllowRevaluation(Boolean value) {
        this.allowRevaluation = value;
    }

    /**
     * Gets the value of the postResultsTo property.
     * 
     * @return
     *     possible object is
     *     {@link PayablesPostResultsTo }
     *     
     */
    public PayablesPostResultsTo getPostResultsTo() {
        return postResultsTo;
    }

    /**
     * Sets the value of the postResultsTo property.
     * 
     * @param value
     *     allowed object is
     *     {@link PayablesPostResultsTo }
     *     
     */
    public void setPostResultsTo(PayablesPostResultsTo value) {
        this.postResultsTo = value;
    }

    /**
     * Gets the value of the historyOptions property.
     * 
     * @return
     *     possible object is
     *     {@link HistoryOptions }
     *     
     */
    public HistoryOptions getHistoryOptions() {
        return historyOptions;
    }

    /**
     * Sets the value of the historyOptions property.
     * 
     * @param value
     *     allowed object is
     *     {@link HistoryOptions }
     *     
     */
    public void setHistoryOptions(HistoryOptions value) {
        this.historyOptions = value;
    }

    /**
     * Gets the value of the defaultCashAccountType property.
     * 
     * @return
     *     possible object is
     *     {@link PayablesCashAccountType }
     *     
     */
    public PayablesCashAccountType getDefaultCashAccountType() {
        return defaultCashAccountType;
    }

    /**
     * Sets the value of the defaultCashAccountType property.
     * 
     * @param value
     *     allowed object is
     *     {@link PayablesCashAccountType }
     *     
     */
    public void setDefaultCashAccountType(PayablesCashAccountType value) {
        this.defaultCashAccountType = value;
    }

    /**
     * Gets the value of the cashGLAccountKey property.
     * 
     * @return
     *     possible object is
     *     {@link GLAccountNumberKey }
     *     
     */
    public GLAccountNumberKey getCashGLAccountKey() {
        return cashGLAccountKey;
    }

    /**
     * Sets the value of the cashGLAccountKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link GLAccountNumberKey }
     *     
     */
    public void setCashGLAccountKey(GLAccountNumberKey value) {
        this.cashGLAccountKey = value;
    }

    /**
     * Gets the value of the accountsPayableGLAccountKey property.
     * 
     * @return
     *     possible object is
     *     {@link GLAccountNumberKey }
     *     
     */
    public GLAccountNumberKey getAccountsPayableGLAccountKey() {
        return accountsPayableGLAccountKey;
    }

    /**
     * Sets the value of the accountsPayableGLAccountKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link GLAccountNumberKey }
     *     
     */
    public void setAccountsPayableGLAccountKey(GLAccountNumberKey value) {
        this.accountsPayableGLAccountKey = value;
    }

    /**
     * Gets the value of the termsDiscountAvailableGLAccountKey property.
     * 
     * @return
     *     possible object is
     *     {@link GLAccountNumberKey }
     *     
     */
    public GLAccountNumberKey getTermsDiscountAvailableGLAccountKey() {
        return termsDiscountAvailableGLAccountKey;
    }

    /**
     * Sets the value of the termsDiscountAvailableGLAccountKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link GLAccountNumberKey }
     *     
     */
    public void setTermsDiscountAvailableGLAccountKey(GLAccountNumberKey value) {
        this.termsDiscountAvailableGLAccountKey = value;
    }

    /**
     * Gets the value of the termsDiscountTakenGLAccountKey property.
     * 
     * @return
     *     possible object is
     *     {@link GLAccountNumberKey }
     *     
     */
    public GLAccountNumberKey getTermsDiscountTakenGLAccountKey() {
        return termsDiscountTakenGLAccountKey;
    }

    /**
     * Sets the value of the termsDiscountTakenGLAccountKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link GLAccountNumberKey }
     *     
     */
    public void setTermsDiscountTakenGLAccountKey(GLAccountNumberKey value) {
        this.termsDiscountTakenGLAccountKey = value;
    }

    /**
     * Gets the value of the financeChargesGLAccountKey property.
     * 
     * @return
     *     possible object is
     *     {@link GLAccountNumberKey }
     *     
     */
    public GLAccountNumberKey getFinanceChargesGLAccountKey() {
        return financeChargesGLAccountKey;
    }

    /**
     * Sets the value of the financeChargesGLAccountKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link GLAccountNumberKey }
     *     
     */
    public void setFinanceChargesGLAccountKey(GLAccountNumberKey value) {
        this.financeChargesGLAccountKey = value;
    }

    /**
     * Gets the value of the purchasesGLAccountKey property.
     * 
     * @return
     *     possible object is
     *     {@link GLAccountNumberKey }
     *     
     */
    public GLAccountNumberKey getPurchasesGLAccountKey() {
        return purchasesGLAccountKey;
    }

    /**
     * Sets the value of the purchasesGLAccountKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link GLAccountNumberKey }
     *     
     */
    public void setPurchasesGLAccountKey(GLAccountNumberKey value) {
        this.purchasesGLAccountKey = value;
    }

    /**
     * Gets the value of the tradeDiscountGLAccountKey property.
     * 
     * @return
     *     possible object is
     *     {@link GLAccountNumberKey }
     *     
     */
    public GLAccountNumberKey getTradeDiscountGLAccountKey() {
        return tradeDiscountGLAccountKey;
    }

    /**
     * Sets the value of the tradeDiscountGLAccountKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link GLAccountNumberKey }
     *     
     */
    public void setTradeDiscountGLAccountKey(GLAccountNumberKey value) {
        this.tradeDiscountGLAccountKey = value;
    }

    /**
     * Gets the value of the miscellaneousGLAccountKey property.
     * 
     * @return
     *     possible object is
     *     {@link GLAccountNumberKey }
     *     
     */
    public GLAccountNumberKey getMiscellaneousGLAccountKey() {
        return miscellaneousGLAccountKey;
    }

    /**
     * Sets the value of the miscellaneousGLAccountKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link GLAccountNumberKey }
     *     
     */
    public void setMiscellaneousGLAccountKey(GLAccountNumberKey value) {
        this.miscellaneousGLAccountKey = value;
    }

    /**
     * Gets the value of the freightGLAccountKey property.
     * 
     * @return
     *     possible object is
     *     {@link GLAccountNumberKey }
     *     
     */
    public GLAccountNumberKey getFreightGLAccountKey() {
        return freightGLAccountKey;
    }

    /**
     * Sets the value of the freightGLAccountKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link GLAccountNumberKey }
     *     
     */
    public void setFreightGLAccountKey(GLAccountNumberKey value) {
        this.freightGLAccountKey = value;
    }

    /**
     * Gets the value of the taxGLAccountKey property.
     * 
     * @return
     *     possible object is
     *     {@link GLAccountNumberKey }
     *     
     */
    public GLAccountNumberKey getTaxGLAccountKey() {
        return taxGLAccountKey;
    }

    /**
     * Sets the value of the taxGLAccountKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link GLAccountNumberKey }
     *     
     */
    public void setTaxGLAccountKey(GLAccountNumberKey value) {
        this.taxGLAccountKey = value;
    }

    /**
     * Gets the value of the writeoffGLAccountKey property.
     * 
     * @return
     *     possible object is
     *     {@link GLAccountNumberKey }
     *     
     */
    public GLAccountNumberKey getWriteoffGLAccountKey() {
        return writeoffGLAccountKey;
    }

    /**
     * Sets the value of the writeoffGLAccountKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link GLAccountNumberKey }
     *     
     */
    public void setWriteoffGLAccountKey(GLAccountNumberKey value) {
        this.writeoffGLAccountKey = value;
    }

    /**
     * Gets the value of the accruedPurchasesGLAccountKey property.
     * 
     * @return
     *     possible object is
     *     {@link GLAccountNumberKey }
     *     
     */
    public GLAccountNumberKey getAccruedPurchasesGLAccountKey() {
        return accruedPurchasesGLAccountKey;
    }

    /**
     * Sets the value of the accruedPurchasesGLAccountKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link GLAccountNumberKey }
     *     
     */
    public void setAccruedPurchasesGLAccountKey(GLAccountNumberKey value) {
        this.accruedPurchasesGLAccountKey = value;
    }

    /**
     * Gets the value of the purchasePriceVarianceGLAccountKey property.
     * 
     * @return
     *     possible object is
     *     {@link GLAccountNumberKey }
     *     
     */
    public GLAccountNumberKey getPurchasePriceVarianceGLAccountKey() {
        return purchasePriceVarianceGLAccountKey;
    }

    /**
     * Sets the value of the purchasePriceVarianceGLAccountKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link GLAccountNumberKey }
     *     
     */
    public void setPurchasePriceVarianceGLAccountKey(GLAccountNumberKey value) {
        this.purchasePriceVarianceGLAccountKey = value;
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
     * Gets the value of the projectAccountingOptions property.
     * 
     * @return
     *     possible object is
     *     {@link ProjectAccountingOptions }
     *     
     */
    public ProjectAccountingOptions getProjectAccountingOptions() {
        return projectAccountingOptions;
    }

    /**
     * Sets the value of the projectAccountingOptions property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProjectAccountingOptions }
     *     
     */
    public void setProjectAccountingOptions(ProjectAccountingOptions value) {
        this.projectAccountingOptions = value;
    }

    /**
     * Gets the value of the addresses property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfVendorAddress }
     *     
     */
    public ArrayOfVendorAddress getAddresses() {
        return addresses;
    }

    /**
     * Sets the value of the addresses property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfVendorAddress }
     *     
     */
    public void setAddresses(ArrayOfVendorAddress value) {
        this.addresses = value;
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

    /**
     * Gets the value of the language property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLanguage() {
        return language;
    }

    /**
     * Sets the value of the language property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLanguage(String value) {
        this.language = value;
    }

    /**
     * Gets the value of the isOnHold property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    
    public Boolean isUpdateIfExists() {
        return UpdateIfExists;
    }

    /**
     * Sets the value of the isOnHold property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setUpdateIfExists(Boolean value) {
        this.UpdateIfExists = value;
    }

}
