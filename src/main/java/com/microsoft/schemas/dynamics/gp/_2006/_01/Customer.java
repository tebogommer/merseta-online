
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for Customer complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Customer"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/gp/2006/01}BusinessObject"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Key" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}CustomerKey" minOccurs="0"/&gt;
 *         &lt;element name="IsOnHold" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="IsActive" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="ShipCompleteOnly" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="Name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="Shortname" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="StatementName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="ClassKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}CustomerClassKey" minOccurs="0"/&gt;
 *         &lt;element name="Priority" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="DefaultAddressKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}CustomerAddressKey" minOccurs="0"/&gt;
 *         &lt;element name="ShipToAddressKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}CustomerAddressKey" minOccurs="0"/&gt;
 *         &lt;element name="BillToAddressKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}CustomerAddressKey" minOccurs="0"/&gt;
 *         &lt;element name="StatementToAddressKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}CustomerAddressKey" minOccurs="0"/&gt;
 *         &lt;element name="Comment1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="Comment2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="TradeDiscountPercent" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}Percent" minOccurs="0"/&gt;
 *         &lt;element name="PaymentTermsKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}PaymentTermsKey" minOccurs="0"/&gt;
 *         &lt;element name="DiscountGracePeriod" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="DueDateGracePeriod" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="PriceLevelKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}PriceLevelKey" minOccurs="0"/&gt;
 *         &lt;element name="Notes" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="BalanceType" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}BalanceType"/&gt;
 *         &lt;element name="FinanceCharge" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyPercentChoice" minOccurs="0"/&gt;
 *         &lt;element name="MinimumPayment" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyPercentChoice" minOccurs="0"/&gt;
 *         &lt;element name="CreditLimit" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}CustomerCreditLimit" minOccurs="0"/&gt;
 *         &lt;element name="MaximumWriteoff" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MaximumWriteoff" minOccurs="0"/&gt;
 *         &lt;element name="AllowRevaluation" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="PostResultsTo" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ReceivablesPostResultsTo"/&gt;
 *         &lt;element name="OrderFullfillmentShortageDefault" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}OrderFullfillmentShortage"/&gt;
 *         &lt;element name="IncludeInDemandPlanning" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="PaymentCardAccount" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}PaymentCardAccount" minOccurs="0"/&gt;
 *         &lt;element name="BankName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="BankBranch" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="UserLanguageKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}LanguageKey" minOccurs="0"/&gt;
 *         &lt;element name="TaxExemptNumbers" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ArrayOfString" minOccurs="0"/&gt;
 *         &lt;element name="TaxRegistrationNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="CurrencyKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}CurrencyKey" minOccurs="0"/&gt;
 *         &lt;element name="RateTypeKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}RateTypeKey" minOccurs="0"/&gt;
 *         &lt;element name="StatementCycle" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}StatementCycle"/&gt;
 *         &lt;element name="HistoryOptions" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}HistoryOptions" minOccurs="0"/&gt;
 *         &lt;element name="SendEmailStatements" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="BankAccountKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}BankAccountKey" minOccurs="0"/&gt;
 *         &lt;element name="DefaultCashAccountType" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ReceivablesCashAccountType"/&gt;
 *         &lt;element name="CashGLAccountKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}GLAccountNumberKey" minOccurs="0"/&gt;
 *         &lt;element name="AccountsReceivableGLAccountKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}GLAccountNumberKey" minOccurs="0"/&gt;
 *         &lt;element name="SalesGLAccountKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}GLAccountNumberKey" minOccurs="0"/&gt;
 *         &lt;element name="CostOfGoodsSoldGLAccountKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}GLAccountNumberKey" minOccurs="0"/&gt;
 *         &lt;element name="InventoryGLAccountKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}GLAccountNumberKey" minOccurs="0"/&gt;
 *         &lt;element name="PaymentTermsDiscountTakenGLAccountKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}GLAccountNumberKey" minOccurs="0"/&gt;
 *         &lt;element name="PaymentTermsDiscountAvailableGLAccountKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}GLAccountNumberKey" minOccurs="0"/&gt;
 *         &lt;element name="FinanceChargesGLAccountKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}GLAccountNumberKey" minOccurs="0"/&gt;
 *         &lt;element name="WriteoffGLAccountKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}GLAccountNumberKey" minOccurs="0"/&gt;
 *         &lt;element name="SalesOrderReturnsGLAccountKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}GLAccountNumberKey" minOccurs="0"/&gt;
 *         &lt;element name="OverpaymentWriteoffGLAccountKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}GLAccountNumberKey" minOccurs="0"/&gt;
 *         &lt;element name="StatementRecipients" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}EmailRecipients" minOccurs="0"/&gt;
 *         &lt;element name="CreatedDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="ModifiedDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="LastModifiedDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="CorporateAccountKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}CustomerKey" minOccurs="0"/&gt;
 *         &lt;element name="UserDefined1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="UserDefined2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="Addresses" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ArrayOfCustomerAddress" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Customer", propOrder = {
    "key",
    "isOnHold",
    "isActive",
    "shipCompleteOnly",
    "name",
    "shortname",
    "statementName",
    "classKey",
    "priority",
    "defaultAddressKey",
    "shipToAddressKey",
    "billToAddressKey",
    "statementToAddressKey",
    "comment1",
    "comment2",
    "tradeDiscountPercent",
    "paymentTermsKey",
    "discountGracePeriod",
    "dueDateGracePeriod",
    "priceLevelKey",
    "notes",
    "balanceType",
    "financeCharge",
    "minimumPayment",
    "creditLimit",
    "maximumWriteoff",
    "allowRevaluation",
    "postResultsTo",
    "orderFullfillmentShortageDefault",
    "includeInDemandPlanning",
    "paymentCardAccount",
    "bankName",
    "bankBranch",
    "userLanguageKey",
    "taxExemptNumbers",
    "taxRegistrationNumber",
    "currencyKey",
    "rateTypeKey",
    "statementCycle",
    "historyOptions",
    "sendEmailStatements",
    "bankAccountKey",
    "defaultCashAccountType",
    "cashGLAccountKey",
    "accountsReceivableGLAccountKey",
    "salesGLAccountKey",
    "costOfGoodsSoldGLAccountKey",
    "inventoryGLAccountKey",
    "paymentTermsDiscountTakenGLAccountKey",
    "paymentTermsDiscountAvailableGLAccountKey",
    "financeChargesGLAccountKey",
    "writeoffGLAccountKey",
    "salesOrderReturnsGLAccountKey",
    "overpaymentWriteoffGLAccountKey",
    "statementRecipients",
    "createdDate",
    "modifiedDate",
    "lastModifiedDate",
    "corporateAccountKey",
    "userDefined1",
    "userDefined2",
    "addresses"
})
public class Customer
    extends BusinessObject
{

    @XmlElement(name = "Key")
    protected CustomerKey key;
    @XmlElement(name = "IsOnHold", required = true, type = Boolean.class, nillable = true)
    protected Boolean isOnHold;
    @XmlElement(name = "IsActive", required = true, type = Boolean.class, nillable = true)
    protected Boolean isActive;
    @XmlElement(name = "ShipCompleteOnly", required = true, type = Boolean.class, nillable = true)
    protected Boolean shipCompleteOnly;
    @XmlElement(name = "Name")
    protected String name;
    @XmlElement(name = "Shortname")
    protected String shortname;
    @XmlElement(name = "StatementName")
    protected String statementName;
    @XmlElement(name = "ClassKey")
    protected CustomerClassKey classKey;
    @XmlElement(name = "Priority", required = true, type = Integer.class, nillable = true)
    protected Integer priority;
    @XmlElement(name = "DefaultAddressKey")
    protected CustomerAddressKey defaultAddressKey;
    @XmlElement(name = "ShipToAddressKey")
    protected CustomerAddressKey shipToAddressKey;
    @XmlElement(name = "BillToAddressKey")
    protected CustomerAddressKey billToAddressKey;
    @XmlElement(name = "StatementToAddressKey")
    protected CustomerAddressKey statementToAddressKey;
    @XmlElement(name = "Comment1")
    protected String comment1;
    @XmlElement(name = "Comment2")
    protected String comment2;
    @XmlElement(name = "TradeDiscountPercent")
    protected Percent tradeDiscountPercent;
    @XmlElement(name = "PaymentTermsKey")
    protected PaymentTermsKey paymentTermsKey;
    @XmlElement(name = "DiscountGracePeriod", required = true, type = Integer.class, nillable = true)
    protected Integer discountGracePeriod;
    @XmlElement(name = "DueDateGracePeriod", required = true, type = Integer.class, nillable = true)
    protected Integer dueDateGracePeriod;
    @XmlElement(name = "PriceLevelKey")
    protected PriceLevelKey priceLevelKey;
    @XmlElement(name = "Notes")
    protected String notes;
    @XmlElement(name = "BalanceType", required = true, nillable = true)
    @XmlSchemaType(name = "string")
    protected BalanceType balanceType;
    @XmlElement(name = "FinanceCharge")
    protected MoneyPercentChoice financeCharge;
    @XmlElement(name = "MinimumPayment")
    protected MoneyPercentChoice minimumPayment;
    @XmlElement(name = "CreditLimit")
    protected CustomerCreditLimit creditLimit;
    @XmlElement(name = "MaximumWriteoff")
    protected MaximumWriteoff maximumWriteoff;
    @XmlElement(name = "AllowRevaluation", required = true, type = Boolean.class, nillable = true)
    protected Boolean allowRevaluation;
    @XmlElement(name = "PostResultsTo", required = true)
    @XmlSchemaType(name = "string")
    protected ReceivablesPostResultsTo postResultsTo;
    @XmlElement(name = "OrderFullfillmentShortageDefault", required = true, nillable = true)
    @XmlSchemaType(name = "string")
    protected OrderFullfillmentShortage orderFullfillmentShortageDefault;
    @XmlElement(name = "IncludeInDemandPlanning", required = true, type = Boolean.class, nillable = true)
    protected Boolean includeInDemandPlanning;
    @XmlElement(name = "PaymentCardAccount")
    protected PaymentCardAccount paymentCardAccount;
    @XmlElement(name = "BankName")
    protected String bankName;
    @XmlElement(name = "BankBranch")
    protected String bankBranch;
    @XmlElement(name = "UserLanguageKey")
    protected LanguageKey userLanguageKey;
    @XmlElement(name = "TaxExemptNumbers")
    protected ArrayOfString taxExemptNumbers;
    @XmlElement(name = "TaxRegistrationNumber")
    protected String taxRegistrationNumber;
    @XmlElement(name = "CurrencyKey")
    protected CurrencyKey currencyKey;
    @XmlElement(name = "RateTypeKey")
    protected RateTypeKey rateTypeKey;
    @XmlElement(name = "StatementCycle", required = true, nillable = true)
    @XmlSchemaType(name = "string")
    protected StatementCycle statementCycle;
    @XmlElement(name = "HistoryOptions")
    protected HistoryOptions historyOptions;
    @XmlElement(name = "SendEmailStatements", required = true, type = Boolean.class, nillable = true)
    protected Boolean sendEmailStatements;
    @XmlElement(name = "BankAccountKey")
    protected BankAccountKey bankAccountKey;
    @XmlElement(name = "DefaultCashAccountType", required = true, nillable = true)
    @XmlSchemaType(name = "string")
    protected ReceivablesCashAccountType defaultCashAccountType;
    @XmlElement(name = "CashGLAccountKey")
    protected GLAccountNumberKey cashGLAccountKey;
    @XmlElement(name = "AccountsReceivableGLAccountKey")
    protected GLAccountNumberKey accountsReceivableGLAccountKey;
    @XmlElement(name = "SalesGLAccountKey")
    protected GLAccountNumberKey salesGLAccountKey;
    @XmlElement(name = "CostOfGoodsSoldGLAccountKey")
    protected GLAccountNumberKey costOfGoodsSoldGLAccountKey;
    @XmlElement(name = "InventoryGLAccountKey")
    protected GLAccountNumberKey inventoryGLAccountKey;
    @XmlElement(name = "PaymentTermsDiscountTakenGLAccountKey")
    protected GLAccountNumberKey paymentTermsDiscountTakenGLAccountKey;
    @XmlElement(name = "PaymentTermsDiscountAvailableGLAccountKey")
    protected GLAccountNumberKey paymentTermsDiscountAvailableGLAccountKey;
    @XmlElement(name = "FinanceChargesGLAccountKey")
    protected GLAccountNumberKey financeChargesGLAccountKey;
    @XmlElement(name = "WriteoffGLAccountKey")
    protected GLAccountNumberKey writeoffGLAccountKey;
    @XmlElement(name = "SalesOrderReturnsGLAccountKey")
    protected GLAccountNumberKey salesOrderReturnsGLAccountKey;
    @XmlElement(name = "OverpaymentWriteoffGLAccountKey")
    protected GLAccountNumberKey overpaymentWriteoffGLAccountKey;
    @XmlElement(name = "StatementRecipients")
    protected EmailRecipients statementRecipients;
    @XmlElement(name = "CreatedDate", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar createdDate;
    @XmlElement(name = "ModifiedDate", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar modifiedDate;
    @XmlElement(name = "LastModifiedDate", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar lastModifiedDate;
    @XmlElement(name = "CorporateAccountKey")
    protected CustomerKey corporateAccountKey;
    @XmlElement(name = "UserDefined1")
    protected String userDefined1;
    @XmlElement(name = "UserDefined2")
    protected String userDefined2;
    @XmlElement(name = "Addresses")
    protected ArrayOfCustomerAddress addresses;

    /**
     * Gets the value of the key property.
     * 
     * @return
     *     possible object is
     *     {@link CustomerKey }
     *     
     */
    public CustomerKey getKey() {
        return key;
    }

    /**
     * Sets the value of the key property.
     * 
     * @param value
     *     allowed object is
     *     {@link CustomerKey }
     *     
     */
    public void setKey(CustomerKey value) {
        this.key = value;
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
     * Gets the value of the shipCompleteOnly property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isShipCompleteOnly() {
        return shipCompleteOnly;
    }

    /**
     * Sets the value of the shipCompleteOnly property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setShipCompleteOnly(Boolean value) {
        this.shipCompleteOnly = value;
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
     * Gets the value of the shortname property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getShortname() {
        return shortname;
    }

    /**
     * Sets the value of the shortname property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setShortname(String value) {
        this.shortname = value;
    }

    /**
     * Gets the value of the statementName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatementName() {
        return statementName;
    }

    /**
     * Sets the value of the statementName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatementName(String value) {
        this.statementName = value;
    }

    /**
     * Gets the value of the classKey property.
     * 
     * @return
     *     possible object is
     *     {@link CustomerClassKey }
     *     
     */
    public CustomerClassKey getClassKey() {
        return classKey;
    }

    /**
     * Sets the value of the classKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link CustomerClassKey }
     *     
     */
    public void setClassKey(CustomerClassKey value) {
        this.classKey = value;
    }

    /**
     * Gets the value of the priority property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getPriority() {
        return priority;
    }

    /**
     * Sets the value of the priority property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setPriority(Integer value) {
        this.priority = value;
    }

    /**
     * Gets the value of the defaultAddressKey property.
     * 
     * @return
     *     possible object is
     *     {@link CustomerAddressKey }
     *     
     */
    public CustomerAddressKey getDefaultAddressKey() {
        return defaultAddressKey;
    }

    /**
     * Sets the value of the defaultAddressKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link CustomerAddressKey }
     *     
     */
    public void setDefaultAddressKey(CustomerAddressKey value) {
        this.defaultAddressKey = value;
    }

    /**
     * Gets the value of the shipToAddressKey property.
     * 
     * @return
     *     possible object is
     *     {@link CustomerAddressKey }
     *     
     */
    public CustomerAddressKey getShipToAddressKey() {
        return shipToAddressKey;
    }

    /**
     * Sets the value of the shipToAddressKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link CustomerAddressKey }
     *     
     */
    public void setShipToAddressKey(CustomerAddressKey value) {
        this.shipToAddressKey = value;
    }

    /**
     * Gets the value of the billToAddressKey property.
     * 
     * @return
     *     possible object is
     *     {@link CustomerAddressKey }
     *     
     */
    public CustomerAddressKey getBillToAddressKey() {
        return billToAddressKey;
    }

    /**
     * Sets the value of the billToAddressKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link CustomerAddressKey }
     *     
     */
    public void setBillToAddressKey(CustomerAddressKey value) {
        this.billToAddressKey = value;
    }

    /**
     * Gets the value of the statementToAddressKey property.
     * 
     * @return
     *     possible object is
     *     {@link CustomerAddressKey }
     *     
     */
    public CustomerAddressKey getStatementToAddressKey() {
        return statementToAddressKey;
    }

    /**
     * Sets the value of the statementToAddressKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link CustomerAddressKey }
     *     
     */
    public void setStatementToAddressKey(CustomerAddressKey value) {
        this.statementToAddressKey = value;
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
     * Gets the value of the priceLevelKey property.
     * 
     * @return
     *     possible object is
     *     {@link PriceLevelKey }
     *     
     */
    public PriceLevelKey getPriceLevelKey() {
        return priceLevelKey;
    }

    /**
     * Sets the value of the priceLevelKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link PriceLevelKey }
     *     
     */
    public void setPriceLevelKey(PriceLevelKey value) {
        this.priceLevelKey = value;
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
     * Gets the value of the balanceType property.
     * 
     * @return
     *     possible object is
     *     {@link BalanceType }
     *     
     */
    public BalanceType getBalanceType() {
        return balanceType;
    }

    /**
     * Sets the value of the balanceType property.
     * 
     * @param value
     *     allowed object is
     *     {@link BalanceType }
     *     
     */
    public void setBalanceType(BalanceType value) {
        this.balanceType = value;
    }

    /**
     * Gets the value of the financeCharge property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyPercentChoice }
     *     
     */
    public MoneyPercentChoice getFinanceCharge() {
        return financeCharge;
    }

    /**
     * Sets the value of the financeCharge property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyPercentChoice }
     *     
     */
    public void setFinanceCharge(MoneyPercentChoice value) {
        this.financeCharge = value;
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
     * Gets the value of the creditLimit property.
     * 
     * @return
     *     possible object is
     *     {@link CustomerCreditLimit }
     *     
     */
    public CustomerCreditLimit getCreditLimit() {
        return creditLimit;
    }

    /**
     * Sets the value of the creditLimit property.
     * 
     * @param value
     *     allowed object is
     *     {@link CustomerCreditLimit }
     *     
     */
    public void setCreditLimit(CustomerCreditLimit value) {
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
     *     {@link ReceivablesPostResultsTo }
     *     
     */
    public ReceivablesPostResultsTo getPostResultsTo() {
        return postResultsTo;
    }

    /**
     * Sets the value of the postResultsTo property.
     * 
     * @param value
     *     allowed object is
     *     {@link ReceivablesPostResultsTo }
     *     
     */
    public void setPostResultsTo(ReceivablesPostResultsTo value) {
        this.postResultsTo = value;
    }

    /**
     * Gets the value of the orderFullfillmentShortageDefault property.
     * 
     * @return
     *     possible object is
     *     {@link OrderFullfillmentShortage }
     *     
     */
    public OrderFullfillmentShortage getOrderFullfillmentShortageDefault() {
        return orderFullfillmentShortageDefault;
    }

    /**
     * Sets the value of the orderFullfillmentShortageDefault property.
     * 
     * @param value
     *     allowed object is
     *     {@link OrderFullfillmentShortage }
     *     
     */
    public void setOrderFullfillmentShortageDefault(OrderFullfillmentShortage value) {
        this.orderFullfillmentShortageDefault = value;
    }

    /**
     * Gets the value of the includeInDemandPlanning property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIncludeInDemandPlanning() {
        return includeInDemandPlanning;
    }

    /**
     * Sets the value of the includeInDemandPlanning property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIncludeInDemandPlanning(Boolean value) {
        this.includeInDemandPlanning = value;
    }

    /**
     * Gets the value of the paymentCardAccount property.
     * 
     * @return
     *     possible object is
     *     {@link PaymentCardAccount }
     *     
     */
    public PaymentCardAccount getPaymentCardAccount() {
        return paymentCardAccount;
    }

    /**
     * Sets the value of the paymentCardAccount property.
     * 
     * @param value
     *     allowed object is
     *     {@link PaymentCardAccount }
     *     
     */
    public void setPaymentCardAccount(PaymentCardAccount value) {
        this.paymentCardAccount = value;
    }

    /**
     * Gets the value of the bankName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBankName() {
        return bankName;
    }

    /**
     * Sets the value of the bankName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBankName(String value) {
        this.bankName = value;
    }

    /**
     * Gets the value of the bankBranch property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBankBranch() {
        return bankBranch;
    }

    /**
     * Sets the value of the bankBranch property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBankBranch(String value) {
        this.bankBranch = value;
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
     * Gets the value of the taxExemptNumbers property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfString }
     *     
     */
    public ArrayOfString getTaxExemptNumbers() {
        return taxExemptNumbers;
    }

    /**
     * Sets the value of the taxExemptNumbers property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfString }
     *     
     */
    public void setTaxExemptNumbers(ArrayOfString value) {
        this.taxExemptNumbers = value;
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
     * Gets the value of the statementCycle property.
     * 
     * @return
     *     possible object is
     *     {@link StatementCycle }
     *     
     */
    public StatementCycle getStatementCycle() {
        return statementCycle;
    }

    /**
     * Sets the value of the statementCycle property.
     * 
     * @param value
     *     allowed object is
     *     {@link StatementCycle }
     *     
     */
    public void setStatementCycle(StatementCycle value) {
        this.statementCycle = value;
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
     * Gets the value of the sendEmailStatements property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isSendEmailStatements() {
        return sendEmailStatements;
    }

    /**
     * Sets the value of the sendEmailStatements property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setSendEmailStatements(Boolean value) {
        this.sendEmailStatements = value;
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
     * Gets the value of the defaultCashAccountType property.
     * 
     * @return
     *     possible object is
     *     {@link ReceivablesCashAccountType }
     *     
     */
    public ReceivablesCashAccountType getDefaultCashAccountType() {
        return defaultCashAccountType;
    }

    /**
     * Sets the value of the defaultCashAccountType property.
     * 
     * @param value
     *     allowed object is
     *     {@link ReceivablesCashAccountType }
     *     
     */
    public void setDefaultCashAccountType(ReceivablesCashAccountType value) {
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
     * Gets the value of the accountsReceivableGLAccountKey property.
     * 
     * @return
     *     possible object is
     *     {@link GLAccountNumberKey }
     *     
     */
    public GLAccountNumberKey getAccountsReceivableGLAccountKey() {
        return accountsReceivableGLAccountKey;
    }

    /**
     * Sets the value of the accountsReceivableGLAccountKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link GLAccountNumberKey }
     *     
     */
    public void setAccountsReceivableGLAccountKey(GLAccountNumberKey value) {
        this.accountsReceivableGLAccountKey = value;
    }

    /**
     * Gets the value of the salesGLAccountKey property.
     * 
     * @return
     *     possible object is
     *     {@link GLAccountNumberKey }
     *     
     */
    public GLAccountNumberKey getSalesGLAccountKey() {
        return salesGLAccountKey;
    }

    /**
     * Sets the value of the salesGLAccountKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link GLAccountNumberKey }
     *     
     */
    public void setSalesGLAccountKey(GLAccountNumberKey value) {
        this.salesGLAccountKey = value;
    }

    /**
     * Gets the value of the costOfGoodsSoldGLAccountKey property.
     * 
     * @return
     *     possible object is
     *     {@link GLAccountNumberKey }
     *     
     */
    public GLAccountNumberKey getCostOfGoodsSoldGLAccountKey() {
        return costOfGoodsSoldGLAccountKey;
    }

    /**
     * Sets the value of the costOfGoodsSoldGLAccountKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link GLAccountNumberKey }
     *     
     */
    public void setCostOfGoodsSoldGLAccountKey(GLAccountNumberKey value) {
        this.costOfGoodsSoldGLAccountKey = value;
    }

    /**
     * Gets the value of the inventoryGLAccountKey property.
     * 
     * @return
     *     possible object is
     *     {@link GLAccountNumberKey }
     *     
     */
    public GLAccountNumberKey getInventoryGLAccountKey() {
        return inventoryGLAccountKey;
    }

    /**
     * Sets the value of the inventoryGLAccountKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link GLAccountNumberKey }
     *     
     */
    public void setInventoryGLAccountKey(GLAccountNumberKey value) {
        this.inventoryGLAccountKey = value;
    }

    /**
     * Gets the value of the paymentTermsDiscountTakenGLAccountKey property.
     * 
     * @return
     *     possible object is
     *     {@link GLAccountNumberKey }
     *     
     */
    public GLAccountNumberKey getPaymentTermsDiscountTakenGLAccountKey() {
        return paymentTermsDiscountTakenGLAccountKey;
    }

    /**
     * Sets the value of the paymentTermsDiscountTakenGLAccountKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link GLAccountNumberKey }
     *     
     */
    public void setPaymentTermsDiscountTakenGLAccountKey(GLAccountNumberKey value) {
        this.paymentTermsDiscountTakenGLAccountKey = value;
    }

    /**
     * Gets the value of the paymentTermsDiscountAvailableGLAccountKey property.
     * 
     * @return
     *     possible object is
     *     {@link GLAccountNumberKey }
     *     
     */
    public GLAccountNumberKey getPaymentTermsDiscountAvailableGLAccountKey() {
        return paymentTermsDiscountAvailableGLAccountKey;
    }

    /**
     * Sets the value of the paymentTermsDiscountAvailableGLAccountKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link GLAccountNumberKey }
     *     
     */
    public void setPaymentTermsDiscountAvailableGLAccountKey(GLAccountNumberKey value) {
        this.paymentTermsDiscountAvailableGLAccountKey = value;
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
     * Gets the value of the salesOrderReturnsGLAccountKey property.
     * 
     * @return
     *     possible object is
     *     {@link GLAccountNumberKey }
     *     
     */
    public GLAccountNumberKey getSalesOrderReturnsGLAccountKey() {
        return salesOrderReturnsGLAccountKey;
    }

    /**
     * Sets the value of the salesOrderReturnsGLAccountKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link GLAccountNumberKey }
     *     
     */
    public void setSalesOrderReturnsGLAccountKey(GLAccountNumberKey value) {
        this.salesOrderReturnsGLAccountKey = value;
    }

    /**
     * Gets the value of the overpaymentWriteoffGLAccountKey property.
     * 
     * @return
     *     possible object is
     *     {@link GLAccountNumberKey }
     *     
     */
    public GLAccountNumberKey getOverpaymentWriteoffGLAccountKey() {
        return overpaymentWriteoffGLAccountKey;
    }

    /**
     * Sets the value of the overpaymentWriteoffGLAccountKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link GLAccountNumberKey }
     *     
     */
    public void setOverpaymentWriteoffGLAccountKey(GLAccountNumberKey value) {
        this.overpaymentWriteoffGLAccountKey = value;
    }

    /**
     * Gets the value of the statementRecipients property.
     * 
     * @return
     *     possible object is
     *     {@link EmailRecipients }
     *     
     */
    public EmailRecipients getStatementRecipients() {
        return statementRecipients;
    }

    /**
     * Sets the value of the statementRecipients property.
     * 
     * @param value
     *     allowed object is
     *     {@link EmailRecipients }
     *     
     */
    public void setStatementRecipients(EmailRecipients value) {
        this.statementRecipients = value;
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
     * Gets the value of the corporateAccountKey property.
     * 
     * @return
     *     possible object is
     *     {@link CustomerKey }
     *     
     */
    public CustomerKey getCorporateAccountKey() {
        return corporateAccountKey;
    }

    /**
     * Sets the value of the corporateAccountKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link CustomerKey }
     *     
     */
    public void setCorporateAccountKey(CustomerKey value) {
        this.corporateAccountKey = value;
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
     * Gets the value of the addresses property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfCustomerAddress }
     *     
     */
    public ArrayOfCustomerAddress getAddresses() {
        return addresses;
    }

    /**
     * Sets the value of the addresses property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfCustomerAddress }
     *     
     */
    public void setAddresses(ArrayOfCustomerAddress value) {
        this.addresses = value;
    }

}
