
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for PurchaseOrderLine complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PurchaseOrderLine"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/gp/2006/01}BusinessObject"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Key" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}PurchaseTransactionLineKey" minOccurs="0"/&gt;
 *         &lt;element name="WarehouseKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}WarehouseKey" minOccurs="0"/&gt;
 *         &lt;element name="VendorItemNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="ItemKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ItemKey" minOccurs="0"/&gt;
 *         &lt;element name="QuantityOrdered" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}Quantity" minOccurs="0"/&gt;
 *         &lt;element name="QuantityCanceled" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}Quantity" minOccurs="0"/&gt;
 *         &lt;element name="FreeOnBoard" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}FreeOnBoard"/&gt;
 *         &lt;element name="RequestedBy" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="CommentKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}CommentKey" minOccurs="0"/&gt;
 *         &lt;element name="RequestedDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="ReleaseByDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="PromisedDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="PromisedShipDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="IsNonInventory" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="InventoryGLAccountKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}GLAccountNumberKey" minOccurs="0"/&gt;
 *         &lt;element name="ItemDescription" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="VendorItemDescription" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="UofM" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="TaxBasis" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}PurchasingTaxBasis"/&gt;
 *         &lt;element name="ItemTaxScheduleKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ItemTaxScheduleKey" minOccurs="0"/&gt;
 *         &lt;element name="WarehouseTaxScheduleKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}TaxScheduleKey" minOccurs="0"/&gt;
 *         &lt;element name="LandedCostGroupKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}LandedCostGroupKey" minOccurs="0"/&gt;
 *         &lt;element name="ShippingMethodKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ShippingMethodKey" minOccurs="0"/&gt;
 *         &lt;element name="Status" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}PurchaseOrderStatus"/&gt;
 *         &lt;element name="Comment" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="ShipToAddressKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}AddressKey" minOccurs="0"/&gt;
 *         &lt;element name="ShipToAddress" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}BusinessAddress" minOccurs="0"/&gt;
 *         &lt;element name="ProjectKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ProjectKey" minOccurs="0"/&gt;
 *         &lt;element name="CostCategoryKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}CostCategoryKey" minOccurs="0"/&gt;
 *         &lt;element name="UnitCost" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="ExtendedCost" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="SourceDocumentNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="SourceDocumentLineNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="ReleasedDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="TaxAmount" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="BackoutTaxAmount" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="Taxes" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ArrayOfPurchaseTax" minOccurs="0"/&gt;
 *         &lt;element name="JobNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="LineOrigin" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}PurchaseOrderLineOrigin"/&gt;
 *         &lt;element name="IsCapitalItem" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="DocumentDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PurchaseOrderLine", propOrder = {
    "key",
    "warehouseKey",
    "vendorItemNumber",
    "itemKey",
    "quantityOrdered",
    "quantityCanceled",
    "freeOnBoard",
    "requestedBy",
    "commentKey",
    "requestedDate",
    "releaseByDate",
    "promisedDate",
    "promisedShipDate",
    "isNonInventory",
    "inventoryGLAccountKey",
    "itemDescription",
    "vendorItemDescription",
    "uofM",
    "taxBasis",
    "itemTaxScheduleKey",
    "warehouseTaxScheduleKey",
    "landedCostGroupKey",
    "shippingMethodKey",
    "status",
    "comment",
    "shipToAddressKey",
    "shipToAddress",
    "projectKey",
    "costCategoryKey",
    "unitCost",
    "extendedCost",
    "sourceDocumentNumber",
    "sourceDocumentLineNumber",
    "releasedDate",
    "taxAmount",
    "backoutTaxAmount",
    "taxes",
    "jobNumber",
    "lineOrigin",
    "isCapitalItem",
    "documentDate"
})
public class PurchaseOrderLine
    extends BusinessObject
{

    @XmlElement(name = "Key")
    protected PurchaseTransactionLineKey key;
    @XmlElement(name = "WarehouseKey")
    protected WarehouseKey warehouseKey;
    @XmlElement(name = "VendorItemNumber")
    protected String vendorItemNumber;
    @XmlElement(name = "ItemKey")
    protected ItemKey itemKey;
    @XmlElement(name = "QuantityOrdered")
    protected Quantity quantityOrdered;
    @XmlElement(name = "QuantityCanceled")
    protected Quantity quantityCanceled;
    @XmlElement(name = "FreeOnBoard", required = true, nillable = true)
    @XmlSchemaType(name = "string")
    protected FreeOnBoard freeOnBoard;
    @XmlElement(name = "RequestedBy")
    protected String requestedBy;
    @XmlElement(name = "CommentKey")
    protected CommentKey commentKey;
    @XmlElement(name = "RequestedDate", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar requestedDate;
    @XmlElement(name = "ReleaseByDate", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar releaseByDate;
    @XmlElement(name = "PromisedDate", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar promisedDate;
    @XmlElement(name = "PromisedShipDate", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar promisedShipDate;
    @XmlElement(name = "IsNonInventory", required = true, type = Boolean.class, nillable = true)
    protected Boolean isNonInventory;
    @XmlElement(name = "InventoryGLAccountKey")
    protected GLAccountNumberKey inventoryGLAccountKey;
    @XmlElement(name = "ItemDescription")
    protected String itemDescription;
    @XmlElement(name = "VendorItemDescription")
    protected String vendorItemDescription;
    @XmlElement(name = "UofM")
    protected String uofM;
    @XmlElement(name = "TaxBasis", required = true, nillable = true)
    @XmlSchemaType(name = "string")
    protected PurchasingTaxBasis taxBasis;
    @XmlElement(name = "ItemTaxScheduleKey")
    protected ItemTaxScheduleKey itemTaxScheduleKey;
    @XmlElement(name = "WarehouseTaxScheduleKey")
    protected TaxScheduleKey warehouseTaxScheduleKey;
    @XmlElement(name = "LandedCostGroupKey")
    protected LandedCostGroupKey landedCostGroupKey;
    @XmlElement(name = "ShippingMethodKey")
    protected ShippingMethodKey shippingMethodKey;
    @XmlElement(name = "Status", required = true, nillable = true)
    @XmlSchemaType(name = "string")
    protected PurchaseOrderStatus status;
    @XmlElement(name = "Comment")
    protected String comment;
    @XmlElement(name = "ShipToAddressKey")
    protected AddressKey shipToAddressKey;
    @XmlElement(name = "ShipToAddress")
    protected BusinessAddress shipToAddress;
    @XmlElement(name = "ProjectKey")
    protected ProjectKey projectKey;
    @XmlElement(name = "CostCategoryKey")
    protected CostCategoryKey costCategoryKey;
    @XmlElement(name = "UnitCost")
    protected MoneyAmount unitCost;
    @XmlElement(name = "ExtendedCost")
    protected MoneyAmount extendedCost;
    @XmlElement(name = "SourceDocumentNumber")
    protected String sourceDocumentNumber;
    @XmlElement(name = "SourceDocumentLineNumber")
    protected String sourceDocumentLineNumber;
    @XmlElement(name = "ReleasedDate", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar releasedDate;
    @XmlElement(name = "TaxAmount")
    protected MoneyAmount taxAmount;
    @XmlElement(name = "BackoutTaxAmount")
    protected MoneyAmount backoutTaxAmount;
    @XmlElement(name = "Taxes")
    protected ArrayOfPurchaseTax taxes;
    @XmlElement(name = "JobNumber")
    protected String jobNumber;
    @XmlElement(name = "LineOrigin", required = true, nillable = true)
    @XmlSchemaType(name = "string")
    protected PurchaseOrderLineOrigin lineOrigin;
    @XmlElement(name = "IsCapitalItem", required = true, type = Boolean.class, nillable = true)
    protected Boolean isCapitalItem;
    @XmlElement(name = "DocumentDate", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar documentDate;

    /**
     * Gets the value of the key property.
     * 
     * @return
     *     possible object is
     *     {@link PurchaseTransactionLineKey }
     *     
     */
    public PurchaseTransactionLineKey getKey() {
        return key;
    }

    /**
     * Sets the value of the key property.
     * 
     * @param value
     *     allowed object is
     *     {@link PurchaseTransactionLineKey }
     *     
     */
    public void setKey(PurchaseTransactionLineKey value) {
        this.key = value;
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
     * Gets the value of the vendorItemNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVendorItemNumber() {
        return vendorItemNumber;
    }

    /**
     * Sets the value of the vendorItemNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVendorItemNumber(String value) {
        this.vendorItemNumber = value;
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
     * Gets the value of the quantityOrdered property.
     * 
     * @return
     *     possible object is
     *     {@link Quantity }
     *     
     */
    public Quantity getQuantityOrdered() {
        return quantityOrdered;
    }

    /**
     * Sets the value of the quantityOrdered property.
     * 
     * @param value
     *     allowed object is
     *     {@link Quantity }
     *     
     */
    public void setQuantityOrdered(Quantity value) {
        this.quantityOrdered = value;
    }

    /**
     * Gets the value of the quantityCanceled property.
     * 
     * @return
     *     possible object is
     *     {@link Quantity }
     *     
     */
    public Quantity getQuantityCanceled() {
        return quantityCanceled;
    }

    /**
     * Sets the value of the quantityCanceled property.
     * 
     * @param value
     *     allowed object is
     *     {@link Quantity }
     *     
     */
    public void setQuantityCanceled(Quantity value) {
        this.quantityCanceled = value;
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
     * Gets the value of the requestedBy property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRequestedBy() {
        return requestedBy;
    }

    /**
     * Sets the value of the requestedBy property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRequestedBy(String value) {
        this.requestedBy = value;
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
     * Gets the value of the releaseByDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getReleaseByDate() {
        return releaseByDate;
    }

    /**
     * Sets the value of the releaseByDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setReleaseByDate(XMLGregorianCalendar value) {
        this.releaseByDate = value;
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
     * Gets the value of the isNonInventory property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsNonInventory() {
        return isNonInventory;
    }

    /**
     * Sets the value of the isNonInventory property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsNonInventory(Boolean value) {
        this.isNonInventory = value;
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
     * Gets the value of the vendorItemDescription property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVendorItemDescription() {
        return vendorItemDescription;
    }

    /**
     * Sets the value of the vendorItemDescription property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVendorItemDescription(String value) {
        this.vendorItemDescription = value;
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
     * Gets the value of the taxBasis property.
     * 
     * @return
     *     possible object is
     *     {@link PurchasingTaxBasis }
     *     
     */
    public PurchasingTaxBasis getTaxBasis() {
        return taxBasis;
    }

    /**
     * Sets the value of the taxBasis property.
     * 
     * @param value
     *     allowed object is
     *     {@link PurchasingTaxBasis }
     *     
     */
    public void setTaxBasis(PurchasingTaxBasis value) {
        this.taxBasis = value;
    }

    /**
     * Gets the value of the itemTaxScheduleKey property.
     * 
     * @return
     *     possible object is
     *     {@link ItemTaxScheduleKey }
     *     
     */
    public ItemTaxScheduleKey getItemTaxScheduleKey() {
        return itemTaxScheduleKey;
    }

    /**
     * Sets the value of the itemTaxScheduleKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link ItemTaxScheduleKey }
     *     
     */
    public void setItemTaxScheduleKey(ItemTaxScheduleKey value) {
        this.itemTaxScheduleKey = value;
    }

    /**
     * Gets the value of the warehouseTaxScheduleKey property.
     * 
     * @return
     *     possible object is
     *     {@link TaxScheduleKey }
     *     
     */
    public TaxScheduleKey getWarehouseTaxScheduleKey() {
        return warehouseTaxScheduleKey;
    }

    /**
     * Sets the value of the warehouseTaxScheduleKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link TaxScheduleKey }
     *     
     */
    public void setWarehouseTaxScheduleKey(TaxScheduleKey value) {
        this.warehouseTaxScheduleKey = value;
    }

    /**
     * Gets the value of the landedCostGroupKey property.
     * 
     * @return
     *     possible object is
     *     {@link LandedCostGroupKey }
     *     
     */
    public LandedCostGroupKey getLandedCostGroupKey() {
        return landedCostGroupKey;
    }

    /**
     * Sets the value of the landedCostGroupKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link LandedCostGroupKey }
     *     
     */
    public void setLandedCostGroupKey(LandedCostGroupKey value) {
        this.landedCostGroupKey = value;
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
     * Gets the value of the projectKey property.
     * 
     * @return
     *     possible object is
     *     {@link ProjectKey }
     *     
     */
    public ProjectKey getProjectKey() {
        return projectKey;
    }

    /**
     * Sets the value of the projectKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProjectKey }
     *     
     */
    public void setProjectKey(ProjectKey value) {
        this.projectKey = value;
    }

    /**
     * Gets the value of the costCategoryKey property.
     * 
     * @return
     *     possible object is
     *     {@link CostCategoryKey }
     *     
     */
    public CostCategoryKey getCostCategoryKey() {
        return costCategoryKey;
    }

    /**
     * Sets the value of the costCategoryKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link CostCategoryKey }
     *     
     */
    public void setCostCategoryKey(CostCategoryKey value) {
        this.costCategoryKey = value;
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
     * Gets the value of the extendedCost property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getExtendedCost() {
        return extendedCost;
    }

    /**
     * Sets the value of the extendedCost property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setExtendedCost(MoneyAmount value) {
        this.extendedCost = value;
    }

    /**
     * Gets the value of the sourceDocumentNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSourceDocumentNumber() {
        return sourceDocumentNumber;
    }

    /**
     * Sets the value of the sourceDocumentNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSourceDocumentNumber(String value) {
        this.sourceDocumentNumber = value;
    }

    /**
     * Gets the value of the sourceDocumentLineNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSourceDocumentLineNumber() {
        return sourceDocumentLineNumber;
    }

    /**
     * Sets the value of the sourceDocumentLineNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSourceDocumentLineNumber(String value) {
        this.sourceDocumentLineNumber = value;
    }

    /**
     * Gets the value of the releasedDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getReleasedDate() {
        return releasedDate;
    }

    /**
     * Sets the value of the releasedDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setReleasedDate(XMLGregorianCalendar value) {
        this.releasedDate = value;
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
     * Gets the value of the jobNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getJobNumber() {
        return jobNumber;
    }

    /**
     * Sets the value of the jobNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setJobNumber(String value) {
        this.jobNumber = value;
    }

    /**
     * Gets the value of the lineOrigin property.
     * 
     * @return
     *     possible object is
     *     {@link PurchaseOrderLineOrigin }
     *     
     */
    public PurchaseOrderLineOrigin getLineOrigin() {
        return lineOrigin;
    }

    /**
     * Sets the value of the lineOrigin property.
     * 
     * @param value
     *     allowed object is
     *     {@link PurchaseOrderLineOrigin }
     *     
     */
    public void setLineOrigin(PurchaseOrderLineOrigin value) {
        this.lineOrigin = value;
    }

    /**
     * Gets the value of the isCapitalItem property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsCapitalItem() {
        return isCapitalItem;
    }

    /**
     * Sets the value of the isCapitalItem property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsCapitalItem(Boolean value) {
        this.isCapitalItem = value;
    }

    /**
     * Gets the value of the documentDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDocumentDate() {
        return documentDate;
    }

    /**
     * Sets the value of the documentDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDocumentDate(XMLGregorianCalendar value) {
        this.documentDate = value;
    }

}
