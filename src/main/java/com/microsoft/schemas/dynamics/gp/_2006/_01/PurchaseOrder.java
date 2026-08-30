
package com.microsoft.schemas.dynamics.gp._2006._01;

import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;

import com.microsoft.schemas.dynamics._2006._01.CompanyKey;


/**
 * <p>Java class for PurchaseOrder complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PurchaseOrder"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/gp/2006/01}BusinessObject"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Key" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}PurchaseTransactionKey" minOccurs="0"/&gt;
 *         &lt;element name="Type" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}PurchaseOrderType"/&gt;
 *         &lt;element name="VendorKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}VendorKey" minOccurs="0"/&gt;
 *         &lt;element name="VendorName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="Date" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="BuyerKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}BuyerKey" minOccurs="0"/&gt;
 *         &lt;element name="DoesAllowSalesOrderCommitments" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="CustomerKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}CustomerKey" minOccurs="0"/&gt;
 *         &lt;element name="ShipToAddressKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}AddressKey" minOccurs="0"/&gt;
 *         &lt;element name="ShipToAddress" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}BusinessAddress" minOccurs="0"/&gt;
 *         &lt;element name="PurchaseAddressKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}AddressKey" minOccurs="0"/&gt;
 *         &lt;element name="PurchaseAddress" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}BusinessAddress" minOccurs="0"/&gt;
 *         &lt;element name="BillToAddressKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}AddressKey" minOccurs="0"/&gt;
 *         &lt;element name="ShippingMethodKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ShippingMethodKey" minOccurs="0"/&gt;
 *         &lt;element name="PaymentTermsKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}PaymentTermsKey" minOccurs="0"/&gt;
 *         &lt;element name="Terms" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}Terms" minOccurs="0"/&gt;
 *         &lt;element name="TaxRegistrationNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="ConfirmWith" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="CommentKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}CommentKey" minOccurs="0"/&gt;
 *         &lt;element name="IsOnHold" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="TaxScheduleKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}TaxScheduleKey" minOccurs="0"/&gt;
 *         &lt;element name="FreightTaxBasis" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}PurchasingTaxBasis"/&gt;
 *         &lt;element name="MiscellaneousTaxBasis" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}PurchasingTaxBasis"/&gt;
 *         &lt;element name="FreightTaxScheduleKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}TaxScheduleKey" minOccurs="0"/&gt;
 *         &lt;element name="MiscellaneousTaxScheduleKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}TaxScheduleKey" minOccurs="0"/&gt;
 *         &lt;element name="CurrencyKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}CurrencyKey" minOccurs="0"/&gt;
 *         &lt;element name="ExchangeRate" type="{http://www.w3.org/2001/XMLSchema}decimal"/&gt;
 *         &lt;element name="ExchangeDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="CreatedBy" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="Status" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}PurchaseOrderStatus"/&gt;
 *         &lt;element name="Comment" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="PromisedDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="PromisedShipDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="RequestedDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="ContractEndDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="StatusGroup" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}StatusGroup"/&gt;
 *         &lt;element name="RequisitionDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="CompanyKey" type="{http://schemas.microsoft.com/dynamics/2006/01}CompanyKey" minOccurs="0"/&gt;
 *         &lt;element name="RemainingSubtotal" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="Subtotal" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="CanceledSubtotal" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="TradeDiscountAmount" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="FreightAmount" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="MiscellaneousAmount" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="TaxAmount" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="LastEditDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="LastPrintedDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="TimesPrinted" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="CreatedDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="ModifiedDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="RevisionNumber" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="TransactionState" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}PurchaseTransactionState"/&gt;
 *         &lt;element name="FreightTaxAmount" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="MiscellaneousTaxAmount" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="BackoutTaxAmount" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="BackoutFreightTaxAmount" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="BackoutMiscellaneousTaxAmount" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="FreightTaxes" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ArrayOfPurchaseTax" minOccurs="0"/&gt;
 *         &lt;element name="MiscellaneousTaxes" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ArrayOfPurchaseTax" minOccurs="0"/&gt;
 *         &lt;element name="Taxes" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ArrayOfPurchaseTax" minOccurs="0"/&gt;
 *         &lt;element name="Lines" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ArrayOfPurchaseOrderLine" minOccurs="0"/&gt;
 *         &lt;element name="WorkflowPriority" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}WorkflowPriority"/&gt;
 *         &lt;element name="Workflows" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ArrayOfWorkflow" minOccurs="0"/&gt;
 *         &lt;element name="ContractNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="TotalAmount" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PurchaseOrder", propOrder = {
    "key",
    "type",
    "vendorKey",
    "vendorName",
    "date",
    "buyerKey",
    "doesAllowSalesOrderCommitments",
    "customerKey",
    "shipToAddressKey",
    "shipToAddress",
    "purchaseAddressKey",
    "purchaseAddress",
    "billToAddressKey",
    "shippingMethodKey",
    "paymentTermsKey",
    "terms",
    "taxRegistrationNumber",
    "confirmWith",
    "commentKey",
    "isOnHold",
    "taxScheduleKey",
    "freightTaxBasis",
    "miscellaneousTaxBasis",
    "freightTaxScheduleKey",
    "miscellaneousTaxScheduleKey",
    "currencyKey",
    "exchangeRate",
    "exchangeDate",
    "createdBy",
    "status",
    "comment",
    "promisedDate",
    "promisedShipDate",
    "requestedDate",
    "contractEndDate",
    "statusGroup",
    "requisitionDate",
    "companyKey",
    "remainingSubtotal",
    "subtotal",
    "canceledSubtotal",
    "tradeDiscountAmount",
    "freightAmount",
    "miscellaneousAmount",
    "taxAmount",
    "lastEditDate",
    "lastPrintedDate",
    "timesPrinted",
    "createdDate",
    "modifiedDate",
    "revisionNumber",
    "transactionState",
    "freightTaxAmount",
    "miscellaneousTaxAmount",
    "backoutTaxAmount",
    "backoutFreightTaxAmount",
    "backoutMiscellaneousTaxAmount",
    "freightTaxes",
    "miscellaneousTaxes",
    "taxes",
    "lines",
    "workflowPriority",
    "workflows",
    "contractNumber",
    "totalAmount"
})
public class PurchaseOrder
    extends BusinessObject
{

    @XmlElement(name = "Key")
    protected PurchaseTransactionKey key;
    @XmlElement(name = "Type", required = true, nillable = true)
    @XmlSchemaType(name = "string")
    protected PurchaseOrderType type;
    @XmlElement(name = "VendorKey")
    protected VendorKey vendorKey;
    @XmlElement(name = "VendorName")
    protected String vendorName;
    @XmlElement(name = "Date", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar date;
    @XmlElement(name = "BuyerKey")
    protected BuyerKey buyerKey;
    @XmlElement(name = "DoesAllowSalesOrderCommitments", required = true, type = Boolean.class, nillable = true)
    protected Boolean doesAllowSalesOrderCommitments;
    @XmlElement(name = "CustomerKey")
    protected CustomerKey customerKey;
    @XmlElement(name = "ShipToAddressKey")
    protected AddressKey shipToAddressKey;
    @XmlElement(name = "ShipToAddress")
    protected BusinessAddress shipToAddress;
    @XmlElement(name = "PurchaseAddressKey")
    protected AddressKey purchaseAddressKey;
    @XmlElement(name = "PurchaseAddress")
    protected BusinessAddress purchaseAddress;
    @XmlElement(name = "BillToAddressKey")
    protected AddressKey billToAddressKey;
    @XmlElement(name = "ShippingMethodKey")
    protected ShippingMethodKey shippingMethodKey;
    @XmlElement(name = "PaymentTermsKey")
    protected PaymentTermsKey paymentTermsKey;
    @XmlElement(name = "Terms")
    protected Terms terms;
    @XmlElement(name = "TaxRegistrationNumber")
    protected String taxRegistrationNumber;
    @XmlElement(name = "ConfirmWith")
    protected String confirmWith;
    @XmlElement(name = "CommentKey")
    protected CommentKey commentKey;
    @XmlElement(name = "IsOnHold", required = true, type = Boolean.class, nillable = true)
    protected Boolean isOnHold;
    @XmlElement(name = "TaxScheduleKey")
    protected TaxScheduleKey taxScheduleKey;
    @XmlElement(name = "FreightTaxBasis", required = true, nillable = true)
    @XmlSchemaType(name = "string")
    protected PurchasingTaxBasis freightTaxBasis;
    @XmlElement(name = "MiscellaneousTaxBasis", required = true, nillable = true)
    @XmlSchemaType(name = "string")
    protected PurchasingTaxBasis miscellaneousTaxBasis;
    @XmlElement(name = "FreightTaxScheduleKey")
    protected TaxScheduleKey freightTaxScheduleKey;
    @XmlElement(name = "MiscellaneousTaxScheduleKey")
    protected TaxScheduleKey miscellaneousTaxScheduleKey;
    @XmlElement(name = "CurrencyKey")
    protected CurrencyKey currencyKey;
    @XmlElement(name = "ExchangeRate", required = true, nillable = true)
    protected BigDecimal exchangeRate;
    @XmlElement(name = "ExchangeDate", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar exchangeDate;
    @XmlElement(name = "CreatedBy")
    protected String createdBy;
    @XmlElement(name = "Status", required = true, nillable = true)
    @XmlSchemaType(name = "string")
    protected PurchaseOrderStatus status;
    @XmlElement(name = "Comment")
    protected String comment;
    @XmlElement(name = "PromisedDate", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar promisedDate;
    @XmlElement(name = "PromisedShipDate", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar promisedShipDate;
    @XmlElement(name = "RequestedDate", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar requestedDate;
    @XmlElement(name = "ContractEndDate", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar contractEndDate;
    @XmlElement(name = "StatusGroup", required = true, nillable = true)
    @XmlSchemaType(name = "string")
    protected StatusGroup statusGroup;
    @XmlElement(name = "RequisitionDate", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar requisitionDate;
    @XmlElement(name = "CompanyKey")
    protected CompanyKey companyKey;
    @XmlElement(name = "RemainingSubtotal")
    protected MoneyAmount remainingSubtotal;
    @XmlElement(name = "Subtotal")
    protected MoneyAmount subtotal;
    @XmlElement(name = "CanceledSubtotal")
    protected MoneyAmount canceledSubtotal;
    @XmlElement(name = "TradeDiscountAmount")
    protected MoneyAmount tradeDiscountAmount;
    @XmlElement(name = "FreightAmount")
    protected MoneyAmount freightAmount;
    @XmlElement(name = "MiscellaneousAmount")
    protected MoneyAmount miscellaneousAmount;
    @XmlElement(name = "TaxAmount")
    protected MoneyAmount taxAmount;
    @XmlElement(name = "LastEditDate", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar lastEditDate;
    @XmlElement(name = "LastPrintedDate", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar lastPrintedDate;
    @XmlElement(name = "TimesPrinted", required = true, type = Integer.class, nillable = true)
    protected Integer timesPrinted;
    @XmlElement(name = "CreatedDate", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar createdDate;
    @XmlElement(name = "ModifiedDate", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar modifiedDate;
    @XmlElement(name = "RevisionNumber", required = true, type = Integer.class, nillable = true)
    protected Integer revisionNumber;
    @XmlElement(name = "TransactionState", required = true, nillable = true)
    @XmlSchemaType(name = "string")
    protected PurchaseTransactionState transactionState;
    @XmlElement(name = "FreightTaxAmount")
    protected MoneyAmount freightTaxAmount;
    @XmlElement(name = "MiscellaneousTaxAmount")
    protected MoneyAmount miscellaneousTaxAmount;
    @XmlElement(name = "BackoutTaxAmount")
    protected MoneyAmount backoutTaxAmount;
    @XmlElement(name = "BackoutFreightTaxAmount")
    protected MoneyAmount backoutFreightTaxAmount;
    @XmlElement(name = "BackoutMiscellaneousTaxAmount")
    protected MoneyAmount backoutMiscellaneousTaxAmount;
    @XmlElement(name = "FreightTaxes")
    protected ArrayOfPurchaseTax freightTaxes;
    @XmlElement(name = "MiscellaneousTaxes")
    protected ArrayOfPurchaseTax miscellaneousTaxes;
    @XmlElement(name = "Taxes")
    protected ArrayOfPurchaseTax taxes;
    @XmlElement(name = "Lines")
    protected ArrayOfPurchaseOrderLine lines;
    @XmlElement(name = "WorkflowPriority", required = true, nillable = true)
    @XmlSchemaType(name = "string")
    protected WorkflowPriority workflowPriority;
    @XmlElement(name = "Workflows")
    protected ArrayOfWorkflow workflows;
    @XmlElement(name = "ContractNumber")
    protected String contractNumber;
    @XmlElement(name = "TotalAmount")
    protected MoneyAmount totalAmount;

    /**
     * Gets the value of the key property.
     * 
     * @return
     *     possible object is
     *     {@link PurchaseTransactionKey }
     *     
     */
    public PurchaseTransactionKey getKey() {
        return key;
    }

    /**
     * Sets the value of the key property.
     * 
     * @param value
     *     allowed object is
     *     {@link PurchaseTransactionKey }
     *     
     */
    public void setKey(PurchaseTransactionKey value) {
        this.key = value;
    }

    /**
     * Gets the value of the type property.
     * 
     * @return
     *     possible object is
     *     {@link PurchaseOrderType }
     *     
     */
    public PurchaseOrderType getType() {
        return type;
    }

    /**
     * Sets the value of the type property.
     * 
     * @param value
     *     allowed object is
     *     {@link PurchaseOrderType }
     *     
     */
    public void setType(PurchaseOrderType value) {
        this.type = value;
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
     * Gets the value of the buyerKey property.
     * 
     * @return
     *     possible object is
     *     {@link BuyerKey }
     *     
     */
    public BuyerKey getBuyerKey() {
        return buyerKey;
    }

    /**
     * Sets the value of the buyerKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link BuyerKey }
     *     
     */
    public void setBuyerKey(BuyerKey value) {
        this.buyerKey = value;
    }

    /**
     * Gets the value of the doesAllowSalesOrderCommitments property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isDoesAllowSalesOrderCommitments() {
        return doesAllowSalesOrderCommitments;
    }

    /**
     * Sets the value of the doesAllowSalesOrderCommitments property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setDoesAllowSalesOrderCommitments(Boolean value) {
        this.doesAllowSalesOrderCommitments = value;
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
     * Gets the value of the shipToAddressKey property.
     * 
     * @return
     *     possible object is
     *     {@link AddressKey }
     *     
     */
    public AddressKey getShipToAddressKey() {
        return shipToAddressKey;
    }

    /**
     * Sets the value of the shipToAddressKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link AddressKey }
     *     
     */
    public void setShipToAddressKey(AddressKey value) {
        this.shipToAddressKey = value;
    }

    /**
     * Gets the value of the shipToAddress property.
     * 
     * @return
     *     possible object is
     *     {@link BusinessAddress }
     *     
     */
    public BusinessAddress getShipToAddress() {
        return shipToAddress;
    }

    /**
     * Sets the value of the shipToAddress property.
     * 
     * @param value
     *     allowed object is
     *     {@link BusinessAddress }
     *     
     */
    public void setShipToAddress(BusinessAddress value) {
        this.shipToAddress = value;
    }

    /**
     * Gets the value of the purchaseAddressKey property.
     * 
     * @return
     *     possible object is
     *     {@link AddressKey }
     *     
     */
    public AddressKey getPurchaseAddressKey() {
        return purchaseAddressKey;
    }

    /**
     * Sets the value of the purchaseAddressKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link AddressKey }
     *     
     */
    public void setPurchaseAddressKey(AddressKey value) {
        this.purchaseAddressKey = value;
    }

    /**
     * Gets the value of the purchaseAddress property.
     * 
     * @return
     *     possible object is
     *     {@link BusinessAddress }
     *     
     */
    public BusinessAddress getPurchaseAddress() {
        return purchaseAddress;
    }

    /**
     * Sets the value of the purchaseAddress property.
     * 
     * @param value
     *     allowed object is
     *     {@link BusinessAddress }
     *     
     */
    public void setPurchaseAddress(BusinessAddress value) {
        this.purchaseAddress = value;
    }

    /**
     * Gets the value of the billToAddressKey property.
     * 
     * @return
     *     possible object is
     *     {@link AddressKey }
     *     
     */
    public AddressKey getBillToAddressKey() {
        return billToAddressKey;
    }

    /**
     * Sets the value of the billToAddressKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link AddressKey }
     *     
     */
    public void setBillToAddressKey(AddressKey value) {
        this.billToAddressKey = value;
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
     * Gets the value of the terms property.
     * 
     * @return
     *     possible object is
     *     {@link Terms }
     *     
     */
    public Terms getTerms() {
        return terms;
    }

    /**
     * Sets the value of the terms property.
     * 
     * @param value
     *     allowed object is
     *     {@link Terms }
     *     
     */
    public void setTerms(Terms value) {
        this.terms = value;
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
     * Gets the value of the confirmWith property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getConfirmWith() {
        return confirmWith;
    }

    /**
     * Sets the value of the confirmWith property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setConfirmWith(String value) {
        this.confirmWith = value;
    }

    /**
     * Gets the value of the commentKey property.
     * 
     * @return
     *     possible object is
     *     {@link CommentKey }
     *     
     */
    public CommentKey getCommentKey() {
        return commentKey;
    }

    /**
     * Sets the value of the commentKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link CommentKey }
     *     
     */
    public void setCommentKey(CommentKey value) {
        this.commentKey = value;
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
     * Gets the value of the freightTaxBasis property.
     * 
     * @return
     *     possible object is
     *     {@link PurchasingTaxBasis }
     *     
     */
    public PurchasingTaxBasis getFreightTaxBasis() {
        return freightTaxBasis;
    }

    /**
     * Sets the value of the freightTaxBasis property.
     * 
     * @param value
     *     allowed object is
     *     {@link PurchasingTaxBasis }
     *     
     */
    public void setFreightTaxBasis(PurchasingTaxBasis value) {
        this.freightTaxBasis = value;
    }

    /**
     * Gets the value of the miscellaneousTaxBasis property.
     * 
     * @return
     *     possible object is
     *     {@link PurchasingTaxBasis }
     *     
     */
    public PurchasingTaxBasis getMiscellaneousTaxBasis() {
        return miscellaneousTaxBasis;
    }

    /**
     * Sets the value of the miscellaneousTaxBasis property.
     * 
     * @param value
     *     allowed object is
     *     {@link PurchasingTaxBasis }
     *     
     */
    public void setMiscellaneousTaxBasis(PurchasingTaxBasis value) {
        this.miscellaneousTaxBasis = value;
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
     * Gets the value of the createdBy property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * Sets the value of the createdBy property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCreatedBy(String value) {
        this.createdBy = value;
    }

    /**
     * Gets the value of the status property.
     * 
     * @return
     *     possible object is
     *     {@link PurchaseOrderStatus }
     *     
     */
    public PurchaseOrderStatus getStatus() {
        return status;
    }

    /**
     * Sets the value of the status property.
     * 
     * @param value
     *     allowed object is
     *     {@link PurchaseOrderStatus }
     *     
     */
    public void setStatus(PurchaseOrderStatus value) {
        this.status = value;
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
     * Gets the value of the promisedDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getPromisedDate() {
        return promisedDate;
    }

    /**
     * Sets the value of the promisedDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setPromisedDate(XMLGregorianCalendar value) {
        this.promisedDate = value;
    }

    /**
     * Gets the value of the promisedShipDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getPromisedShipDate() {
        return promisedShipDate;
    }

    /**
     * Sets the value of the promisedShipDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setPromisedShipDate(XMLGregorianCalendar value) {
        this.promisedShipDate = value;
    }

    /**
     * Gets the value of the requestedDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getRequestedDate() {
        return requestedDate;
    }

    /**
     * Sets the value of the requestedDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setRequestedDate(XMLGregorianCalendar value) {
        this.requestedDate = value;
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
     * Gets the value of the statusGroup property.
     * 
     * @return
     *     possible object is
     *     {@link StatusGroup }
     *     
     */
    public StatusGroup getStatusGroup() {
        return statusGroup;
    }

    /**
     * Sets the value of the statusGroup property.
     * 
     * @param value
     *     allowed object is
     *     {@link StatusGroup }
     *     
     */
    public void setStatusGroup(StatusGroup value) {
        this.statusGroup = value;
    }

    /**
     * Gets the value of the requisitionDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getRequisitionDate() {
        return requisitionDate;
    }

    /**
     * Sets the value of the requisitionDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setRequisitionDate(XMLGregorianCalendar value) {
        this.requisitionDate = value;
    }

    /**
     * Gets the value of the companyKey property.
     * 
     * @return
     *     possible object is
     *     {@link CompanyKey }
     *     
     */
    public CompanyKey getCompanyKey() {
        return companyKey;
    }

    /**
     * Sets the value of the companyKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link CompanyKey }
     *     
     */
    public void setCompanyKey(CompanyKey value) {
        this.companyKey = value;
    }

    /**
     * Gets the value of the remainingSubtotal property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getRemainingSubtotal() {
        return remainingSubtotal;
    }

    /**
     * Sets the value of the remainingSubtotal property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setRemainingSubtotal(MoneyAmount value) {
        this.remainingSubtotal = value;
    }

    /**
     * Gets the value of the subtotal property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getSubtotal() {
        return subtotal;
    }

    /**
     * Sets the value of the subtotal property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setSubtotal(MoneyAmount value) {
        this.subtotal = value;
    }

    /**
     * Gets the value of the canceledSubtotal property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getCanceledSubtotal() {
        return canceledSubtotal;
    }

    /**
     * Sets the value of the canceledSubtotal property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setCanceledSubtotal(MoneyAmount value) {
        this.canceledSubtotal = value;
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
     * Gets the value of the lastEditDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getLastEditDate() {
        return lastEditDate;
    }

    /**
     * Sets the value of the lastEditDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setLastEditDate(XMLGregorianCalendar value) {
        this.lastEditDate = value;
    }

    /**
     * Gets the value of the lastPrintedDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getLastPrintedDate() {
        return lastPrintedDate;
    }

    /**
     * Sets the value of the lastPrintedDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setLastPrintedDate(XMLGregorianCalendar value) {
        this.lastPrintedDate = value;
    }

    /**
     * Gets the value of the timesPrinted property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getTimesPrinted() {
        return timesPrinted;
    }

    /**
     * Sets the value of the timesPrinted property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setTimesPrinted(Integer value) {
        this.timesPrinted = value;
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
     * Gets the value of the revisionNumber property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getRevisionNumber() {
        return revisionNumber;
    }

    /**
     * Sets the value of the revisionNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setRevisionNumber(Integer value) {
        this.revisionNumber = value;
    }

    /**
     * Gets the value of the transactionState property.
     * 
     * @return
     *     possible object is
     *     {@link PurchaseTransactionState }
     *     
     */
    public PurchaseTransactionState getTransactionState() {
        return transactionState;
    }

    /**
     * Sets the value of the transactionState property.
     * 
     * @param value
     *     allowed object is
     *     {@link PurchaseTransactionState }
     *     
     */
    public void setTransactionState(PurchaseTransactionState value) {
        this.transactionState = value;
    }

    /**
     * Gets the value of the freightTaxAmount property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getFreightTaxAmount() {
        return freightTaxAmount;
    }

    /**
     * Sets the value of the freightTaxAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setFreightTaxAmount(MoneyAmount value) {
        this.freightTaxAmount = value;
    }

    /**
     * Gets the value of the miscellaneousTaxAmount property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getMiscellaneousTaxAmount() {
        return miscellaneousTaxAmount;
    }

    /**
     * Sets the value of the miscellaneousTaxAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setMiscellaneousTaxAmount(MoneyAmount value) {
        this.miscellaneousTaxAmount = value;
    }

    /**
     * Gets the value of the backoutTaxAmount property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getBackoutTaxAmount() {
        return backoutTaxAmount;
    }

    /**
     * Sets the value of the backoutTaxAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setBackoutTaxAmount(MoneyAmount value) {
        this.backoutTaxAmount = value;
    }

    /**
     * Gets the value of the backoutFreightTaxAmount property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getBackoutFreightTaxAmount() {
        return backoutFreightTaxAmount;
    }

    /**
     * Sets the value of the backoutFreightTaxAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setBackoutFreightTaxAmount(MoneyAmount value) {
        this.backoutFreightTaxAmount = value;
    }

    /**
     * Gets the value of the backoutMiscellaneousTaxAmount property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getBackoutMiscellaneousTaxAmount() {
        return backoutMiscellaneousTaxAmount;
    }

    /**
     * Sets the value of the backoutMiscellaneousTaxAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setBackoutMiscellaneousTaxAmount(MoneyAmount value) {
        this.backoutMiscellaneousTaxAmount = value;
    }

    /**
     * Gets the value of the freightTaxes property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfPurchaseTax }
     *     
     */
    public ArrayOfPurchaseTax getFreightTaxes() {
        return freightTaxes;
    }

    /**
     * Sets the value of the freightTaxes property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfPurchaseTax }
     *     
     */
    public void setFreightTaxes(ArrayOfPurchaseTax value) {
        this.freightTaxes = value;
    }

    /**
     * Gets the value of the miscellaneousTaxes property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfPurchaseTax }
     *     
     */
    public ArrayOfPurchaseTax getMiscellaneousTaxes() {
        return miscellaneousTaxes;
    }

    /**
     * Sets the value of the miscellaneousTaxes property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfPurchaseTax }
     *     
     */
    public void setMiscellaneousTaxes(ArrayOfPurchaseTax value) {
        this.miscellaneousTaxes = value;
    }

    /**
     * Gets the value of the taxes property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfPurchaseTax }
     *     
     */
    public ArrayOfPurchaseTax getTaxes() {
        return taxes;
    }

    /**
     * Sets the value of the taxes property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfPurchaseTax }
     *     
     */
    public void setTaxes(ArrayOfPurchaseTax value) {
        this.taxes = value;
    }

    /**
     * Gets the value of the lines property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfPurchaseOrderLine }
     *     
     */
    public ArrayOfPurchaseOrderLine getLines() {
        return lines;
    }

    /**
     * Sets the value of the lines property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfPurchaseOrderLine }
     *     
     */
    public void setLines(ArrayOfPurchaseOrderLine value) {
        this.lines = value;
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
     * Gets the value of the contractNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getContractNumber() {
        return contractNumber;
    }

    /**
     * Sets the value of the contractNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setContractNumber(String value) {
        this.contractNumber = value;
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

}
