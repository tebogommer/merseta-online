
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for ReturnMaterialAuthorizationLine complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ReturnMaterialAuthorizationLine"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/gp/2006/01}BusinessObject"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Key" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ServiceLineKey" minOccurs="0"/&gt;
 *         &lt;element name="BillOfLading" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="ClosedDateTime" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="CommitDateTime" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="Replacement" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ReturnMaterialAuthorizationLineReplacement" minOccurs="0"/&gt;
 *         &lt;element name="CustomerName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="CustomerPONumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="EntryDateTime" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="EstimatedArrivalDateTime" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="IsClosed" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="IsCustomerOwned" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="IsFactorySealed" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="IsReadyToClose" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="ItemDescription" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="ItemKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ItemKey" minOccurs="0"/&gt;
 *         &lt;element name="Note" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="OfficeKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}OfficeKey" minOccurs="0"/&gt;
 *         &lt;element name="OriginatingDocumentId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="PromiseDateTime" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="Quantity" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}Quantity" minOccurs="0"/&gt;
 *         &lt;element name="ReasonCodeKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ReturnMaterialAuthorizationReasonCodeKey" minOccurs="0"/&gt;
 *         &lt;element name="Repair" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ReturnMaterialAuthorizationLineRepair" minOccurs="0"/&gt;
 *         &lt;element name="ReturnDateTime" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="ReturnedItemDescription" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="ReturnedItemKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ItemKey" minOccurs="0"/&gt;
 *         &lt;element name="ReturnedPriceLevelKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}PriceLevelKey" minOccurs="0"/&gt;
 *         &lt;element name="ReturnedQuantity" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}Quantity" minOccurs="0"/&gt;
 *         &lt;element name="ReturnedTotalAmount" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="ReturnedUnitCost" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="ReturnedUnitPrice" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="ReturnedUofM" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="ReturnedWarehouseKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}WarehouseKey" minOccurs="0"/&gt;
 *         &lt;element name="ReturnStatusCodeKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ReturnMaterialAuthorizationStatusCodeKey" minOccurs="0"/&gt;
 *         &lt;element name="ReturnToAddress" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ReturnMaterialAuthorizationReturnToAddress" minOccurs="0"/&gt;
 *         &lt;element name="ReturnTypeKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ReturnMaterialAuthorizationTypeKey" minOccurs="0"/&gt;
 *         &lt;element name="ReturnWarehouseKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}WarehouseKey" minOccurs="0"/&gt;
 *         &lt;element name="SalesInvoiceLineKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}SalesLineKey" minOccurs="0"/&gt;
 *         &lt;element name="SalesInvoiceComponentSequenceNumber" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="EquipmentLineSequenceNumber" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="ServiceLineKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ServiceLineKey" minOccurs="0"/&gt;
 *         &lt;element name="ShippingMethodKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ShippingMethodKey" minOccurs="0"/&gt;
 *         &lt;element name="ShipToAddress" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ReturnMaterialAuthorizationShipToAddress" minOccurs="0"/&gt;
 *         &lt;element name="ShipToAddressKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}AddressKey" minOccurs="0"/&gt;
 *         &lt;element name="TotalAmount" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="UnitCost" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="UnitPrice" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="UofM" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="UserDefined01" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="UserDefined02" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="UserDefined03" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="UserDefined04" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="UserDefined05" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="CreatedBy" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="FrontOfficeIntegrationId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="IsFromServiceCall" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="IsReceived" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="IsTravelerPrinted" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="ReasonCodeDescription" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="Reference" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="ReturnedTotalCost" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="ReturnStatus" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ReturnMaterialAuthorizationReturnStatus"/&gt;
 *         &lt;element name="SalesReturnLineKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}SalesLineKey" minOccurs="0"/&gt;
 *         &lt;element name="SalesOrderLineKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}SalesLineKey" minOccurs="0"/&gt;
 *         &lt;element name="TotalCost" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="TransferDocumentLineKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ServiceLineKey" minOccurs="0"/&gt;
 *         &lt;element name="TransferStatus" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ReturnMaterialAuthorizationTransferDocumentStatus"/&gt;
 *         &lt;element name="Distributions" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ArrayOfReturnMaterialAuthorizationDistribution" minOccurs="0"/&gt;
 *         &lt;element name="Lots" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ArrayOfReturnMaterialAuthorizationLineLot" minOccurs="0"/&gt;
 *         &lt;element name="Serials" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ArrayOfReturnMaterialAuthorizationLineSerial" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ReturnMaterialAuthorizationLine", propOrder = {
    "key",
    "billOfLading",
    "closedDateTime",
    "commitDateTime",
    "replacement",
    "customerName",
    "customerPONumber",
    "entryDateTime",
    "estimatedArrivalDateTime",
    "isClosed",
    "isCustomerOwned",
    "isFactorySealed",
    "isReadyToClose",
    "itemDescription",
    "itemKey",
    "note",
    "officeKey",
    "originatingDocumentId",
    "promiseDateTime",
    "quantity",
    "reasonCodeKey",
    "repair",
    "returnDateTime",
    "returnedItemDescription",
    "returnedItemKey",
    "returnedPriceLevelKey",
    "returnedQuantity",
    "returnedTotalAmount",
    "returnedUnitCost",
    "returnedUnitPrice",
    "returnedUofM",
    "returnedWarehouseKey",
    "returnStatusCodeKey",
    "returnToAddress",
    "returnTypeKey",
    "returnWarehouseKey",
    "salesInvoiceLineKey",
    "salesInvoiceComponentSequenceNumber",
    "equipmentLineSequenceNumber",
    "serviceLineKey",
    "shippingMethodKey",
    "shipToAddress",
    "shipToAddressKey",
    "totalAmount",
    "unitCost",
    "unitPrice",
    "uofM",
    "userDefined01",
    "userDefined02",
    "userDefined03",
    "userDefined04",
    "userDefined05",
    "createdBy",
    "frontOfficeIntegrationId",
    "isFromServiceCall",
    "isReceived",
    "isTravelerPrinted",
    "reasonCodeDescription",
    "reference",
    "returnedTotalCost",
    "returnStatus",
    "salesReturnLineKey",
    "salesOrderLineKey",
    "totalCost",
    "transferDocumentLineKey",
    "transferStatus",
    "distributions",
    "lots",
    "serials"
})
public class ReturnMaterialAuthorizationLine
    extends BusinessObject
{

    @XmlElement(name = "Key")
    protected ServiceLineKey key;
    @XmlElement(name = "BillOfLading")
    protected String billOfLading;
    @XmlElement(name = "ClosedDateTime", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar closedDateTime;
    @XmlElement(name = "CommitDateTime", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar commitDateTime;
    @XmlElement(name = "Replacement")
    protected ReturnMaterialAuthorizationLineReplacement replacement;
    @XmlElement(name = "CustomerName")
    protected String customerName;
    @XmlElement(name = "CustomerPONumber")
    protected String customerPONumber;
    @XmlElement(name = "EntryDateTime", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar entryDateTime;
    @XmlElement(name = "EstimatedArrivalDateTime", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar estimatedArrivalDateTime;
    @XmlElement(name = "IsClosed", required = true, type = Boolean.class, nillable = true)
    protected Boolean isClosed;
    @XmlElement(name = "IsCustomerOwned", required = true, type = Boolean.class, nillable = true)
    protected Boolean isCustomerOwned;
    @XmlElement(name = "IsFactorySealed", required = true, type = Boolean.class, nillable = true)
    protected Boolean isFactorySealed;
    @XmlElement(name = "IsReadyToClose", required = true, type = Boolean.class, nillable = true)
    protected Boolean isReadyToClose;
    @XmlElement(name = "ItemDescription")
    protected String itemDescription;
    @XmlElement(name = "ItemKey")
    protected ItemKey itemKey;
    @XmlElement(name = "Note")
    protected String note;
    @XmlElement(name = "OfficeKey")
    protected OfficeKey officeKey;
    @XmlElement(name = "OriginatingDocumentId")
    protected String originatingDocumentId;
    @XmlElement(name = "PromiseDateTime", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar promiseDateTime;
    @XmlElement(name = "Quantity")
    protected Quantity quantity;
    @XmlElement(name = "ReasonCodeKey")
    protected ReturnMaterialAuthorizationReasonCodeKey reasonCodeKey;
    @XmlElement(name = "Repair")
    protected ReturnMaterialAuthorizationLineRepair repair;
    @XmlElement(name = "ReturnDateTime", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar returnDateTime;
    @XmlElement(name = "ReturnedItemDescription")
    protected String returnedItemDescription;
    @XmlElement(name = "ReturnedItemKey")
    protected ItemKey returnedItemKey;
    @XmlElement(name = "ReturnedPriceLevelKey")
    protected PriceLevelKey returnedPriceLevelKey;
    @XmlElement(name = "ReturnedQuantity")
    protected Quantity returnedQuantity;
    @XmlElement(name = "ReturnedTotalAmount")
    protected MoneyAmount returnedTotalAmount;
    @XmlElement(name = "ReturnedUnitCost")
    protected MoneyAmount returnedUnitCost;
    @XmlElement(name = "ReturnedUnitPrice")
    protected MoneyAmount returnedUnitPrice;
    @XmlElement(name = "ReturnedUofM")
    protected String returnedUofM;
    @XmlElement(name = "ReturnedWarehouseKey")
    protected WarehouseKey returnedWarehouseKey;
    @XmlElement(name = "ReturnStatusCodeKey")
    protected ReturnMaterialAuthorizationStatusCodeKey returnStatusCodeKey;
    @XmlElement(name = "ReturnToAddress")
    protected ReturnMaterialAuthorizationReturnToAddress returnToAddress;
    @XmlElement(name = "ReturnTypeKey")
    protected ReturnMaterialAuthorizationTypeKey returnTypeKey;
    @XmlElement(name = "ReturnWarehouseKey")
    protected WarehouseKey returnWarehouseKey;
    @XmlElement(name = "SalesInvoiceLineKey")
    protected SalesLineKey salesInvoiceLineKey;
    @XmlElement(name = "SalesInvoiceComponentSequenceNumber", required = true, type = Integer.class, nillable = true)
    protected Integer salesInvoiceComponentSequenceNumber;
    @XmlElement(name = "EquipmentLineSequenceNumber", required = true, type = Integer.class, nillable = true)
    protected Integer equipmentLineSequenceNumber;
    @XmlElement(name = "ServiceLineKey")
    protected ServiceLineKey serviceLineKey;
    @XmlElement(name = "ShippingMethodKey")
    protected ShippingMethodKey shippingMethodKey;
    @XmlElement(name = "ShipToAddress")
    protected ReturnMaterialAuthorizationShipToAddress shipToAddress;
    @XmlElement(name = "ShipToAddressKey")
    protected AddressKey shipToAddressKey;
    @XmlElement(name = "TotalAmount")
    protected MoneyAmount totalAmount;
    @XmlElement(name = "UnitCost")
    protected MoneyAmount unitCost;
    @XmlElement(name = "UnitPrice")
    protected MoneyAmount unitPrice;
    @XmlElement(name = "UofM")
    protected String uofM;
    @XmlElement(name = "UserDefined01")
    protected String userDefined01;
    @XmlElement(name = "UserDefined02")
    protected String userDefined02;
    @XmlElement(name = "UserDefined03")
    protected String userDefined03;
    @XmlElement(name = "UserDefined04")
    protected String userDefined04;
    @XmlElement(name = "UserDefined05")
    protected String userDefined05;
    @XmlElement(name = "CreatedBy")
    protected String createdBy;
    @XmlElement(name = "FrontOfficeIntegrationId")
    protected String frontOfficeIntegrationId;
    @XmlElement(name = "IsFromServiceCall", required = true, type = Boolean.class, nillable = true)
    protected Boolean isFromServiceCall;
    @XmlElement(name = "IsReceived", required = true, type = Boolean.class, nillable = true)
    protected Boolean isReceived;
    @XmlElement(name = "IsTravelerPrinted", required = true, type = Boolean.class, nillable = true)
    protected Boolean isTravelerPrinted;
    @XmlElement(name = "ReasonCodeDescription")
    protected String reasonCodeDescription;
    @XmlElement(name = "Reference")
    protected String reference;
    @XmlElement(name = "ReturnedTotalCost")
    protected MoneyAmount returnedTotalCost;
    @XmlElement(name = "ReturnStatus", required = true, nillable = true)
    @XmlSchemaType(name = "string")
    protected ReturnMaterialAuthorizationReturnStatus returnStatus;
    @XmlElement(name = "SalesReturnLineKey")
    protected SalesLineKey salesReturnLineKey;
    @XmlElement(name = "SalesOrderLineKey")
    protected SalesLineKey salesOrderLineKey;
    @XmlElement(name = "TotalCost")
    protected MoneyAmount totalCost;
    @XmlElement(name = "TransferDocumentLineKey")
    protected ServiceLineKey transferDocumentLineKey;
    @XmlElement(name = "TransferStatus", required = true, nillable = true)
    @XmlSchemaType(name = "string")
    protected ReturnMaterialAuthorizationTransferDocumentStatus transferStatus;
    @XmlElement(name = "Distributions")
    protected ArrayOfReturnMaterialAuthorizationDistribution distributions;
    @XmlElement(name = "Lots")
    protected ArrayOfReturnMaterialAuthorizationLineLot lots;
    @XmlElement(name = "Serials")
    protected ArrayOfReturnMaterialAuthorizationLineSerial serials;

    /**
     * Gets the value of the key property.
     * 
     * @return
     *     possible object is
     *     {@link ServiceLineKey }
     *     
     */
    public ServiceLineKey getKey() {
        return key;
    }

    /**
     * Sets the value of the key property.
     * 
     * @param value
     *     allowed object is
     *     {@link ServiceLineKey }
     *     
     */
    public void setKey(ServiceLineKey value) {
        this.key = value;
    }

    /**
     * Gets the value of the billOfLading property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBillOfLading() {
        return billOfLading;
    }

    /**
     * Sets the value of the billOfLading property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBillOfLading(String value) {
        this.billOfLading = value;
    }

    /**
     * Gets the value of the closedDateTime property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getClosedDateTime() {
        return closedDateTime;
    }

    /**
     * Sets the value of the closedDateTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setClosedDateTime(XMLGregorianCalendar value) {
        this.closedDateTime = value;
    }

    /**
     * Gets the value of the commitDateTime property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getCommitDateTime() {
        return commitDateTime;
    }

    /**
     * Sets the value of the commitDateTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setCommitDateTime(XMLGregorianCalendar value) {
        this.commitDateTime = value;
    }

    /**
     * Gets the value of the replacement property.
     * 
     * @return
     *     possible object is
     *     {@link ReturnMaterialAuthorizationLineReplacement }
     *     
     */
    public ReturnMaterialAuthorizationLineReplacement getReplacement() {
        return replacement;
    }

    /**
     * Sets the value of the replacement property.
     * 
     * @param value
     *     allowed object is
     *     {@link ReturnMaterialAuthorizationLineReplacement }
     *     
     */
    public void setReplacement(ReturnMaterialAuthorizationLineReplacement value) {
        this.replacement = value;
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
     * Gets the value of the entryDateTime property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getEntryDateTime() {
        return entryDateTime;
    }

    /**
     * Sets the value of the entryDateTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setEntryDateTime(XMLGregorianCalendar value) {
        this.entryDateTime = value;
    }

    /**
     * Gets the value of the estimatedArrivalDateTime property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getEstimatedArrivalDateTime() {
        return estimatedArrivalDateTime;
    }

    /**
     * Sets the value of the estimatedArrivalDateTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setEstimatedArrivalDateTime(XMLGregorianCalendar value) {
        this.estimatedArrivalDateTime = value;
    }

    /**
     * Gets the value of the isClosed property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsClosed() {
        return isClosed;
    }

    /**
     * Sets the value of the isClosed property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsClosed(Boolean value) {
        this.isClosed = value;
    }

    /**
     * Gets the value of the isCustomerOwned property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsCustomerOwned() {
        return isCustomerOwned;
    }

    /**
     * Sets the value of the isCustomerOwned property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsCustomerOwned(Boolean value) {
        this.isCustomerOwned = value;
    }

    /**
     * Gets the value of the isFactorySealed property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsFactorySealed() {
        return isFactorySealed;
    }

    /**
     * Sets the value of the isFactorySealed property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsFactorySealed(Boolean value) {
        this.isFactorySealed = value;
    }

    /**
     * Gets the value of the isReadyToClose property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsReadyToClose() {
        return isReadyToClose;
    }

    /**
     * Sets the value of the isReadyToClose property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsReadyToClose(Boolean value) {
        this.isReadyToClose = value;
    }

    /**
     * Gets the value of the itemDescription property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getItemDescription() {
        return itemDescription;
    }

    /**
     * Sets the value of the itemDescription property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setItemDescription(String value) {
        this.itemDescription = value;
    }

    /**
     * Gets the value of the itemKey property.
     * 
     * @return
     *     possible object is
     *     {@link ItemKey }
     *     
     */
    public ItemKey getItemKey() {
        return itemKey;
    }

    /**
     * Sets the value of the itemKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link ItemKey }
     *     
     */
    public void setItemKey(ItemKey value) {
        this.itemKey = value;
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
     * Gets the value of the officeKey property.
     * 
     * @return
     *     possible object is
     *     {@link OfficeKey }
     *     
     */
    public OfficeKey getOfficeKey() {
        return officeKey;
    }

    /**
     * Sets the value of the officeKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link OfficeKey }
     *     
     */
    public void setOfficeKey(OfficeKey value) {
        this.officeKey = value;
    }

    /**
     * Gets the value of the originatingDocumentId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOriginatingDocumentId() {
        return originatingDocumentId;
    }

    /**
     * Sets the value of the originatingDocumentId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOriginatingDocumentId(String value) {
        this.originatingDocumentId = value;
    }

    /**
     * Gets the value of the promiseDateTime property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getPromiseDateTime() {
        return promiseDateTime;
    }

    /**
     * Sets the value of the promiseDateTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setPromiseDateTime(XMLGregorianCalendar value) {
        this.promiseDateTime = value;
    }

    /**
     * Gets the value of the quantity property.
     * 
     * @return
     *     possible object is
     *     {@link Quantity }
     *     
     */
    public Quantity getQuantity() {
        return quantity;
    }

    /**
     * Sets the value of the quantity property.
     * 
     * @param value
     *     allowed object is
     *     {@link Quantity }
     *     
     */
    public void setQuantity(Quantity value) {
        this.quantity = value;
    }

    /**
     * Gets the value of the reasonCodeKey property.
     * 
     * @return
     *     possible object is
     *     {@link ReturnMaterialAuthorizationReasonCodeKey }
     *     
     */
    public ReturnMaterialAuthorizationReasonCodeKey getReasonCodeKey() {
        return reasonCodeKey;
    }

    /**
     * Sets the value of the reasonCodeKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link ReturnMaterialAuthorizationReasonCodeKey }
     *     
     */
    public void setReasonCodeKey(ReturnMaterialAuthorizationReasonCodeKey value) {
        this.reasonCodeKey = value;
    }

    /**
     * Gets the value of the repair property.
     * 
     * @return
     *     possible object is
     *     {@link ReturnMaterialAuthorizationLineRepair }
     *     
     */
    public ReturnMaterialAuthorizationLineRepair getRepair() {
        return repair;
    }

    /**
     * Sets the value of the repair property.
     * 
     * @param value
     *     allowed object is
     *     {@link ReturnMaterialAuthorizationLineRepair }
     *     
     */
    public void setRepair(ReturnMaterialAuthorizationLineRepair value) {
        this.repair = value;
    }

    /**
     * Gets the value of the returnDateTime property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getReturnDateTime() {
        return returnDateTime;
    }

    /**
     * Sets the value of the returnDateTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setReturnDateTime(XMLGregorianCalendar value) {
        this.returnDateTime = value;
    }

    /**
     * Gets the value of the returnedItemDescription property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReturnedItemDescription() {
        return returnedItemDescription;
    }

    /**
     * Sets the value of the returnedItemDescription property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReturnedItemDescription(String value) {
        this.returnedItemDescription = value;
    }

    /**
     * Gets the value of the returnedItemKey property.
     * 
     * @return
     *     possible object is
     *     {@link ItemKey }
     *     
     */
    public ItemKey getReturnedItemKey() {
        return returnedItemKey;
    }

    /**
     * Sets the value of the returnedItemKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link ItemKey }
     *     
     */
    public void setReturnedItemKey(ItemKey value) {
        this.returnedItemKey = value;
    }

    /**
     * Gets the value of the returnedPriceLevelKey property.
     * 
     * @return
     *     possible object is
     *     {@link PriceLevelKey }
     *     
     */
    public PriceLevelKey getReturnedPriceLevelKey() {
        return returnedPriceLevelKey;
    }

    /**
     * Sets the value of the returnedPriceLevelKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link PriceLevelKey }
     *     
     */
    public void setReturnedPriceLevelKey(PriceLevelKey value) {
        this.returnedPriceLevelKey = value;
    }

    /**
     * Gets the value of the returnedQuantity property.
     * 
     * @return
     *     possible object is
     *     {@link Quantity }
     *     
     */
    public Quantity getReturnedQuantity() {
        return returnedQuantity;
    }

    /**
     * Sets the value of the returnedQuantity property.
     * 
     * @param value
     *     allowed object is
     *     {@link Quantity }
     *     
     */
    public void setReturnedQuantity(Quantity value) {
        this.returnedQuantity = value;
    }

    /**
     * Gets the value of the returnedTotalAmount property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getReturnedTotalAmount() {
        return returnedTotalAmount;
    }

    /**
     * Sets the value of the returnedTotalAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setReturnedTotalAmount(MoneyAmount value) {
        this.returnedTotalAmount = value;
    }

    /**
     * Gets the value of the returnedUnitCost property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getReturnedUnitCost() {
        return returnedUnitCost;
    }

    /**
     * Sets the value of the returnedUnitCost property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setReturnedUnitCost(MoneyAmount value) {
        this.returnedUnitCost = value;
    }

    /**
     * Gets the value of the returnedUnitPrice property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getReturnedUnitPrice() {
        return returnedUnitPrice;
    }

    /**
     * Sets the value of the returnedUnitPrice property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setReturnedUnitPrice(MoneyAmount value) {
        this.returnedUnitPrice = value;
    }

    /**
     * Gets the value of the returnedUofM property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReturnedUofM() {
        return returnedUofM;
    }

    /**
     * Sets the value of the returnedUofM property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReturnedUofM(String value) {
        this.returnedUofM = value;
    }

    /**
     * Gets the value of the returnedWarehouseKey property.
     * 
     * @return
     *     possible object is
     *     {@link WarehouseKey }
     *     
     */
    public WarehouseKey getReturnedWarehouseKey() {
        return returnedWarehouseKey;
    }

    /**
     * Sets the value of the returnedWarehouseKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link WarehouseKey }
     *     
     */
    public void setReturnedWarehouseKey(WarehouseKey value) {
        this.returnedWarehouseKey = value;
    }

    /**
     * Gets the value of the returnStatusCodeKey property.
     * 
     * @return
     *     possible object is
     *     {@link ReturnMaterialAuthorizationStatusCodeKey }
     *     
     */
    public ReturnMaterialAuthorizationStatusCodeKey getReturnStatusCodeKey() {
        return returnStatusCodeKey;
    }

    /**
     * Sets the value of the returnStatusCodeKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link ReturnMaterialAuthorizationStatusCodeKey }
     *     
     */
    public void setReturnStatusCodeKey(ReturnMaterialAuthorizationStatusCodeKey value) {
        this.returnStatusCodeKey = value;
    }

    /**
     * Gets the value of the returnToAddress property.
     * 
     * @return
     *     possible object is
     *     {@link ReturnMaterialAuthorizationReturnToAddress }
     *     
     */
    public ReturnMaterialAuthorizationReturnToAddress getReturnToAddress() {
        return returnToAddress;
    }

    /**
     * Sets the value of the returnToAddress property.
     * 
     * @param value
     *     allowed object is
     *     {@link ReturnMaterialAuthorizationReturnToAddress }
     *     
     */
    public void setReturnToAddress(ReturnMaterialAuthorizationReturnToAddress value) {
        this.returnToAddress = value;
    }

    /**
     * Gets the value of the returnTypeKey property.
     * 
     * @return
     *     possible object is
     *     {@link ReturnMaterialAuthorizationTypeKey }
     *     
     */
    public ReturnMaterialAuthorizationTypeKey getReturnTypeKey() {
        return returnTypeKey;
    }

    /**
     * Sets the value of the returnTypeKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link ReturnMaterialAuthorizationTypeKey }
     *     
     */
    public void setReturnTypeKey(ReturnMaterialAuthorizationTypeKey value) {
        this.returnTypeKey = value;
    }

    /**
     * Gets the value of the returnWarehouseKey property.
     * 
     * @return
     *     possible object is
     *     {@link WarehouseKey }
     *     
     */
    public WarehouseKey getReturnWarehouseKey() {
        return returnWarehouseKey;
    }

    /**
     * Sets the value of the returnWarehouseKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link WarehouseKey }
     *     
     */
    public void setReturnWarehouseKey(WarehouseKey value) {
        this.returnWarehouseKey = value;
    }

    /**
     * Gets the value of the salesInvoiceLineKey property.
     * 
     * @return
     *     possible object is
     *     {@link SalesLineKey }
     *     
     */
    public SalesLineKey getSalesInvoiceLineKey() {
        return salesInvoiceLineKey;
    }

    /**
     * Sets the value of the salesInvoiceLineKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link SalesLineKey }
     *     
     */
    public void setSalesInvoiceLineKey(SalesLineKey value) {
        this.salesInvoiceLineKey = value;
    }

    /**
     * Gets the value of the salesInvoiceComponentSequenceNumber property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getSalesInvoiceComponentSequenceNumber() {
        return salesInvoiceComponentSequenceNumber;
    }

    /**
     * Sets the value of the salesInvoiceComponentSequenceNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setSalesInvoiceComponentSequenceNumber(Integer value) {
        this.salesInvoiceComponentSequenceNumber = value;
    }

    /**
     * Gets the value of the equipmentLineSequenceNumber property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getEquipmentLineSequenceNumber() {
        return equipmentLineSequenceNumber;
    }

    /**
     * Sets the value of the equipmentLineSequenceNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setEquipmentLineSequenceNumber(Integer value) {
        this.equipmentLineSequenceNumber = value;
    }

    /**
     * Gets the value of the serviceLineKey property.
     * 
     * @return
     *     possible object is
     *     {@link ServiceLineKey }
     *     
     */
    public ServiceLineKey getServiceLineKey() {
        return serviceLineKey;
    }

    /**
     * Sets the value of the serviceLineKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link ServiceLineKey }
     *     
     */
    public void setServiceLineKey(ServiceLineKey value) {
        this.serviceLineKey = value;
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
     * Gets the value of the shipToAddress property.
     * 
     * @return
     *     possible object is
     *     {@link ReturnMaterialAuthorizationShipToAddress }
     *     
     */
    public ReturnMaterialAuthorizationShipToAddress getShipToAddress() {
        return shipToAddress;
    }

    /**
     * Sets the value of the shipToAddress property.
     * 
     * @param value
     *     allowed object is
     *     {@link ReturnMaterialAuthorizationShipToAddress }
     *     
     */
    public void setShipToAddress(ReturnMaterialAuthorizationShipToAddress value) {
        this.shipToAddress = value;
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
     * Gets the value of the unitCost property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getUnitCost() {
        return unitCost;
    }

    /**
     * Sets the value of the unitCost property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setUnitCost(MoneyAmount value) {
        this.unitCost = value;
    }

    /**
     * Gets the value of the unitPrice property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getUnitPrice() {
        return unitPrice;
    }

    /**
     * Sets the value of the unitPrice property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setUnitPrice(MoneyAmount value) {
        this.unitPrice = value;
    }

    /**
     * Gets the value of the uofM property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUofM() {
        return uofM;
    }

    /**
     * Sets the value of the uofM property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUofM(String value) {
        this.uofM = value;
    }

    /**
     * Gets the value of the userDefined01 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUserDefined01() {
        return userDefined01;
    }

    /**
     * Sets the value of the userDefined01 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUserDefined01(String value) {
        this.userDefined01 = value;
    }

    /**
     * Gets the value of the userDefined02 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUserDefined02() {
        return userDefined02;
    }

    /**
     * Sets the value of the userDefined02 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUserDefined02(String value) {
        this.userDefined02 = value;
    }

    /**
     * Gets the value of the userDefined03 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUserDefined03() {
        return userDefined03;
    }

    /**
     * Sets the value of the userDefined03 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUserDefined03(String value) {
        this.userDefined03 = value;
    }

    /**
     * Gets the value of the userDefined04 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUserDefined04() {
        return userDefined04;
    }

    /**
     * Sets the value of the userDefined04 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUserDefined04(String value) {
        this.userDefined04 = value;
    }

    /**
     * Gets the value of the userDefined05 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUserDefined05() {
        return userDefined05;
    }

    /**
     * Sets the value of the userDefined05 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUserDefined05(String value) {
        this.userDefined05 = value;
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
     * Gets the value of the isFromServiceCall property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsFromServiceCall() {
        return isFromServiceCall;
    }

    /**
     * Sets the value of the isFromServiceCall property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsFromServiceCall(Boolean value) {
        this.isFromServiceCall = value;
    }

    /**
     * Gets the value of the isReceived property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsReceived() {
        return isReceived;
    }

    /**
     * Sets the value of the isReceived property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsReceived(Boolean value) {
        this.isReceived = value;
    }

    /**
     * Gets the value of the isTravelerPrinted property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsTravelerPrinted() {
        return isTravelerPrinted;
    }

    /**
     * Sets the value of the isTravelerPrinted property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsTravelerPrinted(Boolean value) {
        this.isTravelerPrinted = value;
    }

    /**
     * Gets the value of the reasonCodeDescription property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReasonCodeDescription() {
        return reasonCodeDescription;
    }

    /**
     * Sets the value of the reasonCodeDescription property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReasonCodeDescription(String value) {
        this.reasonCodeDescription = value;
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
     * Gets the value of the returnedTotalCost property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getReturnedTotalCost() {
        return returnedTotalCost;
    }

    /**
     * Sets the value of the returnedTotalCost property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setReturnedTotalCost(MoneyAmount value) {
        this.returnedTotalCost = value;
    }

    /**
     * Gets the value of the returnStatus property.
     * 
     * @return
     *     possible object is
     *     {@link ReturnMaterialAuthorizationReturnStatus }
     *     
     */
    public ReturnMaterialAuthorizationReturnStatus getReturnStatus() {
        return returnStatus;
    }

    /**
     * Sets the value of the returnStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link ReturnMaterialAuthorizationReturnStatus }
     *     
     */
    public void setReturnStatus(ReturnMaterialAuthorizationReturnStatus value) {
        this.returnStatus = value;
    }

    /**
     * Gets the value of the salesReturnLineKey property.
     * 
     * @return
     *     possible object is
     *     {@link SalesLineKey }
     *     
     */
    public SalesLineKey getSalesReturnLineKey() {
        return salesReturnLineKey;
    }

    /**
     * Sets the value of the salesReturnLineKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link SalesLineKey }
     *     
     */
    public void setSalesReturnLineKey(SalesLineKey value) {
        this.salesReturnLineKey = value;
    }

    /**
     * Gets the value of the salesOrderLineKey property.
     * 
     * @return
     *     possible object is
     *     {@link SalesLineKey }
     *     
     */
    public SalesLineKey getSalesOrderLineKey() {
        return salesOrderLineKey;
    }

    /**
     * Sets the value of the salesOrderLineKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link SalesLineKey }
     *     
     */
    public void setSalesOrderLineKey(SalesLineKey value) {
        this.salesOrderLineKey = value;
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

    /**
     * Gets the value of the transferDocumentLineKey property.
     * 
     * @return
     *     possible object is
     *     {@link ServiceLineKey }
     *     
     */
    public ServiceLineKey getTransferDocumentLineKey() {
        return transferDocumentLineKey;
    }

    /**
     * Sets the value of the transferDocumentLineKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link ServiceLineKey }
     *     
     */
    public void setTransferDocumentLineKey(ServiceLineKey value) {
        this.transferDocumentLineKey = value;
    }

    /**
     * Gets the value of the transferStatus property.
     * 
     * @return
     *     possible object is
     *     {@link ReturnMaterialAuthorizationTransferDocumentStatus }
     *     
     */
    public ReturnMaterialAuthorizationTransferDocumentStatus getTransferStatus() {
        return transferStatus;
    }

    /**
     * Sets the value of the transferStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link ReturnMaterialAuthorizationTransferDocumentStatus }
     *     
     */
    public void setTransferStatus(ReturnMaterialAuthorizationTransferDocumentStatus value) {
        this.transferStatus = value;
    }

    /**
     * Gets the value of the distributions property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfReturnMaterialAuthorizationDistribution }
     *     
     */
    public ArrayOfReturnMaterialAuthorizationDistribution getDistributions() {
        return distributions;
    }

    /**
     * Sets the value of the distributions property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfReturnMaterialAuthorizationDistribution }
     *     
     */
    public void setDistributions(ArrayOfReturnMaterialAuthorizationDistribution value) {
        this.distributions = value;
    }

    /**
     * Gets the value of the lots property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfReturnMaterialAuthorizationLineLot }
     *     
     */
    public ArrayOfReturnMaterialAuthorizationLineLot getLots() {
        return lots;
    }

    /**
     * Sets the value of the lots property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfReturnMaterialAuthorizationLineLot }
     *     
     */
    public void setLots(ArrayOfReturnMaterialAuthorizationLineLot value) {
        this.lots = value;
    }

    /**
     * Gets the value of the serials property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfReturnMaterialAuthorizationLineSerial }
     *     
     */
    public ArrayOfReturnMaterialAuthorizationLineSerial getSerials() {
        return serials;
    }

    /**
     * Sets the value of the serials property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfReturnMaterialAuthorizationLineSerial }
     *     
     */
    public void setSerials(ArrayOfReturnMaterialAuthorizationLineSerial value) {
        this.serials = value;
    }

}
