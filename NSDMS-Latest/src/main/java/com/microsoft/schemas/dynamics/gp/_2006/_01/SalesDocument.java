
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
 * <p>Java class for SalesDocument complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SalesDocument"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/gp/2006/01}BusinessObject"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Key" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}SalesDocumentKey" minOccurs="0"/&gt;
 *         &lt;element name="Type" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}SalesDocumentType"/&gt;
 *         &lt;element name="Date" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="FreightAmount" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="MiscellaneousAmount" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="TaxAmount" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="TradeDiscount" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyPercentChoice" minOccurs="0"/&gt;
 *         &lt;element name="DiscountAmount" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="CustomerName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="CustomerPONumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="UPSZone" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="MasterNumber" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="Reference" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="CommissionAmount" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="ExchangeRate" type="{http://www.w3.org/2001/XMLSchema}decimal"/&gt;
 *         &lt;element name="ExchangeDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="RequestedShipDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="Comment" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="TaxExemptNumber1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="TaxExemptNumber2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="TaxRegistrationNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="FreightTaxBasis" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}SalesTaxBasis"/&gt;
 *         &lt;element name="MiscellaneousTaxBasis" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}SalesTaxBasis"/&gt;
 *         &lt;element name="FreightTaxAmount" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="MiscellaneousTaxAmount" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="Note" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="TransactionState" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}SalesTransactionState"/&gt;
 *         &lt;element name="TaxScheduleKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}TaxScheduleKey" minOccurs="0"/&gt;
 *         &lt;element name="FreightTaxScheduleKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}TaxScheduleKey" minOccurs="0"/&gt;
 *         &lt;element name="MiscellaneousTaxScheduleKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}TaxScheduleKey" minOccurs="0"/&gt;
 *         &lt;element name="LineTotalAmount" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="TotalAmount" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="CreatedBy" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="CreatedDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="ModifiedDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="LastModifiedDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="CommissionBasedOn" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}CommissionBasedOn"/&gt;
 *         &lt;element name="CommissionSaleAmount" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="ActualShipDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="FulfillDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="QuoteDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="AuditTrailCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="IsIntrastatDocument" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="IsVoided" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="ShipCompleteOnly" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="FrontOfficeIntegrationId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="IntegrationSource" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="IntegrationId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="InvoiceDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="BackorderDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="ReturnDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="OriginalSalesDocumentKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}SalesDocumentKey" minOccurs="0"/&gt;
 *         &lt;element name="OriginalSalesDocumentType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="DocumentTypeKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}SalesDocumentTypeKey" minOccurs="0"/&gt;
 *         &lt;element name="ShippingMethodKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ShippingMethodKey" minOccurs="0"/&gt;
 *         &lt;element name="WarehouseKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}WarehouseKey" minOccurs="0"/&gt;
 *         &lt;element name="CustomerKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}CustomerKey" minOccurs="0"/&gt;
 *         &lt;element name="SalesTerritoryKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}SalesTerritoryKey" minOccurs="0"/&gt;
 *         &lt;element name="SalespersonKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}SalespersonKey" minOccurs="0"/&gt;
 *         &lt;element name="BatchKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}BatchKey" minOccurs="0"/&gt;
 *         &lt;element name="BillToAddressKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}CustomerAddressKey" minOccurs="0"/&gt;
 *         &lt;element name="ShipToAddressKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}CustomerAddressKey" minOccurs="0"/&gt;
 *         &lt;element name="PaymentTermsKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}PaymentTermsKey" minOccurs="0"/&gt;
 *         &lt;element name="CurrencyKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}CurrencyKey" minOccurs="0"/&gt;
 *         &lt;element name="CommentKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}CommentKey" minOccurs="0"/&gt;
 *         &lt;element name="PriceLevelKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}PriceLevelKey" minOccurs="0"/&gt;
 *         &lt;element name="ShipToAddress" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}BusinessAddress" minOccurs="0"/&gt;
 *         &lt;element name="UserDefined" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}SalesUserDefined" minOccurs="0"/&gt;
 *         &lt;element name="Commissions" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ArrayOfSalesCommission" minOccurs="0"/&gt;
 *         &lt;element name="ProcessHolds" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ArrayOfSalesProcessHold" minOccurs="0"/&gt;
 *         &lt;element name="Taxes" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ArrayOfSalesDocumentTax" minOccurs="0"/&gt;
 *         &lt;element name="FreightTaxes" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ArrayOfSalesDocumentTax" minOccurs="0"/&gt;
 *         &lt;element name="MiscellaneousTaxes" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ArrayOfSalesDocumentTax" minOccurs="0"/&gt;
 *         &lt;element name="TrackingNumbers" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ArrayOfSalesTrackingNumber" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SalesDocument", propOrder = {
    "key",
    "type",
    "date",
    "freightAmount",
    "miscellaneousAmount",
    "taxAmount",
    "tradeDiscount",
    "discountAmount",
    "customerName",
    "customerPONumber",
    "upsZone",
    "masterNumber",
    "reference",
    "commissionAmount",
    "exchangeRate",
    "exchangeDate",
    "requestedShipDate",
    "comment",
    "taxExemptNumber1",
    "taxExemptNumber2",
    "taxRegistrationNumber",
    "freightTaxBasis",
    "miscellaneousTaxBasis",
    "freightTaxAmount",
    "miscellaneousTaxAmount",
    "note",
    "transactionState",
    "taxScheduleKey",
    "freightTaxScheduleKey",
    "miscellaneousTaxScheduleKey",
    "lineTotalAmount",
    "totalAmount",
    "createdBy",
    "createdDate",
    "modifiedDate",
    "lastModifiedDate",
    "commissionBasedOn",
    "commissionSaleAmount",
    "actualShipDate",
    "fulfillDate",
    "quoteDate",
    "auditTrailCode",
    "isIntrastatDocument",
    "isVoided",
    "shipCompleteOnly",
    "frontOfficeIntegrationId",
    "integrationSource",
    "integrationId",
    "invoiceDate",
    "backorderDate",
    "returnDate",
    "originalSalesDocumentKey",
    "originalSalesDocumentType",
    "documentTypeKey",
    "shippingMethodKey",
    "warehouseKey",
    "customerKey",
    "salesTerritoryKey",
    "salespersonKey",
    "batchKey",
    "billToAddressKey",
    "shipToAddressKey",
    "paymentTermsKey",
    "currencyKey",
    "commentKey",
    "priceLevelKey",
    "shipToAddress",
    "userDefined",
    "commissions",
    "processHolds",
    "taxes",
    "freightTaxes",
    "miscellaneousTaxes",
    "trackingNumbers"
})
@XmlSeeAlso({
    SalesOrder.class,
    SalesInvoice.class,
    SalesReturn.class,
    SalesBackorder.class,
    SalesFulfillmentOrder.class,
    SalesQuote.class
})
public abstract class SalesDocument
    extends BusinessObject
{

    @XmlElement(name = "Key")
    protected SalesDocumentKey key;
    @XmlElement(name = "Type", required = true, nillable = true)
    @XmlSchemaType(name = "string")
    protected SalesDocumentType type;
    @XmlElement(name = "Date", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar date;
    @XmlElement(name = "FreightAmount")
    protected MoneyAmount freightAmount;
    @XmlElement(name = "MiscellaneousAmount")
    protected MoneyAmount miscellaneousAmount;
    @XmlElement(name = "TaxAmount")
    protected MoneyAmount taxAmount;
    @XmlElement(name = "TradeDiscount")
    protected MoneyPercentChoice tradeDiscount;
    @XmlElement(name = "DiscountAmount")
    protected MoneyAmount discountAmount;
    @XmlElement(name = "CustomerName")
    protected String customerName;
    @XmlElement(name = "CustomerPONumber")
    protected String customerPONumber;
    @XmlElement(name = "UPSZone")
    protected String upsZone;
    @XmlElement(name = "MasterNumber", required = true, type = Integer.class, nillable = true)
    protected Integer masterNumber;
    @XmlElement(name = "Reference")
    protected String reference;
    @XmlElement(name = "CommissionAmount")
    protected MoneyAmount commissionAmount;
    @XmlElement(name = "ExchangeRate", required = true, nillable = true)
    protected BigDecimal exchangeRate;
    @XmlElement(name = "ExchangeDate", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar exchangeDate;
    @XmlElement(name = "RequestedShipDate", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar requestedShipDate;
    @XmlElement(name = "Comment")
    protected String comment;
    @XmlElement(name = "TaxExemptNumber1")
    protected String taxExemptNumber1;
    @XmlElement(name = "TaxExemptNumber2")
    protected String taxExemptNumber2;
    @XmlElement(name = "TaxRegistrationNumber")
    protected String taxRegistrationNumber;
    @XmlElement(name = "FreightTaxBasis", required = true, nillable = true)
    @XmlSchemaType(name = "string")
    protected SalesTaxBasis freightTaxBasis;
    @XmlElement(name = "MiscellaneousTaxBasis", required = true, nillable = true)
    @XmlSchemaType(name = "string")
    protected SalesTaxBasis miscellaneousTaxBasis;
    @XmlElement(name = "FreightTaxAmount")
    protected MoneyAmount freightTaxAmount;
    @XmlElement(name = "MiscellaneousTaxAmount")
    protected MoneyAmount miscellaneousTaxAmount;
    @XmlElement(name = "Note")
    protected String note;
    @XmlElement(name = "TransactionState", required = true, nillable = true)
    @XmlSchemaType(name = "string")
    protected SalesTransactionState transactionState;
    @XmlElement(name = "TaxScheduleKey")
    protected TaxScheduleKey taxScheduleKey;
    @XmlElement(name = "FreightTaxScheduleKey")
    protected TaxScheduleKey freightTaxScheduleKey;
    @XmlElement(name = "MiscellaneousTaxScheduleKey")
    protected TaxScheduleKey miscellaneousTaxScheduleKey;
    @XmlElement(name = "LineTotalAmount")
    protected MoneyAmount lineTotalAmount;
    @XmlElement(name = "TotalAmount")
    protected MoneyAmount totalAmount;
    @XmlElement(name = "CreatedBy")
    protected String createdBy;
    @XmlElement(name = "CreatedDate", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar createdDate;
    @XmlElement(name = "ModifiedDate", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar modifiedDate;
    @XmlElement(name = "LastModifiedDate", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar lastModifiedDate;
    @XmlElement(name = "CommissionBasedOn", required = true, nillable = true)
    @XmlSchemaType(name = "string")
    protected CommissionBasedOn commissionBasedOn;
    @XmlElement(name = "CommissionSaleAmount")
    protected MoneyAmount commissionSaleAmount;
    @XmlElement(name = "ActualShipDate", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar actualShipDate;
    @XmlElement(name = "FulfillDate", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar fulfillDate;
    @XmlElement(name = "QuoteDate", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar quoteDate;
    @XmlElement(name = "AuditTrailCode")
    protected String auditTrailCode;
    @XmlElement(name = "IsIntrastatDocument", required = true, type = Boolean.class, nillable = true)
    protected Boolean isIntrastatDocument;
    @XmlElement(name = "IsVoided", required = true, type = Boolean.class, nillable = true)
    protected Boolean isVoided;
    @XmlElement(name = "ShipCompleteOnly", required = true, type = Boolean.class, nillable = true)
    protected Boolean shipCompleteOnly;
    @XmlElement(name = "FrontOfficeIntegrationId")
    protected String frontOfficeIntegrationId;
    @XmlElement(name = "IntegrationSource", required = true, type = Integer.class, nillable = true)
    protected Integer integrationSource;
    @XmlElement(name = "IntegrationId")
    protected String integrationId;
    @XmlElement(name = "InvoiceDate", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar invoiceDate;
    @XmlElement(name = "BackorderDate", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar backorderDate;
    @XmlElement(name = "ReturnDate", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar returnDate;
    @XmlElement(name = "OriginalSalesDocumentKey")
    protected SalesDocumentKey originalSalesDocumentKey;
    @XmlElement(name = "OriginalSalesDocumentType")
    protected String originalSalesDocumentType;
    @XmlElement(name = "DocumentTypeKey")
    protected SalesDocumentTypeKey documentTypeKey;
    @XmlElement(name = "ShippingMethodKey")
    protected ShippingMethodKey shippingMethodKey;
    @XmlElement(name = "WarehouseKey")
    protected WarehouseKey warehouseKey;
    @XmlElement(name = "CustomerKey")
    protected CustomerKey customerKey;
    @XmlElement(name = "SalesTerritoryKey")
    protected SalesTerritoryKey salesTerritoryKey;
    @XmlElement(name = "SalespersonKey")
    protected SalespersonKey salespersonKey;
    @XmlElement(name = "BatchKey")
    protected BatchKey batchKey;
    @XmlElement(name = "BillToAddressKey")
    protected CustomerAddressKey billToAddressKey;
    @XmlElement(name = "ShipToAddressKey")
    protected CustomerAddressKey shipToAddressKey;
    @XmlElement(name = "PaymentTermsKey")
    protected PaymentTermsKey paymentTermsKey;
    @XmlElement(name = "CurrencyKey")
    protected CurrencyKey currencyKey;
    @XmlElement(name = "CommentKey")
    protected CommentKey commentKey;
    @XmlElement(name = "PriceLevelKey")
    protected PriceLevelKey priceLevelKey;
    @XmlElement(name = "ShipToAddress")
    protected BusinessAddress shipToAddress;
    @XmlElement(name = "UserDefined")
    protected SalesUserDefined userDefined;
    @XmlElement(name = "Commissions")
    protected ArrayOfSalesCommission commissions;
    @XmlElement(name = "ProcessHolds")
    protected ArrayOfSalesProcessHold processHolds;
    @XmlElement(name = "Taxes")
    protected ArrayOfSalesDocumentTax taxes;
    @XmlElement(name = "FreightTaxes")
    protected ArrayOfSalesDocumentTax freightTaxes;
    @XmlElement(name = "MiscellaneousTaxes")
    protected ArrayOfSalesDocumentTax miscellaneousTaxes;
    @XmlElement(name = "TrackingNumbers")
    protected ArrayOfSalesTrackingNumber trackingNumbers;

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
     * Gets the value of the type property.
     * 
     * @return
     *     possible object is
     *     {@link SalesDocumentType }
     *     
     */
    public SalesDocumentType getType() {
        return type;
    }

    /**
     * Sets the value of the type property.
     * 
     * @param value
     *     allowed object is
     *     {@link SalesDocumentType }
     *     
     */
    public void setType(SalesDocumentType value) {
        this.type = value;
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
     * Gets the value of the tradeDiscount property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyPercentChoice }
     *     
     */
    public MoneyPercentChoice getTradeDiscount() {
        return tradeDiscount;
    }

    /**
     * Sets the value of the tradeDiscount property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyPercentChoice }
     *     
     */
    public void setTradeDiscount(MoneyPercentChoice value) {
        this.tradeDiscount = value;
    }

    /**
     * Gets the value of the discountAmount property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getDiscountAmount() {
        return discountAmount;
    }

    /**
     * Sets the value of the discountAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setDiscountAmount(MoneyAmount value) {
        this.discountAmount = value;
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
     * Gets the value of the upsZone property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUPSZone() {
        return upsZone;
    }

    /**
     * Sets the value of the upsZone property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUPSZone(String value) {
        this.upsZone = value;
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
     * Gets the value of the reference property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReference() {
        return reference;
    }

    /**
     * Sets the value of the reference property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReference(String value) {
        this.reference = value;
    }

    /**
     * Gets the value of the commissionAmount property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getCommissionAmount() {
        return commissionAmount;
    }

    /**
     * Sets the value of the commissionAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setCommissionAmount(MoneyAmount value) {
        this.commissionAmount = value;
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
     * Gets the value of the requestedShipDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getRequestedShipDate() {
        return requestedShipDate;
    }

    /**
     * Sets the value of the requestedShipDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setRequestedShipDate(XMLGregorianCalendar value) {
        this.requestedShipDate = value;
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
     * Gets the value of the taxExemptNumber1 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTaxExemptNumber1() {
        return taxExemptNumber1;
    }

    /**
     * Sets the value of the taxExemptNumber1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTaxExemptNumber1(String value) {
        this.taxExemptNumber1 = value;
    }

    /**
     * Gets the value of the taxExemptNumber2 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTaxExemptNumber2() {
        return taxExemptNumber2;
    }

    /**
     * Sets the value of the taxExemptNumber2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTaxExemptNumber2(String value) {
        this.taxExemptNumber2 = value;
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
     * Gets the value of the freightTaxBasis property.
     * 
     * @return
     *     possible object is
     *     {@link SalesTaxBasis }
     *     
     */
    public SalesTaxBasis getFreightTaxBasis() {
        return freightTaxBasis;
    }

    /**
     * Sets the value of the freightTaxBasis property.
     * 
     * @param value
     *     allowed object is
     *     {@link SalesTaxBasis }
     *     
     */
    public void setFreightTaxBasis(SalesTaxBasis value) {
        this.freightTaxBasis = value;
    }

    /**
     * Gets the value of the miscellaneousTaxBasis property.
     * 
     * @return
     *     possible object is
     *     {@link SalesTaxBasis }
     *     
     */
    public SalesTaxBasis getMiscellaneousTaxBasis() {
        return miscellaneousTaxBasis;
    }

    /**
     * Sets the value of the miscellaneousTaxBasis property.
     * 
     * @param value
     *     allowed object is
     *     {@link SalesTaxBasis }
     *     
     */
    public void setMiscellaneousTaxBasis(SalesTaxBasis value) {
        this.miscellaneousTaxBasis = value;
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
     * Gets the value of the note property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNote() {
        return note;
    }

    /**
     * Sets the value of the note property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNote(String value) {
        this.note = value;
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
     * Gets the value of the commissionBasedOn property.
     * 
     * @return
     *     possible object is
     *     {@link CommissionBasedOn }
     *     
     */
    public CommissionBasedOn getCommissionBasedOn() {
        return commissionBasedOn;
    }

    /**
     * Sets the value of the commissionBasedOn property.
     * 
     * @param value
     *     allowed object is
     *     {@link CommissionBasedOn }
     *     
     */
    public void setCommissionBasedOn(CommissionBasedOn value) {
        this.commissionBasedOn = value;
    }

    /**
     * Gets the value of the commissionSaleAmount property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getCommissionSaleAmount() {
        return commissionSaleAmount;
    }

    /**
     * Sets the value of the commissionSaleAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setCommissionSaleAmount(MoneyAmount value) {
        this.commissionSaleAmount = value;
    }

    /**
     * Gets the value of the actualShipDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getActualShipDate() {
        return actualShipDate;
    }

    /**
     * Sets the value of the actualShipDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setActualShipDate(XMLGregorianCalendar value) {
        this.actualShipDate = value;
    }

    /**
     * Gets the value of the fulfillDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getFulfillDate() {
        return fulfillDate;
    }

    /**
     * Sets the value of the fulfillDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setFulfillDate(XMLGregorianCalendar value) {
        this.fulfillDate = value;
    }

    /**
     * Gets the value of the quoteDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getQuoteDate() {
        return quoteDate;
    }

    /**
     * Sets the value of the quoteDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setQuoteDate(XMLGregorianCalendar value) {
        this.quoteDate = value;
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
     * Gets the value of the isIntrastatDocument property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsIntrastatDocument() {
        return isIntrastatDocument;
    }

    /**
     * Sets the value of the isIntrastatDocument property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsIntrastatDocument(Boolean value) {
        this.isIntrastatDocument = value;
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
     * Gets the value of the frontOfficeIntegrationId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFrontOfficeIntegrationId() {
        return frontOfficeIntegrationId;
    }

    /**
     * Sets the value of the frontOfficeIntegrationId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFrontOfficeIntegrationId(String value) {
        this.frontOfficeIntegrationId = value;
    }

    /**
     * Gets the value of the integrationSource property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getIntegrationSource() {
        return integrationSource;
    }

    /**
     * Sets the value of the integrationSource property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setIntegrationSource(Integer value) {
        this.integrationSource = value;
    }

    /**
     * Gets the value of the integrationId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIntegrationId() {
        return integrationId;
    }

    /**
     * Sets the value of the integrationId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIntegrationId(String value) {
        this.integrationId = value;
    }

    /**
     * Gets the value of the invoiceDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getInvoiceDate() {
        return invoiceDate;
    }

    /**
     * Sets the value of the invoiceDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setInvoiceDate(XMLGregorianCalendar value) {
        this.invoiceDate = value;
    }

    /**
     * Gets the value of the backorderDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getBackorderDate() {
        return backorderDate;
    }

    /**
     * Sets the value of the backorderDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setBackorderDate(XMLGregorianCalendar value) {
        this.backorderDate = value;
    }

    /**
     * Gets the value of the returnDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getReturnDate() {
        return returnDate;
    }

    /**
     * Sets the value of the returnDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setReturnDate(XMLGregorianCalendar value) {
        this.returnDate = value;
    }

    /**
     * Gets the value of the originalSalesDocumentKey property.
     * 
     * @return
     *     possible object is
     *     {@link SalesDocumentKey }
     *     
     */
    public SalesDocumentKey getOriginalSalesDocumentKey() {
        return originalSalesDocumentKey;
    }

    /**
     * Sets the value of the originalSalesDocumentKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link SalesDocumentKey }
     *     
     */
    public void setOriginalSalesDocumentKey(SalesDocumentKey value) {
        this.originalSalesDocumentKey = value;
    }

    /**
     * Gets the value of the originalSalesDocumentType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOriginalSalesDocumentType() {
        return originalSalesDocumentType;
    }

    /**
     * Sets the value of the originalSalesDocumentType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOriginalSalesDocumentType(String value) {
        this.originalSalesDocumentType = value;
    }

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
     * Gets the value of the warehouseKey property.
     * 
     * @return
     *     possible object is
     *     {@link WarehouseKey }
     *     
     */
    public WarehouseKey getWarehouseKey() {
        return warehouseKey;
    }

    /**
     * Sets the value of the warehouseKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link WarehouseKey }
     *     
     */
    public void setWarehouseKey(WarehouseKey value) {
        this.warehouseKey = value;
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
     * Gets the value of the salesTerritoryKey property.
     * 
     * @return
     *     possible object is
     *     {@link SalesTerritoryKey }
     *     
     */
    public SalesTerritoryKey getSalesTerritoryKey() {
        return salesTerritoryKey;
    }

    /**
     * Sets the value of the salesTerritoryKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link SalesTerritoryKey }
     *     
     */
    public void setSalesTerritoryKey(SalesTerritoryKey value) {
        this.salesTerritoryKey = value;
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
     * Gets the value of the userDefined property.
     * 
     * @return
     *     possible object is
     *     {@link SalesUserDefined }
     *     
     */
    public SalesUserDefined getUserDefined() {
        return userDefined;
    }

    /**
     * Sets the value of the userDefined property.
     * 
     * @param value
     *     allowed object is
     *     {@link SalesUserDefined }
     *     
     */
    public void setUserDefined(SalesUserDefined value) {
        this.userDefined = value;
    }

    /**
     * Gets the value of the commissions property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfSalesCommission }
     *     
     */
    public ArrayOfSalesCommission getCommissions() {
        return commissions;
    }

    /**
     * Sets the value of the commissions property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfSalesCommission }
     *     
     */
    public void setCommissions(ArrayOfSalesCommission value) {
        this.commissions = value;
    }

    /**
     * Gets the value of the processHolds property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfSalesProcessHold }
     *     
     */
    public ArrayOfSalesProcessHold getProcessHolds() {
        return processHolds;
    }

    /**
     * Sets the value of the processHolds property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfSalesProcessHold }
     *     
     */
    public void setProcessHolds(ArrayOfSalesProcessHold value) {
        this.processHolds = value;
    }

    /**
     * Gets the value of the taxes property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfSalesDocumentTax }
     *     
     */
    public ArrayOfSalesDocumentTax getTaxes() {
        return taxes;
    }

    /**
     * Sets the value of the taxes property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfSalesDocumentTax }
     *     
     */
    public void setTaxes(ArrayOfSalesDocumentTax value) {
        this.taxes = value;
    }

    /**
     * Gets the value of the freightTaxes property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfSalesDocumentTax }
     *     
     */
    public ArrayOfSalesDocumentTax getFreightTaxes() {
        return freightTaxes;
    }

    /**
     * Sets the value of the freightTaxes property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfSalesDocumentTax }
     *     
     */
    public void setFreightTaxes(ArrayOfSalesDocumentTax value) {
        this.freightTaxes = value;
    }

    /**
     * Gets the value of the miscellaneousTaxes property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfSalesDocumentTax }
     *     
     */
    public ArrayOfSalesDocumentTax getMiscellaneousTaxes() {
        return miscellaneousTaxes;
    }

    /**
     * Sets the value of the miscellaneousTaxes property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfSalesDocumentTax }
     *     
     */
    public void setMiscellaneousTaxes(ArrayOfSalesDocumentTax value) {
        this.miscellaneousTaxes = value;
    }

    /**
     * Gets the value of the trackingNumbers property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfSalesTrackingNumber }
     *     
     */
    public ArrayOfSalesTrackingNumber getTrackingNumbers() {
        return trackingNumbers;
    }

    /**
     * Sets the value of the trackingNumbers property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfSalesTrackingNumber }
     *     
     */
    public void setTrackingNumbers(ArrayOfSalesTrackingNumber value) {
        this.trackingNumbers = value;
    }

}
